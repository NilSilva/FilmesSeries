package nil.filmesseries;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GeneroActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //Declaração de objetos e variaveis
    private String TAG = "GeneroActivity";

    private static final int ID_CURSO_LOADER_GENEROS = 0;

    private RecyclerView recyclerViewG;
    private AdaptadorGeneros adaptadorG;

    Spinner spinner1;
    Spinner spinner2;

    Uri Endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genero);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner1 = findViewById(R.id.spinnerApagarG);
        spinner2 = findViewById(R.id.spinnerEditarG);

        //inicialização do loader
        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_GENEROS, null, this);

        recyclerViewG = findViewById(R.id.recyclerViewGenero);
        adaptadorG = new AdaptadorGeneros(this);
        recyclerViewG.setAdapter(adaptadorG);
        recyclerViewG.setLayoutManager(new LinearLayoutManager(this));
    }

    private void atualizaSpinner(Cursor cursor) {

        SimpleCursorAdapter Adaptador = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1, cursor,
                new String[]{BdTable_Genero.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        spinner1.setAdapter(Adaptador);
        spinner2.setAdapter(Adaptador);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_GENEROS, null, this);

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
        CursorLoader cursorLoader = new CursorLoader(
                this,
                FilmesContentProvider.ENDERECO_GENERO,
                BdTable_Genero.TODAS_COLUNAS,
                null,
                null,
                BdTable_Genero.CAMPO_NOME
        );

        return cursorLoader;
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link //FragmentManager#beginTransaction()
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
     * and you place it in a {@link //CursorAdapter}, use
     * the {@link //CursorAdapter#CursorAdapter(Context,
     * Cursor, int)} constructor <em>without</em> passing
     * in either {@link// CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link //CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link //CursorAdapter}, you should use the
     * {@link //CursorAdapter#swapCursor(Cursor)}
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

        atualizaSpinner(data);
        adaptadorG.setCursor(data);
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

        atualizaSpinner(null);
        adaptadorG.setCursor(null);
    }

    public void Add(View view) {

        //-------------------------------------------Declaração de variaveis-------------------------------------------
        EditText editTextCampo = findViewById(R.id.editTextAdicionarG);
        String textoCampo = editTextCampo.getText().toString();
        Generos g = new Generos();

        //ve se foi introduzido um nome
        if (textoCampo.trim().isEmpty()) {

            editTextCampo.setError("Add a name");
            editTextCampo.requestFocus();
        } else {

            g.setNome(textoCampo.trim());
            editTextCampo.setText("");
            editTextCampo.setError(null);
            editTextCampo.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            Uri uri = getContentResolver().insert(FilmesContentProvider.ENDERECO_GENERO, g.getContentValues());
            long id = ContentUris.parseId(uri);
            Log.d(TAG, "id inserir - " + id);
            onResume();
            Toast.makeText(this, "Genero adicionado", Toast.LENGTH_SHORT).show();
        }
    }

    public void Delete(View view) {

        //-------------------------------------------Declaração de variaveis-------------------------------------------
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final Spinner spin = findViewById(R.id.spinnerApagarG);
        TextView errorText = (TextView) spin.getSelectedView();

        //-------------------------------------------Construção do AlertDialog-------------------------------------------

        builder.setTitle(getString(R.string.Are_you_sure));
        builder.setMessage("This will delete this person.");
        builder.setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Endereco = Uri.withAppendedPath(FilmesContentProvider.ENDERECO_GENERO, String.valueOf(spinner1.getSelectedItemId()));
                int i = getContentResolver().delete(Endereco, null, null);
                Log.d(TAG, "numero de linhas - " + i);
                Log.d(TAG, "id apagar - " + String.valueOf(spinner1.getSelectedItemId()));
                onResume();
            }
        });
        builder.setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void Save(View view) {

        //-------------------------------------------Declaração de variaveis-------------------------------------------
        EditText editTextCampo = findViewById(R.id.editTextEditarG);
        String textoCampo = editTextCampo.getText().toString();

        editTextCampo.setText("");
        editTextCampo.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        Generos g = new Generos();

        g.setNome(textoCampo);

        Endereco = Uri.withAppendedPath(FilmesContentProvider.ENDERECO_GENERO, String.valueOf(spinner2.getSelectedItemId()));
        int i = getContentResolver().update(Endereco, g.getContentValues(), null, null);
        Log.d(TAG, "numero de linhas - " + i);
        Log.d(TAG, "id editar - " + spinner1.getSelectedItemId());
        onResume();

        Toast.makeText(this, "Alteração guardada", Toast.LENGTH_SHORT).show();
    }
}
