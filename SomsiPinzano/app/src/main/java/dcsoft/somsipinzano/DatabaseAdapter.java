package dcsoft.somsipinzano;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

class DatabaseAdapter {
    private final String TAG = getClass().getSimpleName();
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
                Categoria categoria = new Categoria(
                        cursor.getInt(0),     // idCategoria
                        cursor.getString(1),  // nomeItaliano
                        cursor.getString(2),  // nomeInglese
                        cursor.getString(3),  // fileImmagine
                        cursor.getString(4)); // coloreEsadecimale

                list.add(categoria);

                cursor.moveToNext();
            }

            cursor.close();
        } else {
            Log.d("DEBUGAPP", TAG + " [dammiCategorie] Connessione NON aperta!");
        }

        return list;
    }

    Categoria dammiCategoria(int idCategoria) {
        Categoria categoria = null;

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT * FROM CATEGORIA WHERE idCategoria = " + idCategoria, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                categoria = new Categoria(
                        cursor.getInt(0),     // idCategoria
                        cursor.getString(1),  // nomeItaliano
                        cursor.getString(2),  // nomeInglese
                        cursor.getString(3),  // fileImmagine
                        cursor.getString(4)); // coloreEsadecimale

                cursor.moveToNext();
            }

            cursor.close();
        } else {
            Log.d("DEBUGAPP", TAG + " [dammiCategorie] Connessione NON aperta!");
        }

        return categoria;
    }

    ArrayList<Pdi> dammiPdi() {
        ArrayList<Pdi> list = new ArrayList<>();

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT * FROM PDI", null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Pdi pdi = new Pdi(
                        cursor.getInt(0),      // idPdi
                        cursor.getInt(1),      // idPdi_idCategoria
                        cursor.getString(2),   // titoloItaliano
                        cursor.getString(3),   // titoloInglese
                        cursor.getString(4),   // descrizioneItaliano
                        cursor.getString(5),   // descrizioneInglese
                        cursor.getString(6),   // citta
                        cursor.getString(7),   // via
                        cursor.getInt(8),      // numeroCivico
                        cursor.getString(9),   // interno
                        cursor.getInt(10),     // cap
                        cursor.getDouble(11),  // latitudine
                        cursor.getDouble(12),  // longitudine
                        cursor.getString(13),  // fileImmagine
                        cursor.getString(14),  // titoloLinkGenericoItaliano
                        cursor.getString(15),  // titoloLinkGenericoInglese
                        cursor.getString(16),  // linkGenerico
                        cursor.getString(17)); // linkVideo

                list.add(pdi);

                cursor.moveToNext();
            }

            cursor.close();
        } else {
            Log.d("DEBUGAPP", TAG + " [dammiPdiPerCategoria] Connessione NON aperta!");
        }

        return list;
    }

    ArrayList<Pdi> dammiPdiPerCategoria(int idCategoria) {
        ArrayList<Pdi> list = new ArrayList<>();

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT * FROM PDI WHERE idPdi_idCategoria = " + idCategoria, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Pdi pdi = new Pdi(
                        cursor.getInt(0),      // idPdi
                        cursor.getInt(1),      // idPdi_idCategoria
                        cursor.getString(2),   // titoloItaliano
                        cursor.getString(3),   // titoloInglese
                        cursor.getString(4),   // descrizioneItaliano
                        cursor.getString(5),   // descrizioneInglese
                        cursor.getString(6),   // citta
                        cursor.getString(7),   // via
                        cursor.getInt(8),      // numeroCivico
                        cursor.getString(9),   // interno
                        cursor.getInt(10),     // cap
                        cursor.getDouble(11),  // latitudine
                        cursor.getDouble(12),  // longitudine
                        cursor.getString(13),  // fileImmagine
                        cursor.getString(14),  // titoloLinkGenericoItaliano
                        cursor.getString(15),  // titoloLinkGenericoInglese
                        cursor.getString(16),  // linkGenerico
                        cursor.getString(17)); // linkVideo

                list.add(pdi);

                cursor.moveToNext();
            }

            cursor.close();
        } else {
            Log.d("DEBUGAPP", TAG + " [dammiPdiPerCategoria] Connessione NON aperta!");
        }

        return list;
    }
}
