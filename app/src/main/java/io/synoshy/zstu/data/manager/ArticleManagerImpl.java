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

package io.synoshy.zstu.data.manager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;

import io.synoshy.zstu.data.database.AppDatabase;
import io.synoshy.zstu.data.entity.ArticleEntity;
import io.synoshy.zstu.data.entity.Mapper;
import io.synoshy.zstu.domain.entity.Article;
import io.synoshy.zstu.domain.manager.ArticleManager;
import io.synoshy.zstu.domain.util.Validator;

public class ArticleManagerImpl implements ArticleManager {

    private AppDatabase appDatabase;

    @Inject
    ArticleManagerImpl(@NonNull AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
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
                .map(Mapper::mapArticleEntity)
                .toArray(ArticleEntity[]::new);
        getAppDatabase().articles().createOrUpdate(articleEntities);
    }

    @Override
    public LiveData<Article> getById(int id) {
        return Transformations.map(getAppDatabase().articles().getById(id), Mapper::mapArticle);
    }

    @Override
    public LiveData<List<Article>> getPagedList() {
        return Transformations.map(getAppDatabase().articles().getPagedList(), x -> Stream.of(x).map(Mapper::mapArticle).toList());
    }

    @Override
    public void delete(@NonNull Article entity) {
        Validator.throwIfNull(entity, "Could not delete null entity.");

        getAppDatabase().articles().delete(Mapper.mapArticleEntity(entity));
    }

    @Override
    public LiveData<List<Article>> getByHeading(@NonNull String heading) {
        return Transformations.map(getAppDatabase().articles().getByHeading(heading), x -> Stream.of(x).map(Mapper::mapArticle).toList());
    }

    @Override
    public LiveData<List<Article>> search(@NonNull String query) {
        return Transformations.map(getAppDatabase().articles().search(query), x -> Stream.of(x).map(Mapper::mapArticle).toList());
    }
}
