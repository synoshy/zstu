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

package io.synoshy.zstu.presentation.common.text;

import android.databinding.BindingAdapter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.widget.TextView;

public class SpannableTextBinder {

    @BindingAdapter({"android:text", "bind:spanLines", "bind:spanSize"})
    public static void spannableText(TextView textView, String spannableText, int spanLines, float spanSize) {
        if (TextUtils.isEmpty(spannableText) || spanLines == 0 || spanSize == 0)
            return;

        SpannableString ss = new SpannableString(spannableText);
        ss.setSpan(new LinesLeadingMarginSpan2(spanLines, (int)spanSize), 0, ss.length(), 0);

        textView.setText(ss);
    }
}
