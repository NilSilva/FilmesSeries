package nil.filmesseries;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTable_Pessoas implements BaseColumns {

    private String TAG = "BdTable_Pessoas";

    public static final String NOME_TABELA = "Pessoas";
    public static final String CAMPO_NOME = "Nome";
    public static final String CAMPO_DATA_NASC = "Data_nasc";
    public static final String CAMPO_FUNCAO = "Funcao";
    public static final String CAMPO_IMAGEM = "Foto";
    public static final String[] TODAS_COLUNAS = new String[]{NOME_TABELA + "." + _ID, CAMPO_NOME, CAMPO_FUNCAO, CAMPO_DATA_NASC, CAMPO_IMAGEM};
    private final SQLiteDatabase db;

    public BdTable_Pessoas(SQLiteDatabase db) {

        this.db = db;
    }

    public void cria() {

        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_NOME + " TEXT NOT NULL," +
                        CAMPO_FUNCAO + " TEXT NOT NULL," +
                        CAMPO_DATA_NASC + " TEXT NOT NULL," +
                        CAMPO_IMAGEM + " BLOB NOT NULL" +
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
