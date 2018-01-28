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

import android.databinding.DataBindingComponent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import io.synoshy.zstu.R;
import io.synoshy.zstu.databinding.RowArticleBinding;
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowArticleBinding binding = RowArticleBinding.inflate(inflater);
        return new ArticleHolder(binding);
    }

    @Override
    public void onBindViewHolder(ArticleHolder holder, int position) {
        holder.getBinding().setArticle(articles.get(position));
        holder.getBinding().notifyChange();
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ArticleHolder extends RecyclerView.ViewHolder {

        private RowArticleBinding binding;

        private ImageView backgroundImage;

        public ArticleHolder(@NonNull RowArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            //backgroundImage = binding.getRoot().findViewById(R.id.image_background);
            initialize();
        }

        public RowArticleBinding getBinding() {
            return binding;
        }

        private void initialize() {
            //backgroundImage.setImageResource(R.color.row_article_background);
        }
    }
}
