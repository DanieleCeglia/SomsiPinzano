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
    }

    static DatabaseAdapter dammiDbHelperCondiviso(Context context) {
        if (databaseAdapterCondiviso == null) {
            databaseAdapterCondiviso = new DatabaseAdapter(context);
        }

        return databaseAdapterCondiviso;
    }

    void apriConnesioneDatabase() {
        this.database = databaseHelper.getWritableDatabase();
    }

    void chiudiConnessioneDatabase() {
        if (database != null) {
            this.database.close();
        }
    }

    List <Categoria> dammiCategorie() {
        List <Categoria> list = new ArrayList<>();

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT * FROM CATEGORIA", null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Categoria categoria = new Categoria();
                categoria.idCategoria  = cursor.getInt(0);
                categoria.nome         = cursor.getString(1);
                categoria.fileImmagine = cursor.getString(2);

                list.add(categoria);

                cursor.moveToNext();
            }

            cursor.close();
        } else {
            Log.d("DEBUGAPP", TAG + "[dammiCategorie] Connessione NON aperta!");
        }

        return list;
    }
}
