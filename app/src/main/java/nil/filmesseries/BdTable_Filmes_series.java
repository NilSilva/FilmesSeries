package nil.filmesseries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTable_Filmes_series implements BaseColumns {

    private static final String NOME_TABELA = "Filmes_series";
    private static final String CAMPO_FORMATO = "Formato";
    private static final String CAMPO_NOME = "Nome";
    private static final String CAMPO_NUM = "NumeroEpi";
    private static final String CAMPO_EPI_VISTOS = "EpiVistos";
    private static final String CAMPO_DATA_LANC = "Data";
    private static final String CAMPO_ESTADO = "Estado";
    private final SQLiteDatabase db;

    public BdTable_Filmes_series(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_FORMATO + " INTEGER NOT NULL," +
                        CAMPO_NOME + " TEXT NOT NULL," +
                        CAMPO_NUM + " INTEGER NOT NULL," +
                        CAMPO_EPI_VISTOS + "INTEGER NOT NULL," +
                        CAMPO_DATA_LANC + " TEXT NOT NULL," +
                        CAMPO_ESTADO + " TEXT NOT NULL" +
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
