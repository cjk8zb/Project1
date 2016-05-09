package edu.umkc.cs449.knight.cameron.jaw;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;

/**
 * Created by camjknight on 3/6/16.
 */
class DrawableUtil {

    public static void applyTint(Context context, Drawable drawable, @AttrRes int resId) {
        if (context == null || drawable == null) {
            return;
        }

        final TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(resId, typedValue, true)) {
            return;
        }
        Integer textColorId = typedValue.resourceId;

        drawable = DrawableCompat.wrap(drawable);
        drawable.mutate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DrawableCompat.setTint(drawable, context.getResources().getColor(textColorId, null));
        } else {
            //noinspection deprecation
            DrawableCompat.setTint(drawable, context.getResources().getColor(textColorId));
        }
    }
}
