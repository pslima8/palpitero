package br.com.jrsoft.palpitero.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

public class ResourcesUtil {

    public static Drawable devolveDrawable(Context context, int idDoDrawable) {
        return ContextCompat.getDrawable(context, idDoDrawable);
    }
}
