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

package io.synoshy.zstu.presentation.common;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import io.synoshy.zstu.ZSTUApplication;
import io.synoshy.zstu.di.component.AppComponent;

public class FragmentBase extends Fragment {

    @Nullable
    @Override
    public View getView() {
        if (this instanceof HasBinding)
            return ((HasBinding)this).getBinding().getRoot();

        return super.getView();
    }
}
