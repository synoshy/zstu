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

package io.synoshy.zstu.domain.common;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public interface Manager<T> {

    /**
     * Create entity collection.
     * @param entities Entities to be created or updated.
     */
    void createOrUpdate(@NonNull T... entities);

    /**
     * Update entity collection.
     * @param entities Entities to be updated.
     */
    void update(@NonNull T... entities);

    /**
     * Get an entity by id.
     * @param id Entity id.
     */
    LiveData<T> getById(String id);

    /**
     * Get a list of entities.
     */
    LiveData<List<T>> getList();

    /**
     * Delete an entity.
     * @param entity Entity to be deleted.
     */
    void delete(@NonNull T entity);
}
