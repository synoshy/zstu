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

package io.synoshy.zstu.domain.manager;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.synoshy.zstu.domain.entity.Article;

public interface ArticleManager extends Manager<Article> {

    /**
     * Get paged list of {@link Article} by heading.
     * @param heading Heading pattern.
     */
    LiveData<List<Article>> getByHeading(@NonNull String heading);

    /**
     * Get paged list of {@link Article} that matches search query.
     * @param query Search pattern.
     */
    LiveData<List<Article>> search(@NonNull String query);

    LiveData<List<Article>> getListBatch(int batchNumber, int batchSize);
}
