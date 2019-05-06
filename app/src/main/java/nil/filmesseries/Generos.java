package nil.filmesseries;

import android.content.ContentValues;
import android.database.Cursor;

public class Generos {

    private long ID;
    private String nome;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTable_Genero.CAMPO_NOME, nome);

        return valores;
    }

    public static Generos fromCursor(Cursor cursor) {

        long id = cursor.getLong(
                cursor.getColumnIndex(BdTable_Genero._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTable_Genero.CAMPO_NOME)
        );

        Generos g = new Generos();

        g.setID(id);
        g.setNome(nome);

        return g;
    }
}
