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

import android.app.FragmentTransaction;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.synoshy.zstu.R;
import io.synoshy.zstu.databinding.ActivityFeedBinding;
import io.synoshy.zstu.presentation.article.ArticleControl;
import io.synoshy.zstu.presentation.article.ArticleFragment;
import io.synoshy.zstu.presentation.common.ActivityBase;
import io.synoshy.zstu.presentation.common.decoration.OffsetDecoration;
import io.synoshy.zstu.presentation.menu.MenuButton;
import io.synoshy.zstu.presentation.menu.MenuControl;
import io.synoshy.zstu.presentation.menu.MenuFragment;

public class FeedActivity extends ActivityBase
        implements SwipeRefreshLayout.OnRefreshListener, MenuControl, ArticleControl {

    @BindView(R.id.feed_list)
    RecyclerView feedList;

    @BindView(R.id.info_swipe_to_update)
    View swipeToUpdateInfo;

    @BindView(R.id.btn_menu)
    MenuButton menuButton;

    @BindDrawable(R.drawable.anim_hamburger_cross)
    Drawable hamburgerToCrossIcon;

    @BindDrawable(R.drawable.anim_cross_hamburger)
    Drawable crossToHamburgerIcon;

    @BindDimen(R.dimen.row_feed_offset_horizontal)
    int horizontalRowOffset;

    @BindDimen(R.dimen.row_feed_offset_vertical)
    int verticalRowOffset;

    @BindDimen(R.dimen.spinner_offset_start)
    int startSpinnerOffset;

    @BindDimen(R.dimen.spinner_offset_end)
    int endSpinnerOffset;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private FeedViewModel feedViewModel;

    private FeedListAdapter feedListAdapter;

    private ActivityFeedBinding binding;

    //region State values

    private boolean isMenuShown = false;

    private boolean isArticleShown = false;

    private String shownArticleId;

    //endregion

    private MenuFragment menuFragment;

    private ArticleFragment articleFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        feedViewModel.observeNoPostsMessageVisibility(this, x -> binding.invalidateAll());
        feedViewModel.observeArticles(this, x -> feedListAdapter.mergeChanges(feedViewModel.getArticles()));

        menuButton.initialize((AnimatedVectorDrawable) hamburgerToCrossIcon,
                (AnimatedVectorDrawable) crossToHamburgerIcon);
        menuButton.setOnClickListener(x -> {
            if (!isMenuShown)
                showMenu();
            else
                hideMenu();
        });

        if (menuFragment == null)
            menuFragment = new MenuFragment();

        if (articleFragment == null)
            articleFragment = new ArticleFragment();

        feedListAdapter = new FeedListAdapter(feedViewModel.getArticles());
        feedListAdapter.setOnItemClickListener(x -> showArticleInfo(x.getId()));
        feedList.setLayoutManager(new LinearLayoutManager(this));
        feedList.setAdapter(feedListAdapter);
        feedList.addItemDecoration(new OffsetDecoration(horizontalRowOffset, verticalRowOffset));

        binding.setModel(feedViewModel);

        swipeRefreshLayout.setColorSchemeResources(R.color.zstu_blue, R.color.zstu_yellow);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(true, startSpinnerOffset, endSpinnerOffset);

        feedViewModel.runOnFirstDataLoad(() -> {
            swipeRefreshLayout.setRefreshing(true);
            feedViewModel.updateData(() -> swipeRefreshLayout.setRefreshing(false));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isMenuShown) {
            if (menuFragment == null)
                menuFragment = new MenuFragment();

            isMenuShown = false;    //connected with showMenu() logic
            showMenu();
        }
        else
            menuButton.resetState();

        if (isArticleShown) {
            if (articleFragment == null)
                articleFragment = new ArticleFragment();

            isArticleShown = false; //connected with showArticle() logic
            showArticle(shownArticleId);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isMenuShown) {
            hideMenu();
            menuFragment = null;
            isMenuShown = true;
        }

        if (isArticleShown) {
            hideArticle();
            articleFragment = null;
            isArticleShown = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isMenuShown", isMenuShown);
        outState.putBoolean("isArticleShown", isArticleShown);
        outState.putString("shownArticleId", shownArticleId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("isMenuShown"))
                isMenuShown = savedInstanceState.getBoolean("isMenuShown");

            if (savedInstanceState.containsKey("isArticleShown"))
                isArticleShown = savedInstanceState.getBoolean("isArticleShown");

            if (savedInstanceState.containsKey("shownArticleId"))
                shownArticleId = savedInstanceState.getString("shownArticleId");
        }
    }

    @Override
    public void onRefresh() {
        feedViewModel.updateData(() -> swipeRefreshLayout.setRefreshing(false));
    }

    public void showMenu() {
        if (hasDisplayedFragments())
            return;

        isMenuShown = true;

        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.menu_container, menuFragment)
                .addToBackStack(null)
                .commit();

        menuButton.switchState();
    }

    public void hideMenu() {
        isMenuShown = false;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .remove(menuFragment)
                .commit();

        fragmentManager.popBackStack();
        menuButton.switchState();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isMenuShown) {
            menuButton.resetState();
            isMenuShown = false;
        }

        if (isArticleShown) {
            isArticleShown = false;
        }
    }

    public void showArticleInfo(@NonNull String articleId) {
        if (isArticleShown)
            hideArticle();

        showArticle(articleId);
    }

    @Override
    public void showArticle(String articleId) {
        if (articleId == null)
            throw new RuntimeException("Failed to show details for null article id.");

        if (hasDisplayedFragments())
            return;

        isArticleShown = true;
        shownArticleId = articleId;

        Bundle params = new Bundle();
        params.putString("articleId", articleId);
        articleFragment.setArguments(params);

        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.article_container, articleFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void hideArticle() {
        if (!isArticleShown)
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .remove(articleFragment)
                .commit();

        fragmentManager.popBackStack();

        isArticleShown = false;
    }

    private boolean hasDisplayedFragments() {
        return isArticleShown || isMenuShown;
    }
}
