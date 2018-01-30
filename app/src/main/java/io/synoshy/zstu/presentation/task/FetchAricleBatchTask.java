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

package io.synoshy.zstu.presentation.task;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import io.synoshy.zstu.domain.entity.Article;
import io.synoshy.zstu.domain.manager.ArticleManager;

public class FetchAricleBatchTask extends AsyncTask<FetchAricleBatchTask.Params, Void, LiveData<List<Article>>> {

    @Override
    protected LiveData<List<Article>> doInBackground(Params... params) {
        Params param = params[0];
        return param.getArticleManager().getListBatch(param.getPage(), param.getPageSize());
    }

    public class Params {

        private int page;

        private int pageSize;

        private ArticleManager articleManager;

        public Params(ArticleManager articleManager, int page, int pageSize) {
            this.articleManager = articleManager;
            this.page = page;
            this.pageSize = pageSize;
        }

        public ArticleManager getArticleManager() {
            return articleManager;
        }

        public int getPage() {
            return page;
        }

        public int getPageSize() {
            return pageSize;
        }
    }
}
