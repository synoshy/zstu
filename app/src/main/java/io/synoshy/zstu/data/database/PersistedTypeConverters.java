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

package io.synoshy.zstu.data.database;

import java.text.DateFormat;
import java.util.Date;
import android.arch.persistence.room.TypeConverter;

public class PersistedTypeConverters {

    @TypeConverter
    public static DateFormat fromTimestamp(Long timestamp) {
        if (timestamp == null)
            return null;

        DateFormat result = DateFormat.getDateTimeInstance();
        result.getCalendar().setTimeInMillis(timestamp);
        return result;
    }

    @TypeConverter
    public static Long toTimestamp(DateFormat date) {
        return date == null
                ? null
                : date.getCalendar().getTimeInMillis();
    }
}
