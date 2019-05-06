package nil.filmesseries;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BdFilmesSeriesTeste {

    @Before
    public void apagaBaseDados() {
        getAppContext().deleteDatabase(BdFsOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void criaBdFS() {
        // Context of the app under test.
        Context appContext = getAppContext();

        BdFsOpenHelper openHelper = new BdFsOpenHelper(appContext);

        SQLiteDatabase db = openHelper.getReadableDatabase();

        assertTrue(db.isOpen());
    }

    private Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testCRUD() {
        BdFsOpenHelper openHelper = new BdFsOpenHelper(getAppContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTable_Filmes_series tabelaFS = new BdTable_Filmes_series(db);

        filmesSeries fs = new filmesSeries();
        fs.setNome("Green Book");
        fs.setFormato(1);
        fs.setnEpisodios(1);
        fs.setnEpiVistos(0);
        fs.setData("11/09/2018");
        fs.setStatus("Not seen");
        long idFilme = tabelaFS.insert(fs.getContentValues());
        assertNotEquals(-1, idFilme);

        BdTable_Pessoas tabelaP = new BdTable_Pessoas(db);

        Pessoas p = new Pessoas();
        p.setNome("Denzel Washington");
        p.setFuncao("Actor");
        p.setDataNascimento("28/12/1954");
        long idPessoa = tabelaP.insert(p.getContentValues());
        assertNotEquals(-1, idPessoa);

        BdTable_Genero tabelaG = new BdTable_Genero(db);

        Generos g = new Generos();
        g.setNome("Comédia dramática");
        long idGenero = tabelaG.insert(g.getContentValues());
        assertNotEquals(-1, idGenero);

        BdTable_FS_Pessoas tabela_FS_P = new BdTable_FS_Pessoas(db);

        FS_Pessoas fs_p = new FS_Pessoas();
        fs_p.setID_FS(fs.getID());
        fs_p.setID_P(p.getID());
        long id_FS_P = tabela_FS_P.insert(fs_p.getContentValues());
        assertNotEquals(-1, id_FS_P);

        BdTable_FS_Genero tabela_FS_G = new BdTable_FS_Genero(db);

        FS_Generos fs_g = new FS_Generos();
        fs_g.setID_FS(fs.getID());
        fs_g.setID_G(g.getID());
        long id_FS_G = tabela_FS_G.insert(fs_g.getContentValues());
        assertNotEquals(-1, id_FS_G);
    }
}
