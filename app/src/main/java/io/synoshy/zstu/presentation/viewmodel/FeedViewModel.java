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
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.synoshy.zstu.ZSTUApplication;
import io.synoshy.zstu.domain.entity.Article;
import io.synoshy.zstu.domain.manager.ArticleManager;
import io.synoshy.zstu.domain.util.Validator;

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
//        articles = articleManager.getList();
        articles = loadArticles();
    }

    public LiveData<List<Article>> getArticles() {
        return articles;
    }

    private LiveData<List<Article>> loadArticles() {
        MutableLiveData<List<Article>> result = new MutableLiveData<>();
        List<Article> articlesList = new ArrayList<>();

        Article article1 = new Article();
        article1.setHeading("Професійний розвиток викладача");
        article1.setContent("Викладач не тільки має навчати студентів, а й сам має навчатися...");
        DateFormat date1 = DateFormat.getDateTimeInstance();
        date1.getCalendar().set(2018, 1, 8, 10, 2);
        article1.setLastModified(date1.getCalendar().getTime());
        articlesList.add(article1);

        Article article2 = new Article();
        article2.setHeading("Запобігання корупції в Україні");
        article2.setContent("В ЖДТУ за сприяння ректора відбудеться семінар...");
        DateFormat date2 = DateFormat.getDateTimeInstance();
        date2.getCalendar().set(2018, 1, 12, 14, 20);
        article2.setLastModified(date2.getCalendar().getTime());
        articlesList.add(article2);

        Article article3 = new Article();
        article3.setHeading("Про подання претендентів на здобуття стипендій Кабінету Міністрів України");
        article3.setContent("До уваги студентів...");
        DateFormat date3 = DateFormat.getDateTimeInstance();
        date3.getCalendar().set(2018, 1, 15, 8, 43);
        article3.setLastModified(date3.getCalendar().getTime());
        articlesList.add(article3);

        result.setValue(articlesList);

        return result;
    }
}
