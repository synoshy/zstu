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

package io.synoshy.zstu.presentation.menu;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

public class MenuButton extends FloatingActionButton implements View.OnClickListener {

    private AnimatedVectorDrawable defaultStateDrawable;

    private AnimatedVectorDrawable invertedStateDrawable;

    private boolean isDefaultState = true;

    //region Constructors

    public MenuButton(Context context) {
        super(context);
    }

    public MenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //endregion

    public void initialize(@NonNull AnimatedVectorDrawable defaultState,
                           @NonNull AnimatedVectorDrawable invertedState)
    {
        this.defaultStateDrawable = defaultState;
        this.invertedStateDrawable = invertedState;

        setImageDrawable(defaultState);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AnimatedVectorDrawable drawable = isDefaultState
                ? defaultStateDrawable
                : invertedStateDrawable;

        setImageDrawable(drawable);
        drawable.start();

        isDefaultState = !isDefaultState;
    }
}
