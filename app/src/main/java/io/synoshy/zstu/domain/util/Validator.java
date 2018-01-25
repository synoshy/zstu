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

package io.synoshy.zstu.domain.util;

import io.synoshy.zstu.domain.exception.ValidationException;

public class Validator {

    public static void throwIfNull(Object object) {
        throwIfNull(object, null);
    }

    public static void throwIfNull(Object object, String message) {
        if (object == null)
            throw new ValidationException(message);
    }

    public static void throwIf(boolean condition) {
        throwIf(condition, null);
    }

    public static void throwIf(boolean condition, String message) {
        if (condition)
            throw new ValidationException(message);
    }

    public static void throwIfEmptyString(String string) {
        throwIfEmptyString(string, null);
    }

    public static void throwIfEmptyString(String string, String message) {
        if (string == null || string.isEmpty() || string.split("\\s*").length == 0)
            throw new ValidationException(message);
    }
}
