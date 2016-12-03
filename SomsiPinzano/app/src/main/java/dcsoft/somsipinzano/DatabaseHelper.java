package dcsoft.somsipinzano;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

class DatabaseHelper extends SQLiteAssetHelper {
    private final String TAG = getClass().getSimpleName();
    private static final String NOME_DATABASE = "somsiPinzano002.db";
    private static final int VERSIONE = 1;

    DatabaseHelper(Context context) {
        super(context, NOME_DATABASE, null, VERSIONE);
    }
}
