package national.soft.impresiones.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class Permisos {
    public static boolean solicitarPermisos(Activity a, String... permissions) {
        boolean res = true;
        if (a != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(a, permission) != PackageManager.PERMISSION_GRANTED) {
                    res = false;
                    ActivityCompat.requestPermissions(a, permissions, 1);
                }
            }
        }
        return res;
    }

    public static String[] permisos(){
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }
}
