package nil.filmesseries;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //Declaração de objetos e variaveis
    private String TAG = "MainActivity";

    private static final int ID_CURSOR_LOADER_FS = 0;

    private RecyclerView recyclerViewFS;
    private AdaptadorFilmesSeries adaptadorFS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inicialização do loader
        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_FS, null, this);

        recyclerViewFS = findViewById(R.id.recyclerViewMain);
        adaptadorFS = new AdaptadorFilmesSeries(this);
        recyclerViewFS.setAdapter(adaptadorFS);
        recyclerViewFS.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_mostraTudo) {
            Intent intent = new Intent(this, GerirFSActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_mostraPessoas) {
            Intent intent = new Intent(this, GerirPActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_genero) {
            Intent intent = new Intent(this, GeneroActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_aMinhaLista) {
        } else if (id == R.id.action_Backup) {
            BackUpDB();
        } else {
            RestoreDB();
        }

        return super.onOptionsItemSelected(item);
    }

    public void BackUpDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            Log.d(TAG, "canWrite - " + sd.canWrite());
            if (sd.canWrite()) {
                String currentDBPath = "//data//nil.filmesseries//databases//Fs.db";
                String backupDBPath = "//FSBackup//FSbackupDB.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                Log.d(TAG, "exists - " + currentDB.exists());
                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    long i = dst.transferFrom(src, 0, src.size());
                    Log.d(TAG, "tamanho - " + i);
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

            Log.d(TAG, e + "");
            Log.d(TAG, "error a fazer backup");
        }
    }

    public void RestoreDB() {

        String currentDBPath = "//data//nil.filmesseries//databases//Fs.db";

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            File currentDB = new File(data, currentDBPath);

            Log.d(TAG, "canWrite - " + currentDB.canWrite());
            if (currentDB.canWrite()) {
                String backupDBPath = "//FSBackup//FSbackupDB.db";
                File backupDB = new File(sd, backupDBPath);

                Log.d(TAG, "exists - " + currentDB.exists());
                if (currentDB.exists()) {
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    FileChannel src = new FileInputStream(backupDB).getChannel();
                    long i = dst.transferFrom(src, 0, src.size());
                    Log.d(TAG, "tamanho - " + i);
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

            Log.d(TAG, e + "");
            Log.d(TAG, "error a fazer backup");
        }
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_FS, null, this);

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

        Cursor cursor = getContentResolver().query(FilmesContentProvider.ENDERECO_FS, null, null, null, null);
        Calendar cal = Calendar.getInstance();
        Calendar calMesSeguinte = Calendar.getInstance();
        filmesSeries fs;
        String data;
        calMesSeguinte.add(Calendar.MONTH, 1);
        ArrayList<String> ids = new ArrayList<>();

        Log.d(TAG, "cursor size " + cursor.getCount());

        while (cursor.moveToNext()) {
            fs = filmesSeries.fromCursor(cursor);
            data = fs.getData();

            try {

                cal.setLenient(false);

                //da exceção se o ano, o mes e/ou o dia não forem numeros
                cal.set(Integer.parseInt(data.substring(6)), (Integer.parseInt(data.substring(3, 5))) - 1, Integer.parseInt(data.substring(0, 2)));
            } catch (Exception e) {

                Log.d(TAG, "erro set cal");
                return null;
            }


            Log.d(TAG, cal.getTime() + "");
            Log.d(TAG, calMesSeguinte.getTime() + "");
            Log.d(TAG, cal.compareTo(calMesSeguinte) + "");

            if (cal.before(calMesSeguinte) && cal.after(Calendar.getInstance())) {

                ids.add(String.valueOf(fs.getID()));
                Log.d(TAG, "data " + fs.getNome() + " - " + cal.getTime().toString());
            }
        }

        String[] idP = new String[ids.size()];
        idP = ids.toArray(idP);

        String selection = "";

        for (int i = 1; i <= idP.length; i++) {

            if (i == idP.length) {

                selection += (BdTable_Pessoas._ID + "=?");
            } else {

                selection += (BdTable_Pessoas._ID + "=? OR ");

            }
        }

        Log.d(TAG, "selection = " + selection + ".");

        androidx.loader.content.CursorLoader cursorLoader;

        TextView textView = findViewById(R.id.textViewProximas);

        if (idP.length != 0) {

            textView.setText(R.string.proximosFS);
            textView.setTextColor(Color.BLACK);

            cursorLoader = new androidx.loader.content.CursorLoader(
                    this,
                    FilmesContentProvider.ENDERECO_FS,
                    BdTable_Filmes_series.TODAS_COLUNAS,
                    selection,
                    idP,
                    BdTable_Filmes_series.CAMPO_NOME
            );
        } else {

            textView.setText(R.string.proximos);
            textView.setTextColor(Color.RED);

            cursorLoader = new androidx.loader.content.CursorLoader(
                    this,
                    FilmesContentProvider.ENDERECO_FS,
                    BdTable_Filmes_series.TODAS_COLUNAS,
                    BdTable_Filmes_series._ID + "=?",
                    new String[]{"0"},
                    BdTable_Filmes_series.CAMPO_NOME
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
