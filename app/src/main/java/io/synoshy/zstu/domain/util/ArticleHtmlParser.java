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

package io.synoshy.zstu.domain.util;

import android.support.annotation.NonNull;

import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.synoshy.zstu.domain.Constants;
import io.synoshy.zstu.domain.entity.Article;
import io.synoshy.zstu.domain.exception.ExceptionWrapper;

public class ArticleHtmlParser {

    private ArticleHtmlParser() {}

    public static Article parseArticle(@NonNull Element rootNode) {
        Article result = new Article();

        Element heading = rootNode.selectFirst("h4 a");
        if (heading != null) {
            result.setHeading(heading.text());
            result.setUrl(heading.attr("href"));
        }

        Element image = rootNode.selectFirst("img");
        if (image != null)
            result.setImageSrc(image.attr("src"));

        Element time = rootNode.selectFirst("p.time");
        String timeString;
        if (time != null && (timeString = time.text()) != null) {
            ExceptionWrapper.execute(() -> {
                Date date = new SimpleDateFormat(Constants.Locale.DATE_TIME_FORMAT, Constants.Locale.UKRAINIAN).parse(timeString);
                result.setLastModified(date);
            });
        }

        Element description = rootNode.selectFirst("div p:not(.time)");
        if (description != null)
            result.setDescription(description.text());

        return result;
    }
}
