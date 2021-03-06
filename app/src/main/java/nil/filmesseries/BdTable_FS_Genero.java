package nil.filmesseries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTable_FS_Genero implements BaseColumns {

    private String TAG = "BdTable_FS_Genero";

    public static final String NOME_TABELA = "FS_Genero";
    public static final String CAMPO_ID_FS = "ID_FS";
    public static final String CAMPO_ID_GENERO = "ID_G";
    public static final String[] TODAS_COLUNAS = new String[]{NOME_TABELA + "." + _ID, CAMPO_ID_FS, CAMPO_ID_GENERO};
    private final SQLiteDatabase db;

    public BdTable_FS_Genero(SQLiteDatabase db) {

        this.db = db;
    }

    public void cria() {

        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_ID_FS + " INTEGER NOT NULL," +
                        CAMPO_ID_GENERO + " INTEGER NOT NULL," +
                        "FOREIGN KEY (" + CAMPO_ID_FS + ") REFERENCES " + BdTable_Filmes_series.NOME_TABELA + "(" + BdTable_Filmes_series._ID + ")," +
                        "FOREIGN KEY (" + CAMPO_ID_GENERO + ") REFERENCES " + BdTable_Genero.NOME_TABELA + "(" + BdTable_Genero._ID + ")" +
                        ")"
        );
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public long insert(ContentValues values) {

        return db.insert(NOME_TABELA, null, values);
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs) {

        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {

        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
