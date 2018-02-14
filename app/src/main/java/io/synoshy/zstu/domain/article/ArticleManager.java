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

package io.synoshy.zstu.domain.article;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import io.synoshy.zstu.domain.common.Manager;
import okhttp3.HttpUrl;

public interface ArticleManager extends Manager<IArticle> {

    /**
     * Get list of {@link IArticle} by heading.
     * @param heading Heading pattern.
     */
    LiveData<List<IArticle>> getByHeading(@NonNull String heading);

    /**
     * Get list of {@link IArticle} that matches search query.
     * @param query Search pattern.
     */
    LiveData<List<IArticle>> search(@NonNull String query);

    /**
     * Get list batch of {@link IArticle}.
     * @param batchNumber Batch number.
     * @param batchSize Batch size.
     */
    LiveData<List<IArticle>> getListBatch(int batchNumber, int batchSize);

    /**
     * Load list of {@link IArticle} from network to storage.
     * @param url Url to load data.
     * @param newerThan Bottom news last modified filter.
     * @param callback Callback that is executed after data is returned.
     */
    void loadNewsFromNetwork(@NonNull HttpUrl url, Date newerThan, @NonNull Function<List<IArticle>, Void> callback);
}
