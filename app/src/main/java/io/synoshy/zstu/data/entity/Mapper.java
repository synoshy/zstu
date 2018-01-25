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

package io.synoshy.zstu.data.entity;

import io.synoshy.zstu.domain.entity.Article;

public class Mapper {

    public static ArticleEntity mapArticleEntity(Article article) {
        if (article == null)
            return null;

        ArticleEntity result = new ArticleEntity();
        result.setId(article.getId());
        result.setHeading(article.getHeading());
        result.setShortDescription(article.getShortDescription());
        result.setContent(article.getContent());
        result.setImageSrc(article.getImageSrc());
        result.setLastModified(article.getLastModified());

        return result;
    }

    public static Article mapArticle(ArticleEntity articleEntity) {
        if (articleEntity == null)
            return null;

        Article result = new Article();
        result.setId(articleEntity.getId());
        result.setHeading(articleEntity.getHeading());
        result.setShortDescription(articleEntity.getShortDescription());
        result.setContent(articleEntity.getContent());
        result.setImageSrc(articleEntity.getImageSrc());
        result.setLastModified(articleEntity.getLastModified());

        return result;
    }
}
