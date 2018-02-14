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

package io.synoshy.zstu.domain.article;

import android.text.TextUtils;

import java.util.Date;

/**
 * Class that represents a news Article.
 */
public class Article implements IArticle {

    //region Fields

    private String id;

    private String heading;

    private String content;

    private String description;

    private String imageUrl;

    private Date lastModified;

    private String[] attachments;

    //endregion

    //region Field accessors

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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

    public String[] getAttachments() {
        return attachments;
    }

    public void setAttachments(String[] attachments) {
        this.attachments = attachments;
    }

    //endregion

    @Override
    public boolean equals(Object obj) {
        Article another = (Article)obj;
        if (another == null) return false;

        return TextUtils.equals(this.getHeading(), another.getHeading())
                && TextUtils.equals(this.getDescription(), another.getDescription())
                && TextUtils.equals(this.getContent(), another.getContent())
                && TextUtils.equals(this.getImageUrl(), another.getImageUrl())
                && (this.getLastModified() != null && this.getLastModified().equals(another.getLastModified())
                    || another.getLastModified() != null && another.getLastModified().equals(this.getLastModified()));
    }
}
