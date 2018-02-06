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

package io.synoshy.zstu.data.common;

import android.arch.persistence.room.*;

import io.synoshy.zstu.data.article.ArticleEntity;
import io.synoshy.zstu.domain.Constants;
import io.synoshy.zstu.data.article.ArticleDao;

import android.arch.persistence.room.TypeConverters;

@Database(entities = ArticleEntity.class, version = Constants.Database.VERSION)
@TypeConverters(PersistedTypeConverters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ArticleDao articles();
}
