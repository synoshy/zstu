/**
 * Copyright (c) 2018 Denys Zosimovych Open Source Project
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.synoshy.zstu.data.article;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Stream;

import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.List;

import io.synoshy.zstu.data.common.AppDatabase;
import io.synoshy.zstu.domain.article.Article;
import io.synoshy.zstu.domain.article.ArticleManager;
import io.synoshy.zstu.domain.common.util.Validator;
import io.synoshy.zstu.presentation.common.task.SimpleAsyncTask;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleManagerImpl implements ArticleManager {

    private AppDatabase appDatabase;

    private ArticleApi articleLoader;

    public ArticleManagerImpl(@NonNull AppDatabase appDatabase, @NonNull ArticleApi articleLoader) {
        this.appDatabase = appDatabase;
        this.articleLoader = articleLoader;
    }

    protected AppDatabase getAppDatabase() {
        Validator.throwIfNull(appDatabase, "Database was not initialized.");

        return appDatabase;
    }

    @Override
    public void createOrUpdate(@NonNull Article... entities) {
        Validator.throwIfNull(entities, "Could not create or update null entities.");
        Validator.throwIf(entities.length == 0, "No entities to create or update.");

        ArticleEntity[] articleEntities = Stream.of(entities)
                .map(ArticleMapper::mapArticleEntity)
                .toArray(ArticleEntity[]::new);

        SimpleAsyncTask.execute(() -> getAppDatabase().articles().createOrUpdate(articleEntities));
    }

    @Override
    public LiveData<Article> getById(int id) {
        return Transformations.map(getAppDatabase().articles().getById(id), ArticleMapper::mapArticle);
    }

    @Override
    public LiveData<List<Article>> getList() {
        return transform(getAppDatabase().articles().getList());
    }

    @Override
    public LiveData<List<Article>> getListBatch(int batchNumber, int batchSize) {
        Validator.throwIf(batchNumber < 0, "Batch number must be a non-negative value.");
        Validator.throwIf(batchSize <= 0, "Batch size must be a positive value.");

        return transform(getAppDatabase().articles().getListBatch(batchNumber * batchSize, batchSize));
    }

    @Override
    public void loadNewsFromNetwork(@NonNull HttpUrl url, @Nullable final Date newerThan, @NonNull Function<List<Article>, Void> callback) {
        articleLoader.getNewsContainer(url).enqueue(new Callback<Element>() {

            @Override
            public void onResponse(Call<Element> call, Response<Element> response) {
                Stream<Article> stream = Stream.of(ArticleHtmlParser.selectArticleNodes(response.body()))
                        .map(ArticleHtmlParser::parseArticle);
                if (newerThan != null)
                    stream = stream.filter(x -> x.getLastModified().compareTo(newerThan) > 0);

                callback.apply(stream.toList());
            }

            @Override
            public void onFailure(Call<Element> call, Throwable t) {
                callback.apply(null);
            }
        });
    }

    @Override
    public void delete(@NonNull Article entity) {
        Validator.throwIfNull(entity, "Could not delete null entity.");

        getAppDatabase().articles().delete(ArticleMapper.mapArticleEntity(entity));
    }

    @Override
    public LiveData<List<Article>> getByHeading(@NonNull String heading) {
        return transform(getAppDatabase().articles().getByHeading(heading));
    }

    @Override
    public LiveData<List<Article>> search(@NonNull String query) {
        return transform(getAppDatabase().articles().search(query));
    }

    private LiveData<List<Article>> transform(LiveData<List<ArticleEntity>> list) {
        return Transformations.map(list, x -> Stream.of(x).map(ArticleMapper::mapArticle).toList());
    }
}
