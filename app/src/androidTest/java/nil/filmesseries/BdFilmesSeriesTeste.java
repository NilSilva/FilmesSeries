package nil.filmesseries;

import android.content.Context;
import android.database.Cursor;
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

        BdFsOpenHelper openHelper = new BdFsOpenHelper(getAppContext());

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

        //Teste read filmes/series---------------------------------------------------------------------------
        Cursor cursorFS = getFS(tabelaFS);
        assertEquals(0, cursorFS.getCount());

        //Test create/read FS
        String nome ="Green Book";
        int formato = 1;
        int num = 1;
        int epiVistos = 0;
        String data = "11/09/2018";
        String estado = "Not seen";
        long idGB = criaFS(tabelaFS, nome, formato, num, epiVistos, data, estado);

        cursorFS = getFS(tabelaFS);
        assertEquals(1, cursorFS.getCount());

        filmesSeries FS = getFScomID(cursorFS, idGB);

        assertEquals(nome, FS.getNome());
        assertEquals(formato, FS.getFormato());
        assertEquals(num, FS.getnEpisodios());
        assertEquals(epiVistos, FS.getnEpiVistos());
        assertEquals(data, FS.getData());
        assertEquals(estado, FS.getStatus());

        nome ="Detective Pikachu";
        formato = 1;
        num = 1;
        epiVistos = 1;
        data = "10/05/2019";
        estado = "Watched";
        long idDP = criaFS(tabelaFS, nome, formato, num, epiVistos, data, estado);

        cursorFS = getFS(tabelaFS);
        assertEquals(2, cursorFS.getCount());

        FS = getFScomID(cursorFS, idDP);

        assertEquals(nome, FS.getNome());
        assertEquals(formato, FS.getFormato());
        assertEquals(num, FS.getnEpisodios());
        assertEquals(epiVistos, FS.getnEpiVistos());
        assertEquals(data, FS.getData());
        assertEquals(estado, FS.getStatus());

        //update filmes

        nome ="John Wick: Chapter 3";
        formato = 1;
        num = 1;
        epiVistos = 0;
        data = "17/05/2019";
        estado = "Not seen";
        FS.setNome(nome);
        FS.setFormato(formato);
        FS.setnEpisodios(num);
        FS.setnEpiVistos(epiVistos);
        FS.setData(data);
        FS.setStatus(estado);

        int registosAlterados = tabelaFS.update(FS.getContentValues(), BdTable_Filmes_series._ID + "=?", new String[]{String.valueOf(idDP)});

        assertEquals(1, registosAlterados);

        cursorFS = getFS(tabelaFS);
        FS = getFScomID(cursorFS, idDP);

        assertEquals(nome, FS.getNome());
        assertEquals(formato, FS.getFormato());
        assertEquals(num, FS.getnEpisodios());
        assertEquals(epiVistos, FS.getnEpiVistos());
        assertEquals(data, FS.getData());
        assertEquals(estado, FS.getStatus());

        //Teste create/delete/read filme
        nome ="testeNome";
        formato = 1;
        num = 1;
        epiVistos = 0;
        data = "testeData";
        estado = "testeEstado";
        long id = criaFS(tabelaFS, nome, formato, num, epiVistos, data, estado);
        cursorFS = getFS(tabelaFS);
        assertEquals(3, cursorFS.getCount());

        tabelaFS.delete(BdTable_Filmes_series._ID + "=?", new String[]{String.valueOf(id)});
        cursorFS = getFS(tabelaFS);
        assertEquals(2, cursorFS.getCount());

        getFScomID(cursorFS, idGB);
        getFScomID(cursorFS, idDP);

        //Teste read pessoas---------------------------------------------------------------------------

        BdTable_Pessoas tabelaP = new BdTable_Pessoas(db);

        Cursor cursorP = getP(tabelaP);
        assertEquals(0, cursorP.getCount());

        //Teste create/read pessoas
        String nomeP = "Denzel Washington";
        String funcaoP = "Actor/Actress";
        String dataP = "28/12/1954";

        long idDW = criaP(tabelaP, nomeP, funcaoP, dataP);
        cursorP = getP(tabelaP);
        assertEquals(1, cursorP.getCount());

        Pessoas pessoas = getPcomID(cursorP, idDW);

        assertEquals(nomeP, pessoas.getNome());
        assertEquals(funcaoP, pessoas.getFuncao());
        assertEquals(dataP, pessoas.getDataNascimento());

        nomeP = "Natalie Portman";
        funcaoP = "Actor/Actress";
        dataP = "09/06/1981";

        long idNP = criaP(tabelaP, nomeP, funcaoP, dataP);
        cursorP = getP(tabelaP);
        assertEquals(2, cursorP.getCount());

        pessoas = getPcomID(cursorP, idNP);

        assertEquals(nomeP, pessoas.getNome());
        assertEquals(funcaoP, pessoas.getFuncao());
        assertEquals(dataP, pessoas.getDataNascimento());

        //update generos

        nomeP = "James Cameron";
        funcaoP = "Realizador/Realizadora";
        dataP = "16/08/1954";
        pessoas.setNome(nomeP);
        pessoas.setFuncao(funcaoP);
        pessoas.setDataNascimento(dataP);

        registosAlterados = tabelaP.update(pessoas.getContentValues(), BdTable_Genero._ID + "=?", new String[]{String.valueOf(idNP)});

        assertEquals(1, registosAlterados);

        cursorP = getP(tabelaP);
        pessoas = getPcomID(cursorP, idNP);

        assertEquals(nomeP, pessoas.getNome());
        assertEquals(funcaoP, pessoas.getFuncao());
        assertEquals(dataP, pessoas.getDataNascimento());

        //teste create/delete/read pessoas

        nomeP = "Natalie Portman";
        funcaoP = "Actor/Actress";
        dataP = "09/06/1981";

        id = criaP(tabelaP, nomeP, funcaoP, dataP);
        cursorP = getP(tabelaP);
        assertEquals(3, cursorP.getCount());

        tabelaP.delete(BdTable_Pessoas._ID + "=?", new String[]{String.valueOf(id)});
        cursorP = getP(tabelaP);
        assertEquals(2, cursorP.getCount());

        //Teste read FS_Pessoas----------------------------------------------------------------------------------

        BdTable_FS_Pessoas tabelaFS_P = new BdTable_FS_Pessoas(db);

        Cursor cursorFS_P = getFS_P(tabelaFS_P);
        assertEquals(0, cursorFS_P.getCount());

        //Teste create/read FS_Pessoas

        criaFS_P(tabelaFS_P, idDW, idDP);
        cursorFS_P = getFS_P(tabelaFS_P);
        assertEquals(1, cursorFS_P.getCount());

        FS_Pessoas fs_p = getFS_PcomID(cursorFS_P, idDP);

        assertEquals(idDP, fs_p.getID_FS());
        assertEquals(idDW, fs_p.getID_P());

        //update FS_Pessoas

        fs_p.setID_FS(idGB);
        fs_p.setID_P(idNP);

        registosAlterados = tabelaFS_P.update(fs_p.getContentValues(), BdTable_FS_Pessoas.CAMPO_ID_FS + "=?", new String[]{String.valueOf(idDP)});

        assertEquals(1, registosAlterados);

        cursorFS_P = getFS_P(tabelaFS_P);
        fs_p = getFS_PcomID(cursorFS_P, idGB);

        assertEquals(idGB, fs_p.getID_FS());
        assertEquals(idNP, fs_p.getID_P());

        //teste create/delete/read FS_Pessoas

        cursorFS_P = getFS_P(tabelaFS_P);
        assertEquals(1, cursorFS_P.getCount());

        tabelaFS_P.delete(BdTable_FS_Pessoas.CAMPO_ID_FS + "=?", new String[]{String.valueOf(idGB)});
        cursorFS_P = getFS_P(tabelaFS_P);
        assertEquals(0, cursorFS_P.getCount());

        //Teste read generos-------------------------------------------------------------------------------------

        BdTable_Genero tabelaG = new BdTable_Genero(db);

        Cursor cursorG = getG(tabelaG);
        assertEquals(0, cursorG.getCount());

        //Teste create/read generos
        String nomeG = "Romance";

        long idR = criaG(tabelaG, nomeG);
        cursorG = getG(tabelaG);
        assertEquals(1, cursorG.getCount());

        Generos generos = getGcomID(cursorG, idR);

        assertEquals(nomeG, generos.getNome());

        nomeP = "Ficção";

        long idF = criaG(tabelaG, nomeG);
        cursorG = getG(tabelaG);
        assertEquals(2, cursorG.getCount());

        generos = getGcomID(cursorG, idF);

        assertEquals(nomeG, generos.getNome());

        //update generos

        nomeG ="Ação";
        generos.setNome(nomeG);

        registosAlterados = tabelaG.update(generos.getContentValues(), BdTable_Genero._ID + "=?", new String[]{String.valueOf(idF)});

        assertEquals(1, registosAlterados);

        cursorG = getG(tabelaG);
        generos = getGcomID(cursorG, idF);

        assertEquals(nomeG, generos.getNome());

        //teste create/delete/read generos

        nomeG = "testeGenero";

        id = criaG(tabelaG, nomeG);
        cursorG = getG(tabelaG);
        assertEquals(3, cursorG.getCount());

        tabelaG.delete(BdTable_Genero._ID + "=?", new String[]{String.valueOf(id)});
        cursorG = getG(tabelaG);
        assertEquals(2, cursorG.getCount());

        //Teste read FS_Generos----------------------------------------------------------------------------------

        BdTable_FS_Genero tabelaFS_G = new BdTable_FS_Genero(db);

        Cursor cursorFS_G = getFS_G(tabelaFS_G);
        assertEquals(0, cursorFS_G.getCount());

        //Teste create/read FS_Generos

        criaFS_G(tabelaFS_G, idDP, idR);
        cursorFS_G = getFS_G(tabelaFS_G);
        assertEquals(1, cursorFS_G.getCount());

        FS_Generos fs_g = getFS_GcomID(cursorFS_G, idDP);

        assertEquals(idDP, fs_g.getID_FS());
        assertEquals(idR, fs_g.getID_G());

        //update FS_Generos

        fs_g.setID_FS(idGB);
        fs_g.setID_G(idF);

        registosAlterados = tabelaFS_G.update(fs_g.getContentValues(), BdTable_FS_Genero.CAMPO_ID_FS + "=?", new String[]{String.valueOf(idDP)});

        assertEquals(1, registosAlterados);

        cursorFS_G = getFS_G(tabelaFS_G);
        fs_g = getFS_GcomID(cursorFS_G, idGB);

        assertEquals(idGB, fs_g.getID_FS());
        assertEquals(idNP, fs_g.getID_G());

        //teste create/delete/read FS_Generos

        cursorFS_G = getFS_G(tabelaFS_G);
        assertEquals(1, cursorFS_G.getCount());

        tabelaFS_G.delete(BdTable_FS_Genero.CAMPO_ID_FS + "=?", new String[]{String.valueOf(idGB)});
        cursorFS_G = getFS_G(tabelaFS_G);
        assertEquals(0, cursorFS_G.getCount());

    }

    private FS_Generos getFS_GcomID(Cursor cursor, long id) {

        FS_Generos fs_g = null;

        while (cursor.moveToNext()){
            fs_g = FS_Generos.fromCursor(cursor);

            if(fs_g.getID_FS() == id){
                break;
            }
        }
        assertNotNull(fs_g);

        return fs_g;
    }

    private void criaFS_G(BdTable_FS_Genero tabelaFS_g, long id_fs, long id_g) {
        FS_Generos FS_G = new FS_Generos();

        FS_G.setID_G(id_g);
        FS_G.setID_FS(id_fs);

        long id = tabelaFS_g.insert(FS_G.getContentValues());
        assertNotEquals(-1, id);
    }

    private Cursor getFS_G(BdTable_FS_Genero tabelaFS_g) {
        return tabelaFS_g.query(BdTable_FS_Genero.TODAS_COLUNAS, null, null, null, null, null);
    }

    private FS_Pessoas getFS_PcomID(Cursor cursor, long id) {

        FS_Pessoas fs_p = null;

        while (cursor.moveToNext()){
            fs_p = FS_Pessoas.fromCursor(cursor);

            if(fs_p.getID_FS() == id){
                break;
            }
        }
        assertNotNull(fs_p);

        return fs_p;
    }

    private void criaFS_P(BdTable_FS_Pessoas tabelaFS_P, long ID_P, long ID_FS) {

        FS_Pessoas FS_P = new FS_Pessoas();

        FS_P.setID_P(ID_P);
        FS_P.setID_FS(ID_FS);

        long id = tabelaFS_P.insert(FS_P.getContentValues());
        assertNotEquals(-1, id);
    }

    private Cursor getFS_P(BdTable_FS_Pessoas tabelaFS_p) {
        return tabelaFS_p.query(BdTable_FS_Pessoas.TODAS_COLUNAS, null, null, null, null, null);
    }

    private Generos getGcomID(Cursor cursor, long id) {
        Generos G = null;

        while (cursor.moveToNext()){
            G = Generos.fromCursor(cursor);

            if(G.getID() == id){
                break;
            }
        }
        assertNotNull(G);

        return G;
    }

    private long criaG(BdTable_Genero tabelaG, String nome) {
        Generos G = new Generos();

        G.setNome(nome);

        long id = tabelaG.insert(G.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getG(BdTable_Genero tabelaG) {
        return tabelaG.query(BdTable_Genero.TODAS_COLUNAS, null, null, null, null, null);
    }

    private Pessoas getPcomID(Cursor cursor, long id) {
        Pessoas P = null;

        while (cursor.moveToNext()){
            P = Pessoas.fromCursor(cursor);

            if(P.getID() == id){
                break;
            }
        }
        assertNotNull(P);

        return P;
    }

    private long criaP(BdTable_Pessoas tabelaP, String nome, String funcao, String data) {
        Pessoas P = new Pessoas();

        P.setNome(nome);
        P.setFuncao(funcao);
        P.setDataNascimento(data);
        P.setImagem(new byte[]{});

        long id = tabelaP.insert(P.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getP(BdTable_Pessoas tabelaP) {
        return tabelaP.query(BdTable_Pessoas.TODAS_COLUNAS, null, null, null, null, null);
    }

    private filmesSeries getFScomID(Cursor cursor, long id) {
        filmesSeries FS = null;

        while(cursor.moveToNext()){
            FS = filmesSeries.fromCursor(cursor);

            if(FS.getID() == id){
                break;
            }
        }

        assertNotNull(FS);

        return FS;
    }

    private long criaFS(BdTable_Filmes_series tabelaFS, String nome, int formato, int num, int epiVistos, String data, String estado) {
        filmesSeries FS = new filmesSeries();

        FS.setNome(nome);
        FS.setFormato(formato);
        FS.setnEpisodios(num);
        FS.setnEpiVistos(epiVistos);
        FS.setData(data);
        FS.setStatus(estado);
        FS.setImagem(new byte[]{});

        long id = tabelaFS.insert(FS.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getFS(BdTable_Filmes_series tabelaFS) {
        return  tabelaFS.query(BdTable_Filmes_series.TODAS_COLUNAS, null, null, null, null, null);
    }
}
