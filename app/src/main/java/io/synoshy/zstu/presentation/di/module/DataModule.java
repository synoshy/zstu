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

package io.synoshy.zstu.presentation.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.synoshy.zstu.domain.Constants;
import io.synoshy.zstu.data.article.ArticleApi;
import io.synoshy.zstu.data.article.ArticleManagerImpl;
import io.synoshy.zstu.data.common.AppDatabase;
import io.synoshy.zstu.data.common.HtmlElementAdapter;
import io.synoshy.zstu.domain.article.ArticleManager;
import retrofit2.Retrofit;

@Module
public class DataModule {

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, Constants.Database.NAME)
                .build();
    }

    @Provides
    @Singleton
    ArticleManager provideArticleManager(AppDatabase appDatabase, ArticleApi articleLoader) {
        return new ArticleManagerImpl(appDatabase, articleLoader);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.Network.NEWS_ULR)
                .addConverterFactory(HtmlElementAdapter.FACTORY)
                .build();
    }

    @Provides
    @Singleton
    ArticleApi provideArticleApi(Retrofit retrofit) {
        return retrofit.create(ArticleApi.class);
    }
}
