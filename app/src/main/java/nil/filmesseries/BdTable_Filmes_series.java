package nil.filmesseries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTable_Filmes_series implements BaseColumns {

    public static final String NOME_TABELA = "Filmes_series";
    public static final String CAMPO_FORMATO = "Formato";
    public static final String CAMPO_NOME = "Nome";
    public static final String CAMPO_NUM = "NumeroEpi";
    public static final String CAMPO_EPI_VISTOS = "EpiVistos";
    public static final String CAMPO_DATA_LANC = "Data";
    public static final String CAMPO_ESTADO = "Estado";
    public static final String[] TODAS_COLUNAS= new String[]{NOME_TABELA + "." + _ID, CAMPO_NOME, CAMPO_FORMATO, CAMPO_NUM, CAMPO_EPI_VISTOS, CAMPO_DATA_LANC, CAMPO_ESTADO};
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
                        CAMPO_EPI_VISTOS + " INTEGER NOT NULL," +
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
