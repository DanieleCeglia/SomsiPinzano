package dcsoft.somsipinzano;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

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

    ArrayList<Categoria> dammiCategorie() {
        ArrayList<Categoria> list = new ArrayList<>();

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

    ArrayList<Pdi> dammiPdiPerCategoria(int idCategoria) {
        ArrayList<Pdi> list = new ArrayList<>();

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT * FROM PDI WHERE idPdi_idCategoria = " + idCategoria, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Pdi pdi = new Pdi();

                pdi.idPdi             = cursor.getInt(0);
                pdi.idPdi_idCategoria = cursor.getInt(1);
                pdi.titolo            = cursor.getString(2);
                pdi.descrizione       = cursor.getString(3);
                pdi.citta             = cursor.getString(4);
                pdi.via               = cursor.getString(5);
                pdi.numeroCivico      = cursor.getInt(6);
                pdi.interno           = cursor.getString(7);
                pdi.cap               = cursor.getInt(8);
                pdi.latitudine        = cursor.getDouble(9);
                pdi.longitudine       = cursor.getDouble(10);
                pdi.fileImmagine      = cursor.getString(11);

                list.add(pdi);

                cursor.moveToNext();
            }

            cursor.close();
        } else {
            Log.d("DEBUGAPP", TAG + "[dammiPdiPerCategoria] Connessione NON aperta!");
        }

        return list;
    }
}
