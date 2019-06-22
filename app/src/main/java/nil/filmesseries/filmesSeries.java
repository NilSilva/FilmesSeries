package nil.filmesseries;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class filmesSeries implements Parcelable {

    private long ID;
    private int formato;
    private String nome;
    private int nEpisodios;
    private int nEpiVistos;
    private String data;
    private String status;
    private byte[] imagem;

    //Para poder passar num intent------------------------------------------------------------------

    public filmesSeries() {
        this.ID = -1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeLong(ID);
        out.writeInt(formato);
        out.writeString(nome);
        out.writeInt(nEpisodios);
        out.writeInt(nEpiVistos);
        out.writeString(data);
        out.writeString(status);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<filmesSeries> CREATOR = new Parcelable.Creator<filmesSeries>() {
        public filmesSeries createFromParcel(Parcel in) {
            return new filmesSeries(in);
        }

        public filmesSeries[] newArray(int size) {
            return new filmesSeries[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private filmesSeries(Parcel in) {

        ID = in.readLong();
        formato = in.readInt();
        nome = in.readString();
        nEpisodios = in.readInt();
        nEpiVistos = in.readInt();
        data = in.readString();
        status = in.readString();
    }

    //----------------------------------------------------------------------------------------------

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

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public ContentValues getContentValues() {

        ContentValues valores = new ContentValues();

        valores.put(BdTable_Filmes_series.CAMPO_FORMATO, formato);
        valores.put(BdTable_Filmes_series.CAMPO_NOME, nome);
        valores.put(BdTable_Filmes_series.CAMPO_NUM, nEpisodios);
        valores.put(BdTable_Filmes_series.CAMPO_EPI_VISTOS, nEpiVistos);
        valores.put(BdTable_Filmes_series.CAMPO_DATA_LANC, data);
        valores.put(BdTable_Filmes_series.CAMPO_ESTADO, status);
        valores.put(BdTable_Filmes_series.CAMPO_IMAGEM, imagem);

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

        byte[] imagem = cursor.getBlob(
                cursor.getColumnIndex(BdTable_Filmes_series.CAMPO_IMAGEM)
        );

        filmesSeries fs = new filmesSeries();

        fs.setID(id);
        fs.setFormato(formato);
        fs.setNome(nome);
        fs.setnEpisodios(num);
        fs.setnEpiVistos(epiVistos);
        fs.setData(data);
        fs.setStatus(estado);
        fs.setImagem(imagem);

        return fs;
    }
}
