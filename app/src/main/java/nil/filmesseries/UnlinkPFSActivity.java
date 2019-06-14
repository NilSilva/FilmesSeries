package nil.filmesseries;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UnlinkPFSActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //Declaração de objetos e variaveis
    private String TAG = "UnlinkPFSActivity";

    private static final int ID_CURSOR_LOADER_PESSOAS = 0;

    private RecyclerView recyclerViewP;
    private AdaptadorPessoas adaptadorP;

    private filmesSeries fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlink_pfs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        fs = intent.getParcelableExtra("FS");

        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_PESSOAS, null, this);
        recyclerViewP = (RecyclerView) findViewById(R.id.recyclerViewRemoverPFS);
        adaptadorP = new AdaptadorPessoas(this, fs);
        recyclerViewP.setAdapter(adaptadorP);
        recyclerViewP.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {

        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_PESSOAS, null, this);

        super.onResume();
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        Cursor cursor = getContentResolver().query(FilmesContentProvider.ENDERECO_FS_PESSOAS, null, null, null, null);

        ArrayList<String> ids = new ArrayList<>();

        FS_Pessoas fs_p = new FS_Pessoas();

        while(cursor.moveToNext()){

            fs_p = FS_Pessoas.fromCursor(cursor);

            if(fs_p.getID_FS() == fs.getID()){

                ids.add(String.valueOf(fs_p.getID_P()));
                Log.d(TAG, "ID filme:" + fs_p.getID_FS());
                Log.d(TAG, "ID actor:" + fs_p.getID_P());
            }
        }

        String[] idP = new String[ids.size()];
        idP = ids.toArray(idP);

        Log.d(TAG, "String[] = " + idP);

        String selection = "";

        Log.d(TAG, "idP.lenght = " + idP.length);

        for(int i = 0;i < idP.length;i++){

            if(i == idP.length - 1){

                selection += (BdTable_Pessoas._ID + "=?");
            }else{

                selection += (BdTable_Pessoas._ID + "=? OR ");

            }
        }

        Log.d(TAG, "selection = " + selection + ".");

        androidx.loader.content.CursorLoader cursorLoader;

        if(idP.length != 0){

            cursorLoader = new androidx.loader.content.CursorLoader(
                    this,
                    FilmesContentProvider.ENDERECO_PESSOAS,
                    BdTable_Pessoas.TODAS_COLUNAS,
                    selection,
                    idP,
                    BdTable_Pessoas.CAMPO_NOME
            );
        }else {

            cursorLoader = new androidx.loader.content.CursorLoader(
                    this,
                    FilmesContentProvider.ENDERECO_PESSOAS,
                    BdTable_Pessoas.TODAS_COLUNAS,
                    BdTable_Pessoas._ID + "=?",
                    new String[]{"0"},
                    BdTable_Pessoas.CAMPO_NOME
            );
        }

        return cursorLoader;
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     *
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context,
     * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        adaptadorP.setCursor(data);
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        adaptadorP.setCursor(null);
    }
}
