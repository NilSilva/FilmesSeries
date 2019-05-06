package nil.filmesseries;

import android.content.ContentValues;
import android.database.Cursor;

public class Pessoas {

    private long ID;
    private String nome;
    private String funcao;
    private String dataNascimento;

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

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTable_Pessoas.CAMPO_NOME, nome);
        valores.put(BdTable_Pessoas.CAMPO_FUNCAO, funcao);
        valores.put(BdTable_Pessoas.CAMPO_DATA_NASC, dataNascimento);

        return valores;
    }

    public static Pessoas fromCursor(Cursor cursor) {

        long id = cursor.getLong(
                cursor.getColumnIndex(BdTable_Pessoas._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTable_Pessoas.CAMPO_NOME)
        );

        String funcao = cursor.getString(
                cursor.getColumnIndex(BdTable_Pessoas.CAMPO_FUNCAO)
        );

        String data = cursor.getString(
                cursor.getColumnIndex(BdTable_Pessoas.CAMPO_DATA_NASC)
        );

        Pessoas p = new Pessoas();

        p.setID(id);
        p.setNome(nome);
        p.setFuncao(funcao);
        p.setDataNascimento(data);

        return p;
    }
}
