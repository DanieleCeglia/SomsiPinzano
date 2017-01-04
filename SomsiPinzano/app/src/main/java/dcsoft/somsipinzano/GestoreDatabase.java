package dcsoft.somsipinzano;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

class GestoreDatabase extends SQLiteOpenHelper {
    private final String TAG = getClass().getSimpleName();
    private static GestoreDatabase gestoreDatabaseCondiviso;
    private String lingua;

    // info database
    private static String PERCORSO_DATABASE           = null;
    private static final String NOME_DATABASE_COPIATO = "somsiPinzanoCopia.db";
    private static final String NOME_DATABASE_ASSET   = "somsiPinzanoAsset.db";
    private static final int VERSIONE_DATABASE        = 1;

    //region Metodi privati

    // costruttore privato per costringere l'uso questa classe solo come singleton
    private GestoreDatabase(Context context) {
        super(context, NOME_DATABASE_COPIATO, null, VERSIONE_DATABASE);

        PERCORSO_DATABASE = context.getApplicationInfo().dataDir + "/databases/";

        cancellaDatabaseSeEsistente();
        copiaDatabase(context);
    }

    private void cancellaDatabaseSeEsistente() {
        String percorsoFileDatabase = PERCORSO_DATABASE + NOME_DATABASE_COPIATO;
        File fileDatabase = new File(percorsoFileDatabase);

        if (fileDatabase.exists()) {
            if (!fileDatabase.delete()) {
                Log.d("DEBUGAPP", TAG + " Fallita la cancellazione del file " + NOME_DATABASE_COPIATO);
            }
        }
    }

    private void copiaDatabase(Context context) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        AssetManager assetManager = context.getAssets();

        File directory = new File(PERCORSO_DATABASE);

        Boolean directoryEsistente = true;

        if (!directory.exists()) {
            directoryEsistente = directory.mkdir();
        }

        if (directoryEsistente) {
            try {
                inputStream = assetManager.open("sqlite/" + NOME_DATABASE_ASSET);
                File outFile = new File(PERCORSO_DATABASE, NOME_DATABASE_COPIATO);
                outputStream = new FileOutputStream(outFile);
                copiaFile(inputStream, outputStream);
            } catch(IOException e) {
                Log.d("DEBUGAPP", TAG + " Fallita la copia del file " + NOME_DATABASE_ASSET + " con errore: " + e);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void copiaFile(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int lunghezza;

        while ((lunghezza = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, lunghezza);
        }

        Log.d("DEBUGAPP", TAG + " Database copiato.");
    }

    //endregion

    //region Metodi override

    // chiamato quando la connessione al database è in fase di configurazione
    // configurare le impostazioni del database per cose come il supporto chiavi esterne, registrazione write-ahead, ecc...
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);

        db.setForeignKeyConstraintsEnabled(true);
    }

    // chiamato quando viene creato il database per la prima volta
    // se un database esiste già sul disco con lo stesso NOME_DATABASE, questo metodo non viene chiamato...
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    // chiamato quando il database deve essere aggiornato
    // questo metodo verrà chiamato solo se il database esiste già sul disco con lo stesso NOME_DATABASE,
    // ma la VERSIONE_DATABASE è diversa rispetto alla versione del database che è presente sul disco
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    //endregion

    //region Metodi pubblici

    // singleton
    static synchronized GestoreDatabase dammiGestoreDatabaseCondiviso(Context context) {
        if (gestoreDatabaseCondiviso == null) {
            gestoreDatabaseCondiviso = new GestoreDatabase(context.getApplicationContext());

            gestoreDatabaseCondiviso.lingua = Locale.getDefault().getDisplayLanguage();
        }

        return gestoreDatabaseCondiviso;
    }

    String getLingua() {
        return lingua;
    }

    ArrayList<Categoria> dammiCategorie() {
        ArrayList<Categoria> list = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT * FROM CATEGORIA ORDER BY ordinamento", null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Categoria categoria = new Categoria(
                        cursor.getInt(0),     // idCategoria
                        cursor.getInt(1),     // ordinamento
                        cursor.getString(2),  // nomeItaliano
                        cursor.getString(3),  // nomeInglese
                        cursor.getString(4),  // descrizioneItaliano
                        cursor.getString(5),  // descrizioneInglese
                        cursor.getString(6),  // fileImmagine
                        cursor.getString(7),  // fileImmagineCopertina
                        cursor.getString(8)); // filePin

                list.add(categoria);

                cursor.moveToNext();
            }

            cursor.close();

            database.close();
        } else {
            Log.d("DEBUGAPP", TAG + " [dammiCategorie] Connessione NON aperta!");
        }

        return list;
    }

    Categoria dammiCategoria(int idCategoria) {
        Categoria categoria = null;

        SQLiteDatabase database = getReadableDatabase();

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT * FROM CATEGORIA WHERE idCategoria = " + idCategoria + " ORDER BY ordinamento", null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                categoria = new Categoria(
                        cursor.getInt(0),     // idCategoria
                        cursor.getInt(1),     // ordinamento
                        cursor.getString(2),  // nomeItaliano
                        cursor.getString(3),  // nomeInglese
                        cursor.getString(4),  // descrizioneItaliano
                        cursor.getString(5),  // descrizioneInglese
                        cursor.getString(6),  // fileImmagine
                        cursor.getString(7),  // fileImmagineCopertina
                        cursor.getString(8)); // filePin

                cursor.moveToNext();
            }

            cursor.close();

            database.close();
        } else {
            Log.d("DEBUGAPP", TAG + " [dammiCategoria] Connessione NON aperta!");
        }

        return categoria;
    }

    ArrayList<Pdi> dammiPdi() {
        ArrayList<Pdi> list = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT * FROM PDI ORDER BY ordinamento", null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Pdi pdi = new Pdi(
                        cursor.getInt(0),      // idPdi
                        cursor.getInt(1),      // idPdi_idCategoria
                        cursor.getInt(2),      // ordinamento
                        cursor.getString(3),   // titoloItaliano
                        cursor.getString(4),   // titoloInglese
                        cursor.getString(5),   // descrizioneItaliano
                        cursor.getString(6),   // descrizioneInglese
                        cursor.getString(7),   // citta
                        cursor.getString(8),   // via
                        cursor.getInt(9),      // numeroCivico
                        cursor.getString(10),  // interno
                        cursor.getInt(11),     // cap
                        cursor.getDouble(12),  // latitudine
                        cursor.getDouble(13),  // longitudine
                        cursor.getString(14),  // telefono
                        cursor.getString(15),  // fax
                        cursor.getString(16),  // cellulare
                        cursor.getString(17),  // email
                        cursor.getString(18),  // titoloLinkGenericoItaliano
                        cursor.getString(19),  // titoloLinkGenericoInglese
                        cursor.getString(20),  // linkGenerico
                        cursor.getString(21)); // linkVideo

                list.add(pdi);

                cursor.moveToNext();
            }

            cursor.close();

            database.close();
        } else {
            Log.d("DEBUGAPP", TAG + " [dammiPdi] Connessione NON aperta!");
        }

        return list;
    }

    ArrayList<Pdi> dammiPdiPerCategoria(int idCategoria) {
        ArrayList<Pdi> list = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT * FROM PDI WHERE idPdi_idCategoria = " + idCategoria + " ORDER BY ordinamento", null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Pdi pdi = new Pdi(
                        cursor.getInt(0),      // idPdi
                        cursor.getInt(1),      // idPdi_idCategoria
                        cursor.getInt(2),      // ordinamento
                        cursor.getString(3),   // titoloItaliano
                        cursor.getString(4),   // titoloInglese
                        cursor.getString(5),   // descrizioneItaliano
                        cursor.getString(6),   // descrizioneInglese
                        cursor.getString(7),   // citta
                        cursor.getString(8),   // via
                        cursor.getInt(9),      // numeroCivico
                        cursor.getString(10),  // interno
                        cursor.getInt(11),     // cap
                        cursor.getDouble(12),  // latitudine
                        cursor.getDouble(13),  // longitudine
                        cursor.getString(14),  // telefono
                        cursor.getString(15),  // fax
                        cursor.getString(16),  // cellulare
                        cursor.getString(17),  // email
                        cursor.getString(18),  // titoloLinkGenericoItaliano
                        cursor.getString(19),  // titoloLinkGenericoInglese
                        cursor.getString(20),  // linkGenerico
                        cursor.getString(21)); // linkVideo

                list.add(pdi);

                cursor.moveToNext();
            }

            cursor.close();

            database.close();
        } else {
            Log.d("DEBUGAPP", TAG + " [dammiPdiPerCategoria] Connessione NON aperta!");
        }

        return list;
    }

    ArrayList<ImmaginePdi> dammiImmaginiPdiPerPdi(int idPdi) {
        ArrayList<ImmaginePdi> list = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT * FROM IMMAGINE_PDI WHERE idImmaginePdi_idPdi = " + idPdi + " ORDER BY ordinamento", null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                ImmaginePdi immaginePdi = new ImmaginePdi(
                        cursor.getInt(0),     // idImmaginePdi
                        cursor.getInt(1),     // idImmaginePdi_idPdi
                        cursor.getInt(2),     // ordinamento
                        cursor.getString(3)); // fileImmagine

                list.add(immaginePdi);

                cursor.moveToNext();
            }

            cursor.close();

            database.close();
        } else {
            Log.d("DEBUGAPP", TAG + " [dammiImmaginiPdiPerPdi] Connessione NON aperta!");
        }

        return list;
    }

    //endregion
}
