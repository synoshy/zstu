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

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.annimon.stream.function.Function;

import java.util.HashMap;
import java.util.Map;

import io.synoshy.zstu.R;
import io.synoshy.zstu.domain.common.util.Validator;

public class MenuViewModel extends AndroidViewModel {

    private int menuItemBackgroundColor;

    private int menuItemBackgroundPressedColor;

    private int menuItemBorderColor;

    private int menuItemBorderWidth;

    private Map<String, MenuItem> menuItems;

    public MenuViewModel(@NonNull Application application) {
        super(application);
        loadResources();
        initialize();
    }

    private void loadResources() {
        menuItemBackgroundColor = ContextCompat.getColor(getApplication(), R.color.menu_item_background);
        menuItemBackgroundPressedColor = ContextCompat.getColor(getApplication(), R.color.menu_item_background_pressed);
        menuItemBorderColor = ContextCompat.getColor(getApplication(), R.color.menu_item_border);
        menuItemBorderWidth = (int)getApplication().getResources().getDimension(R.dimen.menu_item_border);
    }

    private void initialize() {
        menuItems = new HashMap<>();

        Drawable hexagon = new HexagonDrawable(menuItemBackgroundColor, menuItemBorderColor,
                menuItemBorderWidth);
        Drawable pressedHexagon = new HexagonDrawable(menuItemBackgroundPressedColor,
                menuItemBorderColor, menuItemBorderWidth);

        initializeMenuItems(hexagon, pressedHexagon);
    }

    private void initializeMenuItems(Drawable itemBackground, Drawable itemPressedBackground) {
        for (String name : new String[] {"News", "Schedule", "Teachers", "Accounts", "Group chat"}) {
            StateListDrawable itemDrawable = new StateListDrawable();
            itemDrawable.addState(new int[] {-android.R.attr.state_pressed}, itemBackground);
            if (itemPressedBackground != null)
                itemDrawable.addState(new int[] {android.R.attr.state_pressed}, itemPressedBackground);

            MenuItem item = new MenuItem(name);
            item.setBackground(itemDrawable);
            item.setText(name);
            menuItems.put(item.Name, item);
        }
    }

    public Map<String, MenuItem> getMenuItems() {
        return menuItems;
    }

    public void updateItemSizes(int itemSize) {
        for (MenuItem item : menuItems.values())
            item.setSize(itemSize);
    }

    public void updateItemTextColors(int itemTextColor) {
        for (MenuItem item : menuItems.values())
            item.setTextColor(itemTextColor);
    }

    public void setItemOnClickHandler(@NonNull String itemName, @NonNull Function<View, Void> handler) {
        Validator.throwIf(!menuItems.containsKey(itemName), "There is no item with such name.");

        menuItems.get(itemName).setOnClickHandler(handler);
    }
}
