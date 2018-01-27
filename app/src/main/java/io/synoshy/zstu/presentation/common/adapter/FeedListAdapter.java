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

package io.synoshy.zstu.presentation.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.synoshy.zstu.domain.entity.Article;
import io.synoshy.zstu.domain.util.Validator;

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.ArticleHolder> {

    private List<Article> articles;

    public FeedListAdapter(List<Article> articles) {
        this.articles = articles;
    }

    public void mergeChanges(List<Article> newValues) {
        Validator.throwIfNull(articles, "Could not merge null articles.");

        articles = newValues;
        notifyDataSetChanged();
    }

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ArticleHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ArticleHolder extends RecyclerView.ViewHolder {

        public ArticleHolder(View itemView) {
            super(itemView);
        }
    }
}
