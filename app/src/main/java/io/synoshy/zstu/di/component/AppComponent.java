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

package io.synoshy.zstu.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import io.synoshy.zstu.di.module.AppModule;
import io.synoshy.zstu.di.module.DataModule;
import io.synoshy.zstu.presentation.article.ArticleViewModel;
import io.synoshy.zstu.presentation.feed.FeedViewModel;

@Component(modules = {AppModule.class, DataModule.class})
@Singleton
public interface AppComponent {

    Context context();

    Application application();

    void inject(FeedViewModel model);

    void inject(ArticleViewModel viewModel);
}
