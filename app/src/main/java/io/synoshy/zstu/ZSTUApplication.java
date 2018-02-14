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

package io.synoshy.zstu;

import android.app.Application;

import io.synoshy.zstu.presentation.common.util.AppCompatNavigator;
import io.synoshy.zstu.di.component.AppComponent;
import io.synoshy.zstu.di.component.DaggerAppComponent;
import io.synoshy.zstu.di.module.AppModule;
import io.synoshy.zstu.presentation.feed.FeedActivity;

public class ZSTUApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        configureDependencies();
        configureNavigator();
    }

    private void configureDependencies() {
        appComponent = DaggerAppComponent.builder()
            .appModule(new AppModule(this))
            .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private void configureNavigator() {
        AppCompatNavigator.addActivity("News", FeedActivity.class);
    }
}
