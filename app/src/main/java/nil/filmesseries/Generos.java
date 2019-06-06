package nil.filmesseries;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Generos implements Parcelable {

    private long ID;
    private String nome;

    //Para poder passar num intent------------------------------------------------------------------

    public Generos() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeLong(ID);
        out.writeString(nome);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Generos> CREATOR = new Parcelable.Creator<Generos>() {
        public Generos createFromParcel(Parcel in) {
            return new Generos(in);
        }

        public Generos[] newArray(int size) {
            return new Generos[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Generos(Parcel in) {

        ID = in.readLong();
        nome = in.readString();
    }

    //----------------------------------------------------------------------------------------------

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
