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

package io.synoshy.zstu.data.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.synoshy.zstu.data.database.AppDatabase;
import io.synoshy.zstu.data.manager.ArticleManagerImpl;
import io.synoshy.zstu.data.network.ArticleLoader;
import io.synoshy.zstu.data.network.adapter.HtmlElementAdapter;
import io.synoshy.zstu.data.Constants;
import io.synoshy.zstu.domain.manager.ArticleManager;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    ArticleManager provideArticleManager(AppDatabase appDatabase, ArticleLoader articleLoader) {
        return new ArticleManagerImpl(appDatabase, articleLoader);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://ztu.edu.ua/")
                .addConverterFactory(HtmlElementAdapter.FACTORY)
                .build();
    }

    @Provides
    @Singleton
    ArticleLoader provideArticleLoader(Retrofit retrofit) {
        return retrofit.create(ArticleLoader.class);
    }
}
