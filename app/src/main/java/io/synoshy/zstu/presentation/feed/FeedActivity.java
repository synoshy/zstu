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

package io.synoshy.zstu.presentation.feed;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.synoshy.zstu.R;
import io.synoshy.zstu.domain.article.Article;
import io.synoshy.zstu.domain.article.ArticleManager;
import io.synoshy.zstu.presentation.common.ActivityBase;
import io.synoshy.zstu.presentation.common.decoration.OffsetDecoration;

public class FeedActivity extends ActivityBase implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    ArticleManager articleManager;

    @BindView(R.id.feed_list)
    RecyclerView feedList;

    @BindDimen(R.dimen.row_feed_offset_horizontal)
    int horizontalRowOffset;

    @BindDimen(R.dimen.row_feed_offset_vertical)
    int verticalRowOffset;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private FeedViewModel feedViewModel;

    private FeedListAdapter feedListAdapter;

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
        feedViewModel.initialize();

        List<Article> articles = feedViewModel.getArticles().getValue();
        if (articles == null)
            articles = new ArrayList<>();

        feedListAdapter = new FeedListAdapter(articles);
        feedList.setLayoutManager(new LinearLayoutManager(this));
        feedList.setAdapter(feedListAdapter);
        feedList.addItemDecoration(new OffsetDecoration(horizontalRowOffset, verticalRowOffset));

        feedViewModel.getArticles().observe(this, x -> feedListAdapter.mergeChanges(x));

        swipeRefreshLayout.setColorSchemeResources(R.color.zstu_blue, R.color.zstu_yellow);
        swipeRefreshLayout.setOnRefreshListener(this);
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
}
