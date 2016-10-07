package dcsoft.somsipinzano;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class DatabaseAdapter {
    private static final String TAG = "DatabaseAdapter ";
    private static DatabaseAdapter databaseAdapterCondiviso;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    private DatabaseAdapter(Context context) { // init privato per impedire uso NON come singleton
        this.databaseHelper = new DatabaseHelper(context);

        Log.d("DEBUGAPP", TAG + "DatabaseAdapter");
    }

    static DatabaseAdapter dammiDbHelperCondiviso(Context context) {
        Log.d("DEBUGAPP", TAG + "dammiDbHelperCondiviso");

        if (databaseAdapterCondiviso == null) {
            databaseAdapterCondiviso = new DatabaseAdapter(context);
        }

        return databaseAdapterCondiviso;
    }

    public void open() {
        this.database = databaseHelper.getWritableDatabase();
    }

    void close() {
        if (database != null) {
            this.database.close();
        }
    }

    List <String> dammiCategorie() {
        List <String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM CATEGORIA", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));

            cursor.moveToNext();
        }

        cursor.close();

        return list;
    }
}
