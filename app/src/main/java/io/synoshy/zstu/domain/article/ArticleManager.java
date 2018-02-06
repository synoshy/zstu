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

public interface ArticleManager extends Manager<Article> {

    /**
     * Get list of {@link Article} by heading.
     * @param heading Heading pattern.
     */
    LiveData<List<Article>> getByHeading(@NonNull String heading);

    /**
     * Get list of {@link Article} that matches search query.
     * @param query Search pattern.
     */
    LiveData<List<Article>> search(@NonNull String query);

    /**
     * Get list batch of {@link Article}.
     * @param batchNumber Batch number.
     * @param batchSize Batch size.
     */
    LiveData<List<Article>> getListBatch(int batchNumber, int batchSize);

    /**
     * Load list of {@link Article} from network.
     * @param url Url to load data.
     */
    void loadNewsFromNetwork(@NonNull HttpUrl url, Date newerThan, @NonNull Function<List<Article>, Void> callback);
}
