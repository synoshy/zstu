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

package io.synoshy.zstu.presentation.article;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import com.annimon.stream.Stream;

import java.text.DateFormat;

import javax.inject.Inject;

import io.synoshy.zstu.ZSTUApplication;
import io.synoshy.zstu.domain.article.ArticleContentType;
import io.synoshy.zstu.domain.article.ArticleManager;
import io.synoshy.zstu.domain.article.IArticle;
import io.synoshy.zstu.domain.article.IArticleContent;
import io.synoshy.zstu.domain.common.util.Validator;

public class ArticleViewModel extends AndroidViewModel {

    @Inject
    ArticleManager articleManager;

    private LiveData<IArticle> articleModel;

    private IArticle getArticle() {
        return articleModel == null
                ? null
                : articleModel.getValue();
    }

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        ((ZSTUApplication) getApplication()).getAppComponent().inject(this);
    }

    public void loadArticle(String articleId) {
        articleModel = articleManager.getById(articleId);
    }

    public void setArticle(IArticle article) {
        MutableLiveData<IArticle> newModel = new MutableLiveData<>();
        newModel.setValue(article);
        this.articleModel = newModel;
    }

    public String getId() {
        IArticle article = getArticle();
        return article == null
                ? null
                : article.getId();
    }

    public String getHeading() {
        IArticle article = getArticle();
        return article == null
                ? null
                : article.getHeading();
    }

    public String getDescription() {
        IArticle article = getArticle();
        return article == null
                ? null
                : article.getDescription();
    }

    public String getLastModified() {
        DateFormat format = DateFormat.getDateTimeInstance();
        IArticle article = getArticle();
        return article == null
                ? null
                : format.format(article.getLastModified());
    }

    public String getImageUrl() {
        IArticle article = getArticle();
        return article == null
                ? null
                : article.getImageUrl();
    }

    /**
     * Get inflatable data of the content text and image mix
     */
    public InflatableData[] getInflatableData() {
        IArticle article = getArticle();
        if (article == null)
            return null;

        IArticleContent[] content = article.getContent();
        if (content == null)
            return null;

        return Stream.of(content)
                .map(this::createInflatableData)
                .toArray(InflatableData[]::new);
    }

    private InflatableData createInflatableData(IArticleContent articleContent) {
        if (articleContent == null)
            return null;

        switch (articleContent.getType()) {
            case ArticleContentType.Text:
                return new InflatableText(articleContent.getValue());

            case ArticleContentType.Image:
                return new InflatableImage(articleContent.getValue());

            default:
                throw new UnsupportedOperationException("Unknown content type.");
        }
    }

    public void addModelObserver(LifecycleOwner lifecycleOwner, Observer observer) {
        Validator.throwIfNull(articleModel, "Model is not initialized.");

        articleModel.observe(lifecycleOwner, observer);
    }
}
