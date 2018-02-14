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

package io.synoshy.zstu.data.article;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

import io.synoshy.zstu.presentation.feed.Article;


public class ArticleHtmlParserTests {

    @Test(expected = NullPointerException.class)
    public void parseArticle_NullElement_ExceptionThrown() {
        ArticleHtmlParser.parseArticle(null);
    }

    @Test
    public void parseArticle_NoImageTag_ImageSrcNull() {
        String articleNode =
                "<div class=\"news-container\">" +
                        "<h4><a href=\"link\">heading</a></h4>" +
                        "<div>\n" +
                            "<p class=\"time\">29.01.2018 00:00</p>" +
                            "<p>description</p>" +
                        "</div>" +
                "</div>";

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 1, 29, 0, 0);

        Article article = ArticleHtmlParser.parseArticle(Jsoup.parse(articleNode));

        assert article.getHeading().equals("heading")
                && article.getDescription().equals("description")
                && article.getLastModified().equals(calendar.getTime())
                && article.getImageUrl() == null;
    }

    @Test
    public void parseArticle_DMYFormat_CorrectDate() {
        String articleNode =
                "<div class=\"news-container\">" +
                        "<h4><a href=\"link\">heading</a></h4>" +
                        "<div>\n" +
                            "<p class=\"time\">02.01.2018 00:00</p>" +
                            "<p>description</p>" +
                        "</div>" +
                "</div>";

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 1, 2, 0, 0);

        Article article = ArticleHtmlParser.parseArticle(Jsoup.parse(articleNode));

        assert article.getLastModified().equals(calendar.getTime());
    }

    @Test
    public void selectArticleNodes_ArticleElements_ExpectedElementsSize()
    {
        String articleNode =
                "<div class=\"news-container\">" +
                        "<h4><a href=\"https://yep.ztu.edu.ua/2018/01/rozpochato-novyj-nabir-biznes-inkubatoriv-yep/\">Розпочато новий набір до бізнес-інкубаторів YEP</a></h4>" +
                        "<img src=\"https://news.ztu.edu.ua/wp-content/uploads/2018/01/25552132_423239674775030_5875229020950523142_n.jpg\" width=\"179\" height=\"140\" alt=\"https://news.ztu.edu.ua/wp-content/uploads/2018/01/25552132_423239674775030_5875229020950523142_n.jpg\" class=\"attachment-post-thumbnail size-post-thumbnail wp-post-image\">" +
                        "<div>\n" +
                        "<p class=\"time\">29.01.2018 20:14</p><p>ТАК! Ми анонсуємо набір на YEP Starter! YEP Starter – нова українсько-естонська програма з підприємництва, яка дасть тобі необхідні знання для відкриття власної справи та змінить твоє життя назавжди! Найкращі експерти з України та Естонії допоможуть українським студентам перетворити ідеї у стартапи всього за 3 місяці! …</p>" +
                        "</div>" +
                "</div>";

        String invalidNode =
                "<div class=\"newws-container\">" +
                        "<h4><a href=\"https://yep.ztu.edu.ua/2018/01/rozpochato-novyj-nabir-biznes-inkubatoriv-yep/\">Розпочато новий набір до бізнес-інкубаторів YEP</a></h4>" +
                        "<img src=\"https://news.ztu.edu.ua/wp-content/uploads/2018/01/25552132_423239674775030_5875229020950523142_n.jpg\" width=\"179\" height=\"140\" alt=\"https://news.ztu.edu.ua/wp-content/uploads/2018/01/25552132_423239674775030_5875229020950523142_n.jpg\" class=\"attachment-post-thumbnail size-post-thumbnail wp-post-image\">" +
                        "<div>\n" +
                        "<p class=\"time\">29.01.2018 20:14</p><p>ТАК! Ми анонсуємо набір на YEP Starter! YEP Starter – нова українсько-естонська програма з підприємництва, яка дасть тобі необхідні знання для відкриття власної справи та змінить твоє життя назавжди! Найкращі експерти з України та Естонії допоможуть українським студентам перетворити ідеї у стартапи всього за 3 місяці! …</p>" +
                        "</div>" +
                "</div>";

        ArticleElementTestData[] testData = new ArticleElementTestData[] {
                new ArticleElementTestData(articleNode + invalidNode + invalidNode, 1),
                new ArticleElementTestData(invalidNode + invalidNode, 0),
                new ArticleElementTestData(invalidNode + articleNode + articleNode + articleNode + articleNode + invalidNode, 4)
        };

        for (ArticleElementTestData data : testData)
            Assert.assertEquals(ArticleHtmlParser.selectArticleNodes(data.html).size(), data.expectedElementsSize);
    }

    class ArticleElementTestData {

        public Document html;

        public int expectedElementsSize;

        public ArticleElementTestData(String html, int expectedElementsSize) {
            this.html = Jsoup.parse(html);
            this.expectedElementsSize = expectedElementsSize;
        }
    }
}
