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
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.synoshy.zstu.ZSTUApplication;
import io.synoshy.zstu.domain.article.ArticleManager;

public class ArticleViewModel extends AndroidViewModel {

    @Inject
    ArticleManager articleManager;

    private LiveData<Article> article;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        ((ZSTUApplication)getApplication()).getAppComponent().inject(this);
    }

    public LiveData<Article> getArticle() {
        return article;
    }

    public void loadArticle(String articleId) {
        article = Transformations.map(articleManager.getById(articleId), Article::newInstance);
    }
}
