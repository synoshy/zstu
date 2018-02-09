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

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class HexagonDrawable extends Drawable {

    private final int VERTEXES = 6;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Path path = new Path();

    private Path updateHexagonPath(@NonNull Rect rect) {
        int width = rect.width();
        int height = rect.height();
        int diameter = Math.min(width, height);
        int centerX = rect.left + (width / 2);
        int centerY = rect.top + (height / 2);

        return createHexagon(centerX, centerY, diameter);
    }

    private Path createHexagon(int centerX, int centerY, int diameter) {
        final float section = (float) (2.0 * Math.PI / VERTEXES);
        float phase = section / 2;
        int radius = diameter / 2;
        Path path = new Path();
        path.moveTo((centerX + radius),
                    (centerY + radius * (float)Math.sin(phase)));

        for (int i = 1; i < VERTEXES; i++) {
            float cosine = (float)Math.cos(section * i + phase);
            if (Math.abs(cosine) > 1e-3f) cosine = Math.signum(cosine);

            path.lineTo((centerX + radius * cosine),
                        (centerY + radius * (float)Math.sin(section * i + phase)));
        }

        path.close();
        return path;
    }

    public HexagonDrawable(int color) {
        paint.setColor(color);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        path = updateHexagonPath(bounds);
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public void setAlpha(int i) {
        paint.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha();
    }
}
