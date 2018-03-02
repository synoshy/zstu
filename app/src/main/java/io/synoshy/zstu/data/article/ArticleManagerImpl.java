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
import android.text.TextUtils;

import com.annimon.stream.Stream;

import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.List;

import io.synoshy.zstu.data.common.AppDatabase;
import io.synoshy.zstu.domain.article.ArticleContentType;
import io.synoshy.zstu.domain.article.IArticle;
import io.synoshy.zstu.domain.article.IArticleManager;
import io.synoshy.zstu.domain.common.lang.SimpleAsyncTask;
import io.synoshy.zstu.domain.common.util.Validator;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleManagerImpl implements IArticleManager {

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
    public void createOrUpdate(@NonNull IArticle... entities) {
        Validator.throwIfNull(entities, "Could not create or update null entities.");
        Validator.throwIf(entities.length == 0, "No entities to create or update.");

        Article[] articleEntities = Stream.of(entities)
                .map(Article::newInstance)
                .toArray(Article[]::new);

        SimpleAsyncTask.execute(() -> getAppDatabase().articles().createOrUpdate(articleEntities));
    }

    @Override
    public void update(@NonNull IArticle... entities) {
        Validator.throwIfNull(entities, "Could not update null entities.");
        Validator.throwIf(entities.length == 0, "No entities to update.");

        Article[] articleEntities = Stream.of(entities)
                .map(Article::newInstance)
                .toArray(Article[]::new);

        SimpleAsyncTask.execute(() -> getAppDatabase().articles().update(articleEntities));
    }

    @Override
    public LiveData<IArticle> getById(String id) {
        return transform(getAppDatabase().articles().getById(id));
    }

    @Override
    public LiveData<List<IArticle>> getList() {
        return transformList(getAppDatabase().articles().getList());
    }

    @Override
    public LiveData<List<IArticle>> getListBatch(int batchNumber, int batchSize) {
        Validator.throwIf(batchNumber < 0, "Batch number must be a non-negative value.");
        Validator.throwIf(batchSize <= 0, "Batch size must be a positive value.");

        return transformList(getAppDatabase().articles().getListBatch(batchNumber * batchSize, batchSize));
    }

    @Override
    public void loadNewsFromNetwork(@NonNull HttpUrl url, @Nullable final Date newerThan, @NonNull Function<List<IArticle>, Void> callback) {
        articleLoader.getNewsContainer(url).enqueue(new Callback<Element>() {

            @Override
            public void onResponse(Call<Element> call, Response<Element> response) {
                Stream<IArticle> stream = Stream.of(ArticleHtmlParser.selectArticlePreviewNodes(response.body()))
                        .map(ArticleHtmlParser::parseArticlePreview);
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
    public void loadArticleContent(@NonNull IArticle article, @NonNull Function<IArticle, Void> resultCallback) {
        String url = article.getUrl();
        Validator.throwIfEmptyString(url, "Provided article does not have url.");

        articleLoader.getArticle(HttpUrl.parse(article.getUrl())).enqueue(new Callback<Element>() {

            @Override
            public void onResponse(Call<Element> call, Response<Element> response) {
                Article articleImpl = Article.newInstance(article);
                ArticleContent[] rawContent = ArticleHtmlParser.parseArticleContent(
                        ArticleHtmlParser.selectArticleNode(response.body()));
                //copies of thumbnail image are removed
                ArticleContent[] content = Stream.of(rawContent)
                                                .filter(x -> !TextUtils.isEmpty(x.getValue()) && x.getValue() != article.getImageUrl())
                                                .toArray(ArticleContent[]::new);
                articleImpl.setContent(content);
                resultCallback.apply(articleImpl);
            }

            @Override
            public void onFailure(Call<Element> call, Throwable t) {
                resultCallback.apply(null);
            }
        });
    }

    @Override
    public void delete(@NonNull IArticle entity) {
        Validator.throwIfNull(entity, "Could not delete null entity.");

        getAppDatabase().articles().delete(Article.newInstance(entity));
    }

    @Override
    public LiveData<List<IArticle>> getByHeading(@NonNull String heading) {
        return transformList(getAppDatabase().articles().getByHeading(heading));
    }

    @Override
    public LiveData<List<IArticle>> search(@NonNull String query) {
        return transformList(getAppDatabase().articles().search(query));
    }

    private LiveData<List<IArticle>> transformList(LiveData<List<Article>> list) {
        return Transformations.map(list, x -> Stream.of(x).map(z -> (IArticle)z).toList());
    }

    private LiveData<IArticle> transform(LiveData<Article> article) {
        return Transformations.map(article, x -> (IArticle)x);
    }
}
