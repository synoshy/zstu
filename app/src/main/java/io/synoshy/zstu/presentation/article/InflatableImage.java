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

package io.synoshy.zstu.presentation.article;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import io.synoshy.zstu.R;
import io.synoshy.zstu.presentation.common.glide.GlideApp;

public class InflatableImage implements InflatableData {

    private String imageUrl;

    public InflatableImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public View inflate(Context context, @NonNull LayoutParams params) {
        ImageView view = new ImageView(context);
        view.setLayoutParams(params);
        view.setAdjustViewBounds(true);
        GlideApp.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(view);

        return view;
    }
}
