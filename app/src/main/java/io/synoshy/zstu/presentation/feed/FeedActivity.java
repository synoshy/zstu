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
import android.content.res.Configuration;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.synoshy.zstu.R;
import io.synoshy.zstu.domain.article.Article;
import io.synoshy.zstu.domain.article.ArticleManager;
import io.synoshy.zstu.presentation.common.ActivityBase;
import io.synoshy.zstu.presentation.common.decoration.OffsetDecoration;
import io.synoshy.zstu.presentation.menu.MenuButton;
import io.synoshy.zstu.presentation.menu.MenuControl;
import io.synoshy.zstu.presentation.menu.MenuFragment;

public class FeedActivity extends ActivityBase
        implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, MenuControl
{

    @Inject
    ArticleManager articleManager;

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

    private boolean isMenuShown = false;

    private MenuFragment menuFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        performInjections();
        initialize();
    }

    private void performInjections() {
        ButterKnife.bind(this);
        getAppComponent().inject(this);
    }

    private void initialize() {
        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        if (!feedViewModel.getWasInitializedBefore()) {
            feedViewModel.getShowNoPostsMessage().observe(this,
                    x -> swipeToUpdateInfo.setVisibility(x ? View.VISIBLE : View.GONE));
        }

        if (!menuButton.isInitialized()) {
            menuButton.initialize((AnimatedVectorDrawable) hamburgerToCrossIcon,
                    (AnimatedVectorDrawable) crossToHamburgerIcon);
            menuButton.setOnClickListener(this);
        }

        List<Article> articles = feedViewModel.getArticles().getValue();
        if (articles == null)
            articles = new ArrayList<>();

        feedListAdapter = new FeedListAdapter(articles);

        feedList.setLayoutManager(new LinearLayoutManager(this));
        feedList.setAdapter(feedListAdapter);
        feedList.addItemDecoration(new OffsetDecoration(horizontalRowOffset, verticalRowOffset));

        swipeRefreshLayout.setColorSchemeResources(R.color.zstu_blue, R.color.zstu_yellow);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(true, startSpinnerOffset, endSpinnerOffset);

        if (!feedViewModel.getWasInitializedBefore()) {
            swipeRefreshLayout.setRefreshing(true);
            feedViewModel.updateData(() -> {
                swipeRefreshLayout.setRefreshing(false);
                feedViewModel.getArticles().observe(this, x -> {
                    feedListAdapter.mergeChanges(x);
                    feedViewModel.getShowNoPostsMessage().postValue(x.size() == 0);
                });
            });
            feedViewModel.setWasInitializedBefore(true);
        }
    }

    @Override
    public void onRefresh() {
        feedViewModel.updateData(() -> swipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        feedListAdapter.notifyDataSetChanged();
    }

    /**
    *   Menu button on click handler.
    */
    @Override
    public void onClick(View view) {
        menuButton.switchState();
        if (!isMenuShown)
            showMenu();
        else
            hideMenu();
    }

    public void showMenu() {
        isMenuShown = true;

        if (menuFragment == null)
            menuFragment = new MenuFragment();

        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.container, menuFragment)
                .addToBackStack(null)
                .commit();
    }

    public void hideMenu() {
        isMenuShown = false;

        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .remove(menuFragment)
                .addToBackStack(null)
                .commit();
    }
}
