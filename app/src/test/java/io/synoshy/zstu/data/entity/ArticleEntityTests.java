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

import org.junit.Test;

public class ArticleEntityTests {

    @Test
    public void equals_Null_False() {
        assert !new ArticleEntity().equals(null);
    }

    @Test
    public void equals_Empty_True() {
        assert new ArticleEntity().equals(new ArticleEntity());
    }

    @Test
    public void equals_DifferentIds_True() {
        ArticleEntity article1 = new ArticleEntity();
        article1.setId(0);
        article1.setContent("content");

        ArticleEntity article2 = new ArticleEntity();
        article2.setId(1);
        article2.setContent("content");

        assert article1.equals(article2);
    }

    @Test
    public void equals_DifferentHeadings_False() {
        ArticleEntity article1 = new ArticleEntity();
        article1.setHeading("0");

        ArticleEntity article2 = new ArticleEntity();
        article2.setHeading("1");

        assert !article1.equals(article2);
    }
}
