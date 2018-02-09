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

package io.synoshy.zstu.presentation.common.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;

import io.synoshy.zstu.domain.common.util.Validator;

public class InjectableViewModelFactory implements ViewModelProvider.Factory {

    private Map<Class<? extends ViewModel>, ViewModel> modelStorage;

    @Inject
    public InjectableViewModelFactory(@NonNull Map<Class<? extends ViewModel>, ViewModel> modelStorage) {
        this.modelStorage = modelStorage;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Validator.throwIfNull(modelStorage, "Model storage was not injected.");
        Validator.throwIf(!modelStorage.containsKey(modelClass),
                "Model was not injected. Perhaps, was injection configured?");

        return (T)modelStorage.get(modelClass);
    }
}
