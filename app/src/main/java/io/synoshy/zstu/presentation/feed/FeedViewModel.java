/**
 * Copyright (c) 2018 Denys Zosimovych Open Source Project
 * <p>
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
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.synoshy.zstu.R;
import io.synoshy.zstu.ZSTUApplication;
import io.synoshy.zstu.domain.Constants;
import io.synoshy.zstu.domain.article.IArticleManager;
import io.synoshy.zstu.domain.article.IArticle;
import io.synoshy.zstu.domain.common.lang.OneTimeRunnable;
import io.synoshy.zstu.domain.common.util.Validator;
import io.synoshy.zstu.presentation.article.ArticleViewModel;

public class FeedViewModel extends AndroidViewModel {

    @Inject
    IArticleManager articleManager;

    private LiveData<List<IArticle>> articles;

    private MutableLiveData<Boolean> showNoPostsMessage;

    private Observer<List<IArticle>> articleExistenceChecker = x -> {
        if (x == null || x.size() == 0)
            showNoPostsMessage.postValue(true);
        else
            showNoPostsMessage.postValue(false);
    };

    private static final int VISIBLE = 0;

    private static final int INVISIBLE = 8;

    private OneTimeRunnable oncePerLifetime;

    public FeedViewModel(@NonNull Application application) {
        super(application);
        showNoPostsMessage = new MutableLiveData<>();
        initialize();
    }

    private void initialize() {
        ((ZSTUApplication) getApplication()).getAppComponent().inject(this);
        articles = articleManager.getList();
        articles.observeForever(articleExistenceChecker);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        articles.removeObserver(articleExistenceChecker);
    }

    @NonNull
    public List<ArticleViewModel> getArticles() {
        if (articles.getValue() == null)
            return new ArrayList<>();

        return Stream.of(articles.getValue())
                .map(x -> {
                    ArticleViewModel model = new ArticleViewModel(getApplication());
                    model.setArticle(x);
                    return model;
                }).toList();
    }

    public void observeArticles(LifecycleOwner lifecycleOwner, Observer observer) {
        articles.observe(lifecycleOwner, observer);
    }

    public int getNoPostsMessageVisibility() {
        Boolean showMessage = showNoPostsMessage.getValue();
        return showMessage != null && showMessage
                ? VISIBLE
                : INVISIBLE;
    }

    public void observeNoPostsMessageVisibility(@NonNull LifecycleOwner owner, @NonNull Observer<Boolean> observer) {
        showNoPostsMessage.observe(owner, observer);
    }

    public void updateData(@Nullable Runnable callback) {
        Validator.throwIfNull(articles, "View model was not initialized.");

        List<IArticle> articles = this.articles.getValue();
        Date newerThan = null;
        if (articles != null && articles.size() > 0)
            newerThan = articles.get(0).getLastModified();

        articleManager.loadNewsFromNetwork(Constants.Network.NEWS_ULR, newerThan, x -> {
            Context context = getApplication().getApplicationContext();
            if (x == null) {
                Toast.makeText(context,
                        context.getText(R.string.error_load_news_from_network),
                        Toast.LENGTH_SHORT).show();
            } else if (x.size() > 0)
                articleManager.createOrUpdate(x.toArray(new IArticle[0]));

            if (callback != null)
                callback.run();

            return null;
        });
    }

    /**
     *  Run after data list is loaded.
     */
    public void runOnFirstDataLoad(Runnable runnable) {
        if (oncePerLifetime != null)
            return;

        this.oncePerLifetime = new OneTimeRunnable(runnable);
        if (articles != null)
            articles.observeForever(x -> oncePerLifetime.run());
    }
}
