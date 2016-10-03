package dcsoft.somsipinzano;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by daniele on 03/10/16.
 */
public class DbHelper {
    private static DbHelper DbHelperCondiviso;
    private static final String TAG = "DbHelper ";
    private static final String NOME_DATABASE = "somsiPinzano.db";
    private static final int VERSIONE = 1;
    private SQLiteOpenHelper sqliteHelper;
    private SQLiteDatabase db;

    private DbHelper() {
        // init privato per impedire uso NON come singleton
    }

    public static DbHelper dammiDbHelperCondiviso(Context context) {
        if (DbHelperCondiviso == null) {
            DbHelperCondiviso = new DbHelper();

            DbHelperCondiviso.sqliteHelper = new SQLiteOpenHelper(context, NOME_DATABASE, null, VERSIONE) {
                @Override
                public void onCreate(SQLiteDatabase db) {

                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

                }
            };

            DbHelperCondiviso.db = DbHelperCondiviso.sqliteHelper.getReadableDatabase();

            if (DbHelperCondiviso.db != null) {
                Log.d("DEBUGAPP", TAG + "db caricato");
                Log.d("DEBUGAPP", TAG + "db path: " + context.getDatabasePath(NOME_DATABASE));
            }
        }

        return DbHelperCondiviso;
    }


    public Cursor query()
    {
        Cursor crs = null;

        try {
            crs = db.query("CATEGORIA", null, null, null, null, null, null, null);
        } catch(SQLiteException sqle) {
            Log.d("DEBUGAPP", TAG + "Cursor exception: "  + sqle.toString());

            return null;
        }

        return crs;
    }
}
