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

import android.app.Activity;
import android.app.FragmentTransaction;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindColor;
import butterknife.ButterKnife;
import io.synoshy.zstu.R;
import io.synoshy.zstu.databinding.FragmentMenuBinding;
import io.synoshy.zstu.presentation.common.FragmentBase;
import io.synoshy.zstu.presentation.common.HasBinding;
import io.synoshy.zstu.presentation.common.util.AppCompatNavigator;
import io.synoshy.zstu.presentation.common.util.ViewUtil;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MenuFragment extends FragmentBase implements HasBinding {

    private static final float LANDSCAPE_MENU_ITEM_SIZE_MULTIPLIER = .25f;
    private static final float PORTRAIT_MENU_ITEM_SIZE_MULTIPLIER = .375f;

    @BindColor(R.color.menu_item_text)
    int menuItemTextColor;

    private MenuViewModel menuViewModel;

    private FragmentMenuBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, binding.getRoot());
        initialize();

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initialize() {
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        menuViewModel.setItemOnClickHandler("News", x -> {
            runActivity(AppCompatNavigator.getActivity("News"));
            return null;
        });
        menuViewModel.setItemOnClickHandler("Schedule", x -> {
            Toast.makeText(getContext(), R.string.in_development, Toast.LENGTH_SHORT).show();
            return null;
        });
        menuViewModel.setItemOnClickHandler("Teachers", x -> {
            Toast.makeText(getContext(), R.string.in_development, Toast.LENGTH_SHORT).show();
            return null;
        });
        menuViewModel.setItemOnClickHandler("Accounts", x -> {
            Toast.makeText(getContext(), R.string.in_development, Toast.LENGTH_SHORT).show();
            return null;
        });
        menuViewModel.setItemOnClickHandler("Group chat", x -> {
            Toast.makeText(getContext(), R.string.in_development, Toast.LENGTH_SHORT).show();
            return null;
        });

        recalculateItemSizes(ViewUtil.getOrientation(), ViewUtil.getDeviceWidth());

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

        return (int) (widthMultiplier * containerWidth);
    }

    private void runActivity(@NonNull Class<? extends Activity> target) {
        if (target.isInstance(getActivity())) {
            if (getActivity() instanceof MenuControl)
                ((MenuControl) getActivity()).hideMenu();
            else {
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .remove(this)
                        .commit();
            }

            return;
        }

        Intent i = new Intent(getContext(), target);
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.zoom_out, R.anim.zoom_in);
    }

    public void recalculateItemSizes(int orientation, int screenWidth) {
        if (menuViewModel != null)
            menuViewModel.updateItemSizes(calculateMenuItemSize(orientation, screenWidth));
    }

    @Override
    public ViewDataBinding getBinding() {
        return binding;
    }
}
