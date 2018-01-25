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

package io.synoshy.zstu.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.synoshy.zstu.data.entity.ArticleEntity;

@Dao
public interface ArticleDao {

    /**
    * Create or update collection of {@link ArticleEntity}.
    */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createOrUpdate(ArticleEntity... articleEntities);

    /**
     * Get {@link ArticleEntity} by id.
     */
    @Query("SELECT * FROM articles WHERE id = :id")
    LiveData<ArticleEntity> getById(int id);

    /**
     * Get paged list of {@link ArticleEntity}.
     */
    @Query("SELECT * FROM articles " +
            "ORDER BY lastModified DESC")
    LiveData<List<ArticleEntity>> getPagedList();

    /**
     * Get paged list of {@link ArticleEntity} by heading.
     * @param heading Heading pattern.
     */
    @Query("SELECT * FROM articles " +
            "WHERE heading LIKE :heading " +
            "ORDER BY lastModified DESC")
    LiveData<List<ArticleEntity>> getByHeading(String heading);

    /**
     * Get paged list of {@link ArticleEntity} that matches search query.
     * @param query Search pattern.
     */
    @Query("SELECT * FROM articles " +
            "WHERE heading LIKE :query OR content LIKE :query " +
            "ORDER BY lastModified DESC")
    LiveData<List<ArticleEntity>> search(String query);

    /**
     * Delete collection of {@link ArticleEntity}.
     */
    @Delete
    void delete(ArticleEntity... articleEntities);
}
