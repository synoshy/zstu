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

package io.synoshy.zstu.presentation.menu;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;

import com.annimon.stream.function.Function;

public class MenuItem implements View.OnClickListener {

    public final String Name;

    private Function<View, Void> onClickHandler;

    private Drawable background;

    private String text;

    private int size;

    private int textColor;

    private final int DEFAULT_TEXT_COLOR = Color.WHITE;

    public MenuItem(@NonNull String name) {
        this.Name = name;
    }

    public Drawable getBackground() {
        return background;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSpacing() {
        return size / 6;
    }

    public int getTextColor() {
        return textColor == 0 ? DEFAULT_TEXT_COLOR : textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    @Override
    public void onClick(View view) {
        if (onClickHandler != null)
            onClickHandler.apply(view);
    }

    public void setOnClickHandler(@NonNull Function<View, Void> onClickHandler) {
        this.onClickHandler = onClickHandler;
    }
}
