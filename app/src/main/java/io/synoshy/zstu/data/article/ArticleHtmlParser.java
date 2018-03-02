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

import android.support.annotation.NonNull;

import com.annimon.stream.Stream;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.synoshy.zstu.domain.Constants;
import io.synoshy.zstu.domain.article.ArticleContentType;
import io.synoshy.zstu.domain.common.util.ExceptionWrapper;

public class ArticleHtmlParser {

    private ArticleHtmlParser() {
    }

    public static Article parseArticlePreview(@NonNull Element articlePreviewNode) {
        Article result = new Article();

        Element heading = articlePreviewNode.selectFirst("header a");
        if (heading != null)
            result.setHeading(heading.text());

        Element image = articlePreviewNode.selectFirst(".post-thumbnail img");
        if (image != null)
            result.setImageUrl(image.attr("src"));

        Element time = articlePreviewNode.selectFirst("footer time");
        String timeString;
        if (time != null && (timeString = time.attr("datetime")) != null) {
            ExceptionWrapper.execute(() -> {
                Date date = new SimpleDateFormat(Constants.Locale.DATE_TIME_FORMAT, Constants.Locale.UKRAINIAN).parse(timeString);
                result.setLastModified(date);
            });
        }

        Element description = articlePreviewNode.selectFirst(".entry-content p");
        if (description != null)
            result.setDescription(description.text());

        Element url = articlePreviewNode.selectFirst("a.post-thumbnail");
        if (url != null)
            result.setUrl(url.attr("href"));

        return result;
    }

    public static Elements selectArticlePreviewNodes(@NonNull Element body) {
        return body.select("article.post");
    }

    public static ArticleContent[] parseArticleContent(@NonNull Element articleNode) {
        Element contentNode = articleNode.selectFirst(".entry-content");
        Elements paragraphs = contentNode.select("p, ul, dl, ol, .su-slider .su-slider-slide a");

        List<ArticleContent> result = new ArrayList<>();
        for (Element e : paragraphs)
            result.addAll(processNode(e));

        return result.toArray(new ArticleContent[0]);
    }

    private static List<ArticleContent> processNode(@NonNull Element node) {
        Elements images = node.select("img");
        if (images.size() == 0) {
            List<ArticleContent> result = new ArrayList<>(1);
            result.add(new ArticleContent(ArticleContentType.Text, node.text().trim()));
            return result;
        }

        return Stream.of(images)
                .map(ArticleHtmlParser::processImage)
                .toList();
    }

    private static ArticleContent processImage(@NonNull Element node) {
        return new ArticleContent(ArticleContentType.Image, node.attr("src"));
    }

    public static Element selectArticleNode(@NonNull Element body) {
        return body.selectFirst("article.post");
    }
}
