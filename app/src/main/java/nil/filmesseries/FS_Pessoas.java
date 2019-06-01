package nil.filmesseries;

import android.content.ContentValues;
import android.database.Cursor;

public class FS_Pessoas {

    private long ID;
    private long ID_FS;
    private long ID_P;

    public  long getID(){
        return  ID;
    }

    public long getID_FS() {
        return ID_FS;
    }

    public void setID_FS(long ID_FS) {
        this.ID_FS = ID_FS;
    }

    public long getID_P() {
        return ID_P;
    }

    public void setID(long ID){
        this.ID = ID;
    }

    public void setID_P(long ID_P) {
        this.ID_P = ID_P;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTable_FS_Pessoas.CAMPO_ID_FS, ID_FS);
        valores.put(BdTable_FS_Pessoas.CAMPO_ID_PESSOAS, ID_P);

        return valores;
    }

    public static FS_Pessoas fromCursor(Cursor cursor) {

        long id = cursor.getLong(
                cursor.getColumnIndex(BdTable_FS_Pessoas._ID)
        );

        long id_FS = cursor.getLong(
                cursor.getColumnIndex(BdTable_FS_Pessoas.CAMPO_ID_FS)
        );

        long id_P = cursor.getLong(
                cursor.getColumnIndex(BdTable_FS_Pessoas.CAMPO_ID_PESSOAS)
        );

        FS_Pessoas FS_P = new FS_Pessoas();

        FS_P.setID(id);
        FS_P.setID_FS(id_FS);
        FS_P.setID_P(id_P);

        return FS_P;
    }
}
