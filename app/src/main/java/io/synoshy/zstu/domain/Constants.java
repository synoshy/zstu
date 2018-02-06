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

package io.synoshy.zstu.domain;

import okhttp3.HttpUrl;

public class Constants {

    public static class Database {

        public static final int VERSION = 1;

        public static final String NAME = "ZSTU";
    }

    public static class Network {

        public static final HttpUrl NEWS_ULR = HttpUrl.parse("https://ztu.edu.ua/");
    }

    public static class Locale {

        public static final String DATE_TIME_FORMAT = "dd.MM.yyyy hh:mm";

        public static final java.util.Locale UKRAINIAN = new java.util.Locale("uk", "UA");
    }
}
