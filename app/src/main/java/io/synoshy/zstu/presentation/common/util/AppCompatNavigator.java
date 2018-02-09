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

package io.synoshy.zstu.presentation.common.util;

import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import io.synoshy.zstu.domain.common.util.Validator;

public class AppCompatNavigator {

    private static Map<String, Class<?>> activities = new HashMap<>();

    public static <T extends AppCompatActivity> void addActivity(String name, Class<T> clazz) {
        Validator.throwIfEmptyString(name, "Name is null.");
        Validator.throwIfNull(clazz, "Error adding null activity class.");
        Validator.throwIf(activities.containsKey(name), "Activity is already added.");

        activities.put(name, clazz);
    }

    public static <T extends AppCompatActivity> Class<T> getActivity(String name) {
        Validator.throwIf(!activities.containsKey(name), "There is no activity with such name.");

        return (Class<T>)activities.get(name);
    }

    public static boolean isRegistered(String name) {
        Validator.throwIfEmptyString(name, "Name is null.");

        return activities.containsKey(name);
    }
}
