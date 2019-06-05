package nil.filmesseries;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GerirFSActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //Declaração de objetos e variaveis
    private String TAG = "GerirFSActivity";

    private static final int ID_CURSOR_LOADER_FS = 0;

    private RecyclerView recyclerViewFS;
    private AdaptadorFilmesSeries adaptadorFS;

    private Spinner spins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerir_fs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //inicialização dos objetos
        spins = findViewById(R.id.GerirFSSpinner);

        //Reinicia o loader quando o item selecionado do spinner muda
        spins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onResume();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //inicialização do loader
        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_FS, null, this);

        recyclerViewFS = (RecyclerView) findViewById(R.id.recyclerViewFS);
        adaptadorFS = new AdaptadorFilmesSeries(this);
        recyclerViewFS.setAdapter(adaptadorFS);
        recyclerViewFS.setLayoutManager(new LinearLayoutManager(this));
    }

    private void restartLoader() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_FS, null, this);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_FS, null, this);

        super.onResume();
    }

    public void AdicionarFS(View view) {

        Intent intent = new Intent(this, AdicionarFSActivity.class);
        startActivity(intent);
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
    public Loader onCreateLoader(int id, @Nullable Bundle args) {

        CursorLoader cursorLoader;

        spins = findViewById(R.id.GerirFSSpinner);

        if (spins.getSelectedItemPosition() == 2) {

            cursorLoader = new CursorLoader(
                    this, FilmesContentProvider.ENDERECO_FS, BdTable_Filmes_series.TODAS_COLUNAS, BdTable_Filmes_series.CAMPO_FORMATO + "=?", new String[]{"0"}, BdTable_Filmes_series.CAMPO_NOME
            );
        } else if (spins.getSelectedItemPosition() == 1) {

            cursorLoader = new CursorLoader(
                    this, FilmesContentProvider.ENDERECO_FS, BdTable_Filmes_series.TODAS_COLUNAS, BdTable_Filmes_series.CAMPO_FORMATO + "=?", new String[]{"1"}, BdTable_Filmes_series.CAMPO_NOME
            );
        } else {

            cursorLoader = new CursorLoader(
                    this, FilmesContentProvider.ENDERECO_FS, BdTable_Filmes_series.TODAS_COLUNAS, null, null, BdTable_Filmes_series.CAMPO_NOME
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
        adaptadorFS.setCursor(data);
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
        adaptadorFS.setCursor(null);
    }
}
