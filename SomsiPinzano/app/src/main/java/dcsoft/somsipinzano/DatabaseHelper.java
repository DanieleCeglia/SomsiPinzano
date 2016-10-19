package dcsoft.somsipinzano;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

class DatabaseHelper extends SQLiteAssetHelper {
    private static final String TAG = "DatabaseHelper ";
    private static final String NOME_DATABASE = "somsiPinzano.db";
    private static final int VERSIONE = 1;

    DatabaseHelper(Context context) {
        super(context, NOME_DATABASE, null, VERSIONE);
    }
}
