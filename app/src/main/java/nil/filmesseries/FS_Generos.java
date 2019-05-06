package nil.filmesseries;

import android.content.ContentValues;
import android.database.Cursor;

public class FS_Generos {

    private long ID_FS;
    private long ID_G;

    public long getID_FS() {
        return ID_FS;
    }

    public void setID_FS(long ID_FS) {
        this.ID_FS = ID_FS;
    }

    public long getID_G() {
        return ID_G;
    }

    public void setID_G(long ID_G) {
        this.ID_G = ID_G;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTable_FS_Genero.CAMPO_ID_FS, ID_FS);
        valores.put(BdTable_FS_Genero.CAMPO_ID_GENERO, ID_G);

        return valores;
    }

    public static FS_Generos fromCursor(Cursor cursor) {

        long id_FS = cursor.getLong(
                cursor.getColumnIndex(BdTable_FS_Genero.CAMPO_ID_FS)
        );

        long id_G = cursor.getLong(
                cursor.getColumnIndex(BdTable_FS_Genero.CAMPO_ID_GENERO)
        );

        FS_Generos FS_G = new FS_Generos();

        FS_G.setID_FS(id_FS);
        FS_G.setID_G(id_G);

        return FS_G;
    }
}
