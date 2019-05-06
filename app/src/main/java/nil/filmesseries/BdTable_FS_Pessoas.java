package nil.filmesseries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTable_FS_Pessoas implements BaseColumns {

    public static final String NOME_TABELA = "FS_Genero";
    public static final String CAMPO_ID_FS = "ID_FS";
    public static final String CAMPO_ID_PESSOAS = "ID_G";
    private final SQLiteDatabase db;

    public BdTable_FS_Pessoas(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        CAMPO_ID_FS + " INTEGER NOT NULL," +
                        CAMPO_ID_PESSOAS + " INTEGER NOT NULL," +
                        "FOREIGN KEY (" + CAMPO_ID_FS + ") REFERENCES " + BdTable_Filmes_series.NOME_TABELA + "(" + BdTable_Filmes_series._ID + ")," +
                        "FOREIGN KEY (" + CAMPO_ID_PESSOAS + ") REFERENCES " + BdTable_Pessoas.NOME_TABELA + "(" + BdTable_Pessoas._ID + ")" +
                        ")"
        );
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    public int update(ContentValues values, String whereClause, String [] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
