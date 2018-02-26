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

package io.synoshy.zstu.presentation.common.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

public class LinesLeadingMarginSpan2 implements LeadingMarginSpan.LeadingMarginSpan2 {

    private int spanLines;

    private int spanSize;

    public LinesLeadingMarginSpan2(int spanLines, int spanSize) {
        this.spanLines = spanLines;
        this.spanSize = spanSize;
    }

    @Override
    public int getLeadingMarginLineCount() {
        return spanLines;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return first
                ? spanSize
                : 0;
    }

    @Override
    public void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i1, int i2, int i3, int i4,
                                  CharSequence charSequence, int i5, int i6, boolean b,
                                  Layout layout) {
    }
}
