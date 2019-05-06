package nil.filmesseries;

import android.content.ContentValues;
import android.database.Cursor;

public class filmesSeries {

    private long ID;
    private int formato;
    private String nome;
    private int nEpisodios;
    private int nEpiVistos;
    private String data;
    private String status;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getFormato() {
        return formato;
    }

    public void setFormato(int formato) {
        this.formato = formato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getnEpisodios() {
        return nEpisodios;
    }

    public void setnEpisodios(int nEpisodios) {
        this.nEpisodios = nEpisodios;
    }

    public int getnEpiVistos() {
        return nEpiVistos;
    }

    public void setnEpiVistos(int nEpiVistos) {
        this.nEpiVistos = nEpiVistos;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTable_Filmes_series.CAMPO_FORMATO, formato);
        valores.put(BdTable_Filmes_series.CAMPO_NOME, nome);
        valores.put(BdTable_Filmes_series.CAMPO_NUM, nEpisodios);
        valores.put(BdTable_Filmes_series.CAMPO_EPI_VISTOS, nEpiVistos);
        valores.put(BdTable_Filmes_series.CAMPO_DATA_LANC, data);
        valores.put(BdTable_Filmes_series.CAMPO_ESTADO, status);

        return valores;
    }

    public static filmesSeries fromCursor(Cursor cursor) {

        long id = cursor.getLong(
                cursor.getColumnIndex(BdTable_Filmes_series._ID)
        );

        int formato = cursor.getInt(
                cursor.getColumnIndex(BdTable_Filmes_series.CAMPO_FORMATO)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTable_Filmes_series.CAMPO_NOME)
        );

        int num = cursor.getInt(
                cursor.getColumnIndex(BdTable_Filmes_series.CAMPO_NUM)
        );

        int epiVistos = cursor.getInt(
                cursor.getColumnIndex(BdTable_Filmes_series.CAMPO_EPI_VISTOS)
        );

        String data = cursor.getString(
                cursor.getColumnIndex(BdTable_Filmes_series.CAMPO_DATA_LANC)
        );

        String estado = cursor.getString(
                cursor.getColumnIndex(BdTable_Filmes_series.CAMPO_ESTADO)
        );

        filmesSeries fs = new filmesSeries();

        fs.setID(id);
        fs.setFormato(formato);
        fs.setNome(nome);
        fs.setnEpisodios(num);
        fs.setnEpiVistos(epiVistos);
        fs.setData(data);
        fs.setStatus(estado);

        return fs;
    }
}
