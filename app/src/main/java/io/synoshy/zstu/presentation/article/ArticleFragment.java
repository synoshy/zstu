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

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.synoshy.zstu.R;
import io.synoshy.zstu.databinding.FragmentArticleBinding;
import io.synoshy.zstu.domain.common.util.Validator;
import io.synoshy.zstu.presentation.common.FragmentBase;
import io.synoshy.zstu.presentation.common.HasBinding;

public class ArticleFragment extends FragmentBase implements HasBinding {

    private ArticleViewModel model;

    private String articleId;

    private FragmentArticleBinding binding;

    private Observer bindingInvalidator = x -> binding.invalidateAll();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article, container, false);
        binding.setModel(model);

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Override
    public void onStart() {
        super.onStart();
        Validator.throwIfEmptyString(articleId, "Null article id was passed.");

        model.loadArticle(articleId);
        model.addModelObserver(this, bindingInvalidator);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("articleId", articleId);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey("articleId"))
            articleId = savedInstanceState.getString("articleId");
    }

    private void initialize() {
        model = ViewModelProviders.of(this).get(ArticleViewModel.class);
        model.setBtnBackClickHandler(x -> {
            if (getActivity() instanceof ArticleControl)
                ((ArticleControl) getActivity()).hideArticle();
            else {
                FragmentManager fm = getFragmentManager();
                if (fm != null) {
                    fm.beginTransaction()
                            .remove(this)
                            .commit();
                }
            }
        });

        Bundle args = getArguments();
        if (args != null && args.containsKey("articleId"))
            articleId = args.getString("articleId");
    }

    @Override
    public ViewDataBinding getBinding() {
        return binding;
    }
}
