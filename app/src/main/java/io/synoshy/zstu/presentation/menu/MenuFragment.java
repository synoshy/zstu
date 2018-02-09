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
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.ButterKnife;
import io.synoshy.zstu.R;
import io.synoshy.zstu.databinding.FragmentMenuBinding;
import io.synoshy.zstu.presentation.common.FragmentBase;
import io.synoshy.zstu.presentation.common.viewmodel.InjectableViewModelFactory;
import io.synoshy.zstu.presentation.common.util.AppCompatNavigator;
import io.synoshy.zstu.presentation.common.util.ViewUtil;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MenuFragment extends FragmentBase {

    private static final float LANDSCAPE_MENU_ITEM_SIZE_MULTIPLIER = .25f;
    private static final float PORTRAIT_MENU_ITEM_SIZE_MULTIPLIER = .375f;

    @Inject
    InjectableViewModelFactory viewModelFactory;

    @BindColor(R.color.menu_item_text)
    int menuItemTextColor;

    private MenuViewModel menuViewModel;

    private FragmentMenuBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        initialize();
    }

    private void initialize() {
        menuViewModel = ViewModelProviders.of(this, viewModelFactory).get(MenuViewModel.class);
        menuViewModel.setItemOnClickHandler("News", x -> {
            Intent i = new Intent(getContext(), AppCompatNavigator.getActivity("News"));
            getActivity().startActivity(i);
            getActivity().overridePendingTransition(R.anim.zoom_out, R.anim.zoom_in);
            return null;
        });

        menuViewModel.updateItemSizes(calculateMenuItemSize(ViewUtil.getOrientation(), ViewUtil.getDeviceWidth()));
        menuViewModel.updateItemTextColors(menuItemTextColor);

        binding.setModel(menuViewModel);
    }

    private int calculateMenuItemSize(int orientation, int containerWidth) {
        float widthMultiplier;
        switch (orientation) {
            case ORIENTATION_LANDSCAPE: {
                widthMultiplier = LANDSCAPE_MENU_ITEM_SIZE_MULTIPLIER;
                break;
            }
            case ORIENTATION_PORTRAIT: {
                widthMultiplier = PORTRAIT_MENU_ITEM_SIZE_MULTIPLIER;
                break;
            }
            default:
                throw new UnsupportedOperationException("Orientation is undefined.");
        }

        return (int)(widthMultiplier * containerWidth);
    }
}
