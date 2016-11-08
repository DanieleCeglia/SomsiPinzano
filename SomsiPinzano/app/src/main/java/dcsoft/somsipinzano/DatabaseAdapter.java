package dcsoft.somsipinzano;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

class DatabaseAdapter {
    private static final String TAG = "DatabaseAdapter ";
    private static DatabaseAdapter databaseAdapterCondiviso;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private String lingua;

    private DatabaseAdapter(Context context) { // init privato per impedire uso NON come singleton
        this.databaseHelper = new DatabaseHelper(context);
    }

    static DatabaseAdapter dammiDbHelperCondiviso(Context context) {
        if (databaseAdapterCondiviso == null) {
            databaseAdapterCondiviso = new DatabaseAdapter(context);

            databaseAdapterCondiviso.lingua = Locale.getDefault().getDisplayLanguage();
        }

        return databaseAdapterCondiviso;
    }

    String getLingua() {
        return lingua;
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

                categoria.idCategoria       = cursor.getInt(0);
                categoria.nomeItaliano      = cursor.getString(1);
                categoria.nomeInglese       = cursor.getString(2);
                categoria.fileImmagine      = cursor.getString(3);
                categoria.coloreEsadecimale = cursor.getString(4);

                list.add(categoria);

                cursor.moveToNext();
            }

            cursor.close();
        } else {
            Log.d("DEBUGAPP", TAG + "[dammiCategorie] Connessione NON aperta!");
        }

        return list;
    }

    ArrayList<Categoria> dammiCategoria(int idCategoria) {
        ArrayList<Categoria> list = new ArrayList<>();

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT * FROM CATEGORIA WHERE idCategoria = " + idCategoria, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Categoria categoria = new Categoria();

                categoria.idCategoria       = cursor.getInt(0);
                categoria.nomeItaliano      = cursor.getString(1);
                categoria.nomeInglese       = cursor.getString(2);
                categoria.fileImmagine      = cursor.getString(3);
                categoria.coloreEsadecimale = cursor.getString(4);

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

                pdi.idPdi                      = cursor.getInt(0);
                pdi.idPdi_idCategoria          = cursor.getInt(1);
                pdi.titoloItaliano             = cursor.getString(2);
                pdi.titoloInglese              = cursor.getString(3);
                pdi.descrizioneItaliano        = cursor.getString(4);
                pdi.descrizioneInglese         = cursor.getString(5);
                pdi.citta                      = cursor.getString(6);
                pdi.via                        = cursor.getString(7);
                pdi.numeroCivico               = cursor.getInt(8);
                pdi.interno                    = cursor.getString(9);
                pdi.cap                        = cursor.getInt(10);
                pdi.latitudine                 = cursor.getDouble(11);
                pdi.longitudine                = cursor.getDouble(12);
                pdi.fileImmagine               = cursor.getString(13);
                pdi.titoloLinkGenericoItaliano = cursor.getString(14);
                pdi.titoloLinkGenericoInglese  = cursor.getString(15);
                pdi.linkGenerico               = cursor.getString(16);
                pdi.linkVideo                  = cursor.getString(17);

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
