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

package io.synoshy.zstu.data.cache;

import android.support.annotation.NonNull;

import io.synoshy.zstu.domain.cache.DiskCache;
import okhttp3.internal.DiskLruCache;

public class DiskCacheImpl implements DiskCache {

    private DiskLruCache imageCache;

    public DiskCacheImpl(@NonNull DiskLruCache imageCache) {
        this.imageCache = imageCache;
    }

    @Override
    public DiskLruCache images() {
        return imageCache;
    }
}
