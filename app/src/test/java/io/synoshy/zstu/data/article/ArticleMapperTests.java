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

import org.junit.Test;

import java.util.Date;

import io.synoshy.zstu.domain.article.Article;

public class ArticleMapperTests {

    @Test
    public void mapArticleEntity_SourceUrlNull_ResultUrlNull() {
        Article source = new Article();
        source.setContent("content");
        source.setHeading("heading");
        source.setLastModified(new Date());

        ArticleEntity result = ArticleMapper.mapArticleEntity(source);

        assert result.getContent().equals(source.getContent())
                && result.getHeading().equals(source.getHeading())
                && result.getLastModified().equals(source.getLastModified())
                && result.getUrl() == null;
    }

    @Test
    public void mapArticle_SourceUrlNull_ResultUrlNull() {
        ArticleEntity source = new ArticleEntity();
        source.setContent("content");
        source.setHeading("heading");
        source.setLastModified(new Date());

        Article result = ArticleMapper.mapArticle(source);

        assert result.getContent().equals("content")
                && result.getHeading().equals("heading")
                && result.getLastModified().equals(source.getLastModified())
                && result.getUrl() == null;
    }
}
