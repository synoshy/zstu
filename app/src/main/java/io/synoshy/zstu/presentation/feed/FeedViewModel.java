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

package io.synoshy.zstu.presentation.feed;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.annimon.stream.Stream;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.synoshy.zstu.R;
import io.synoshy.zstu.ZSTUApplication;
import io.synoshy.zstu.domain.Constants;
import io.synoshy.zstu.domain.article.ArticleManager;
import io.synoshy.zstu.domain.article.IArticle;
import io.synoshy.zstu.domain.common.lang.OneTimeRunnable;
import io.synoshy.zstu.domain.common.util.Validator;
import io.synoshy.zstu.presentation.article.Article;

public class FeedViewModel extends AndroidViewModel {

    @Inject
    ArticleManager articleManager;

    private LiveData<List<Article>> articles;

    private MutableLiveData<Boolean> showNoPostsMessage = new MutableLiveData<>();

    private OneTimeRunnable onFirstUpdate;

    public FeedViewModel(@NonNull Application application) {
        super(application);
        initialize();
    }

    private void initialize() {
        ((ZSTUApplication)getApplication()).getAppComponent().inject(this);
        articles = Transformations.map(articleManager.getList(),
                x -> Stream.of(x).map(Article::newInstance).toList());
    }

    public LiveData<List<Article>> getArticles() {
        return articles;
    }

    public MutableLiveData<Boolean> getShowNoPostsMessage() {
        return showNoPostsMessage;
    }

    public void updateData(@Nullable Runnable callback) {
        Validator.throwIfNull(articles, "View model was not initialized.");

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
                articleManager.createOrUpdate(x.toArray(new IArticle[0]));

            if (callback != null)
                callback.run();

            return null;
        });
    }

    public OneTimeRunnable getOnFirstUpdate() {
        return onFirstUpdate;
    }

    public void setOnFirstUpdate(OneTimeRunnable onFirstUpdate) {
        this.onFirstUpdate = onFirstUpdate;
    }
}
