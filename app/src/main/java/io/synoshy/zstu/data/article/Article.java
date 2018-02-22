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

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.annimon.stream.Stream;

import java.util.Date;
import java.util.UUID;

import io.synoshy.zstu.domain.article.IArticle;
import io.synoshy.zstu.domain.article.IArticleContent;

@Entity(tableName = "articles",
        indices = {
            @Index(name = "index_article_id", value = "id", unique = true),
            @Index(name = "index_article_search", value = "heading")
        })
public class Article implements IArticle {

    //region Fields

    @PrimaryKey
    @ColumnInfo(index = true)
    @NonNull
    private String id;

    @ColumnInfo(index = true)
    private String heading;

    @ColumnInfo(index = true)
    private ArticleContent[] content;

    private String description;

    private String imageUrl;

    private Date lastModified;

    //endregion

    public Article() {
        id = UUID.randomUUID().toString();
    }

    //region Field accessors

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public ArticleContent[] getContent() {
        return content;
    }

    public void setContent(ArticleContent[] content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    //endregion

    public static Article newInstance(IArticle proto) {
        if (proto == null)
            return null;

        Article result = new Article();
        result.id = proto.getId();
        result.heading = proto.getHeading();
        if (proto.getContent() == null)
            result.content = null;
        else {
            result.content = Stream.of(proto.getContent())
                    .map(ArticleContent::newInstance)
                    .toArray(ArticleContent[]::new);
        }

        result.description = proto.getDescription();
        result.imageUrl = proto.getImageUrl();
        result.lastModified = proto.getLastModified();

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Article))
            return false;

        Article another = (Article)obj;
//        return getId().equals(another.getId()); ---- when API is implemented
        return getHeading().equals(another.getHeading());
    }
}
