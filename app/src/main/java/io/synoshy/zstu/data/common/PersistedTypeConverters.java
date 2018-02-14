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

import java.util.Date;
import android.arch.persistence.room.TypeConverter;

public class PersistedTypeConverters {

    @TypeConverter
    public static Date fromTimestamp(Long timestamp) {
        if (timestamp == null)
            return null;

        return new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null
                ? null
                : date.getTime();
    }

    @TypeConverter
    public static String toStringArrayXml(String[] array) {
        if (array == null)
            return null;

        return JsonUtil.serialize(array);
    }

    @TypeConverter
    public static String[] fromStringArrayXml(String xml) {
        if (xml == null)
            return null;

        return JsonUtil.deserialize(xml, String[].class);
    }
}
