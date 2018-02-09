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

package io.synoshy.zstu.presentation.di.module;

import android.app.Application;
import android.arch.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.synoshy.zstu.presentation.common.viewmodel.InjectableViewModelFactory;
import io.synoshy.zstu.presentation.menu.MenuViewModel;

@Module
public class PresentationModule {

    @Provides
    @Named("ViewModelStorage")
    @Singleton
    Map<Class<? extends ViewModel>, ViewModel> provideModelStorage(Application application) {
        Map<Class<? extends ViewModel>, ViewModel> result = new HashMap<>();
        result.put(MenuViewModel.class, new MenuViewModel(application));

        return result;
    }

    @Provides
    @Singleton
    InjectableViewModelFactory provideViewModelFactory(
            @Named("ViewModelStorage") Map<Class<? extends ViewModel>, ViewModel> modelStorage)
    {
        return new InjectableViewModelFactory(modelStorage);
    }
}
