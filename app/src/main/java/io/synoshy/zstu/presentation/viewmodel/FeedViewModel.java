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

package io.synoshy.zstu.presentation.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.synoshy.zstu.R;
import io.synoshy.zstu.ZSTUApplication;
import io.synoshy.zstu.data.Constants;
import io.synoshy.zstu.domain.entity.Article;
import io.synoshy.zstu.domain.manager.ArticleManager;

public class FeedViewModel extends AndroidViewModel {

    @Inject
    ArticleManager articleManager;

    private LiveData<List<Article>> articles;

    public FeedViewModel(@NonNull Application application) {
        super(application);
        initialize();
    }

    private void initialize() {
        ((ZSTUApplication)getApplication()).getAppComponent().inject(this);
        articles = articleManager.getList();
    }

    public LiveData<List<Article>> getArticles() {
        return articles;
    }

    public void updateData(@Nullable Runnable callback) {
        List<Article> articles = this.articles.getValue();
        Date newerThan = null;
        if (articles != null && articles.size() > 0)
            newerThan = articles.get(0).getLastModified();

        articleManager.loadNewsFromNetwork(Constants.Network.NEWS_ULR, newerThan, x -> {
            Context context = getApplication().getApplicationContext();
            if (x == null) {
                Toast.makeText(context,
                        context.getText(R.string.error_load_news_from_network),
                        Toast.LENGTH_SHORT).show();
            }
            else if (x.size() > 0)
                articleManager.createOrUpdate(x.toArray(new Article[0]));

            if (callback != null)
                callback.run();

            return null;
        });
    }
}
