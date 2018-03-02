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

package io.synoshy.zstu.data.article;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ArticleDao {

    /**
     * Create or update collection of {@link Article}.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createOrUpdate(Article... articleEntities);

    /**
     * Update collection of {@link Article}.
     */
    @Update(onConflict = OnConflictStrategy.FAIL)
    void update(Article... articleEntities);

    /**
     * Get {@link Article} by id.
     */
    @Query("SELECT * FROM articles WHERE id = :id")
    LiveData<Article> getById(String id);

    /**
     * Get list of {@link Article}.
     */
    @Query("SELECT * FROM articles " +
            "ORDER BY lastModified DESC")
    LiveData<List<Article>> getList();

    /**
     * Get list batch of {@link Article}.
     */
    @Query("SELECT * FROM articles " +
            "ORDER BY lastModified DESC " +
            "LIMIT :batchSize " +
            "OFFSET :skipSize")
    LiveData<List<Article>> getListBatch(int skipSize, int batchSize);

    /**
     * Get list of {@link Article} by heading.
     * @param heading Heading pattern.
     */
    @Query("SELECT * FROM articles " +
            "WHERE heading LIKE :heading " +
            "ORDER BY lastModified DESC")
    LiveData<List<Article>> getByHeading(String heading);

    /**
     * Get list of {@link Article} that matches search query.
     * @param query Search pattern.
     */
    @Query("SELECT * FROM articles " +
            "WHERE heading LIKE :query OR content LIKE :query " +
            "ORDER BY lastModified DESC")
    LiveData<List<Article>> search(String query);

    /**
     * Delete collection of {@link Article}.
     */
    @Delete
    void delete(Article... articleEntities);
}
