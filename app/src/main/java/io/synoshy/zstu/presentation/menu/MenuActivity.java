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

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.BindColor;
import butterknife.ButterKnife;
import io.synoshy.zstu.R;
import io.synoshy.zstu.databinding.ActivityMenuBinding;
import io.synoshy.zstu.presentation.common.ActivityBase;
import io.synoshy.zstu.presentation.common.util.ViewUtil;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MenuActivity extends ActivityBase {

    @BindColor(R.color.menu_item_background)
    int menuItemBackgroundColor;

    @BindColor(R.color.menu_item_background_pressed)
    int menuItemBackgroundPressedColor;

    @BindColor(R.color.menu_item_text)
    int menuItemTextColor;

    private MenuViewModel menuViewModel;

    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        if (!menuViewModel.isInitialized()) {
            Drawable hexagon = new HexagonDrawable(menuItemBackgroundColor);
            Drawable scaledHexagon = new HexagonDrawable(menuItemBackgroundPressedColor);
            menuViewModel.initialize(hexagon, scaledHexagon);
        }

        menuViewModel.updateItemSizes(calculateMenuItemSize(ViewUtil.getOrientation(), ViewUtil.getDeviceWidth()));
        menuViewModel.updateItemTextColors(menuItemTextColor);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        binding.setModel(menuViewModel);
    }

    private int calculateMenuItemSize(int orientation, int containerWidth) {
        float widthMultiplier;
        switch (orientation) {
            case ORIENTATION_LANDSCAPE: {
                widthMultiplier = .25f;
                break;
            }
            case ORIENTATION_PORTRAIT: {
                widthMultiplier = .375f;
                break;
            }
            default:
                throw new UnsupportedOperationException("Orientation is undefined.");
        }

        return (int)(widthMultiplier * containerWidth);
    }
}
