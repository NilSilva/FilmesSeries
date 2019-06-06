package nil.filmesseries;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Pessoas implements Parcelable {

    private long ID;
    private String nome;
    private String funcao;
    private String dataNascimento;

    //Para poder passar num intent------------------------------------------------------------------

    public Pessoas() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeLong(ID);
        out.writeString(nome);
        out.writeString(funcao);
        out.writeString(dataNascimento);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Pessoas> CREATOR = new Parcelable.Creator<Pessoas>() {
        public Pessoas createFromParcel(Parcel in) {
            return new Pessoas(in);
        }

        public Pessoas[] newArray(int size) {
            return new Pessoas[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Pessoas(Parcel in) {

        ID = in.readLong();
        nome = in.readString();
        funcao = in.readString();
        dataNascimento = in.readString();
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
