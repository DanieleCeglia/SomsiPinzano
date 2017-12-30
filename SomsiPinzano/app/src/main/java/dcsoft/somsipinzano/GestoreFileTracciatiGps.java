package dcsoft.somsipinzano;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class GestoreFileTracciatiGps {
    private final String TAG = getClass().getSimpleName();
    private static GestoreFileTracciatiGps gestoreFileTracciatiGpsCondiviso;
    private Context context;
    private String gpsTracksPath = null;
    private File fileCache = null;

    //region Metodi privati

    String getGpsTracksPath() {
        return gpsTracksPath;
    }

    //endregion

    //region Metodi privati

    // costruttore privato per costringere l'uso questa classe solo come singleton
    private GestoreFileTracciatiGps(Context context) {
        this.context = context;

        gpsTracksPath = this.context.getApplicationInfo().dataDir + "/gpsTracks/";
    }

    private boolean fileCacheNonEsiste(String nomeFile) {
        fileCache = new File(context.getCacheDir(), nomeFile);

        return !fileCache.exists() || fileCache.isDirectory();
    }

    private void chiudiStream(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.d("DEBUGAPP", TAG + " [chiudiStream] errore: " + e.toString());
            }
        }
    }

    private void creaFileCache(String nomeFile) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(gpsTracksPath + nomeFile);
            outputStream = new FileOutputStream(fileCache);
            byte[] buf = new byte[1024];
            int len;

            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
        } catch (IOException e) {
            Log.d("DEBUGAPP", TAG + " [creaFileCache] errore: " + e.toString());
        } finally {
            chiudiStream(inputStream);
            chiudiStream(outputStream);
        }
    }

    //endregion

    //region Metodi pubblici

    // singleton
    static synchronized GestoreFileTracciatiGps dammiGestoreDatabaseCondiviso(Context context) {
        if (gestoreFileTracciatiGpsCondiviso == null && context != null) {
            gestoreFileTracciatiGpsCondiviso = new GestoreFileTracciatiGps(context);

            File directory = new File(gestoreFileTracciatiGpsCondiviso.gpsTracksPath);

            Boolean directoryEsistente = true;

            if (!directory.exists()) {
                directoryEsistente = directory.mkdir();
            }

            if (!directoryEsistente) {
                gestoreFileTracciatiGpsCondiviso = null; // questo non dovrebbe mai accadere!
            }
        }

        return gestoreFileTracciatiGpsCondiviso;
    }

    File dammiFileCacheCreaSeNecessario(String nomeFile) {
        if (fileCacheNonEsiste(nomeFile)) {
            creaFileCache(nomeFile);
        }

        return this.fileCache;
    }

    //endregion
}
