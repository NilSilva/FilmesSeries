package nil.filmesseries;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.util.Calendar;

public class EditarFSActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, PopupMenu.OnMenuItemClickListener {

    //Declaração de objetos e variaveis
    private String TAG = "EditarFSActivity";

    private EditText nome;
    private EditText num;
    private EditText epiVistos;
    private EditText data;
    private Button button;
    private RadioGroup RadioG;
    private RadioButton RadioF;
    private RadioButton RadioS;
    private Spinner spins;
    private filmesSeries fs;

    private boolean flag = false;

    private static final int ID_CURSOR_LOADER_PESSOAS = 0;

    private RecyclerView recyclerViewP;
    private AdaptadorPessoas adaptadorP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_fs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //inicialização dos objetos
        nome = findViewById(R.id.editTextEditarNomeFS);
        nome.setEnabled(false);

        num = findViewById(R.id.editTextEditarNumFS);
        num.setEnabled(false);

        epiVistos = findViewById(R.id.editTextEditarEpiVistosFS);
        epiVistos.setEnabled(false);

        data = findViewById(R.id.editTextEditarDataFS);
        data.setEnabled(false);

        button = findViewById(R.id.butãoEditarSaveFS);

        RadioG = findViewById(R.id.radioGroupEditarFS);

        spins = findViewById(R.id.spinnerEditarEstadoFS);
        spins.setEnabled(false);

        RadioF = findViewById(R.id.radioButtonEditarFSFilme);
        RadioF.setEnabled(false);

        RadioS = findViewById(R.id.radioButtonEditarFSSerie);
        RadioS.setEnabled(false);

        //controlo do butão para guardar
        //alguma da verificação tambem é feita aqui, nomeadamente ver se os campos estão preenchidos
        nome.addTextChangedListener(Campos);
        num.addTextChangedListener(Campos);
        epiVistos.addTextChangedListener(Campos);
        data.addTextChangedListener(Campos);
        RadioG.setOnCheckedChangeListener(CampoRadioG);
        spins.setOnItemSelectedListener(CampoSpinner);

        Intent intent = getIntent();
        fs = (filmesSeries) intent.getParcelableExtra("FS");

        if (fs.getFormato() == 0) {

            RadioS.setChecked(true);
        } else {

            RadioF.setChecked(true);
        }

        nome.setText(fs.getNome());
        num.setText(String.valueOf(fs.getnEpisodios()));
        epiVistos.setText(String.valueOf(fs.getnEpiVistos()));
        data.setText(fs.getData());

        for (int i = 1; i < spins.getCount(); i++) {

            if (spins.getItemAtPosition(i).toString().equals(fs.getStatus())) {
                spins.setSelection(i);
                break;
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewP = (RecyclerView) findViewById(R.id.recyclerViewFS_P);
        recyclerViewP.setLayoutManager(layoutManager);
        adaptadorP = new AdaptadorPessoas(this);
        recyclerViewP.setAdapter(adaptadorP);
    }

    //é ativado quando o item selecionado no spinner muda
    private AdapterView.OnItemSelectedListener CampoSpinner = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

            CamposPreenchidos();

            if (spins.getSelectedItemPosition() == 0) {

                button.setEnabled(false);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }

    };

    //é ativado quando o campo a que esta atribuido tem uma mudança no texto
    private TextWatcher Campos = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            CamposPreenchidos();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    //é ativado quando um dos radioButtons é pressionado
    private RadioGroup.OnCheckedChangeListener CampoRadioG = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            CamposPreenchidos();
        }
    };

    //ativa o butão se todos os campos estiverem preenchidos
    private void CamposPreenchidos() {

        String nomeInput = nome.getText().toString().trim();
        String numInput = num.getText().toString().trim();
        String epiVistosInput = epiVistos.getText().toString().trim();
        String dataInput = data.getText().toString().trim();
        int radioG = RadioG.getCheckedRadioButtonId();

        if (radioG != -1 && spins.getSelectedItemPosition() != 0) {

            button.setEnabled(!nomeInput.isEmpty() && !numInput.isEmpty() && !epiVistosInput.isEmpty() && !dataInput.isEmpty());
        }
    }

    public void Cancel(View view) {

        finish();
    }

    public void Save() {

        String textoCampo;
        boolean Erros = false;
        Calendar cal = Calendar.getInstance();

        //-------------------------------------------Verificação da data-------------------------------------------
        textoCampo = data.getText().toString();

        try {

            cal.setLenient(false);

            //da exceção se o ano, o mes e/ou o dia não forem validos
            cal.set(Integer.parseInt(textoCampo.substring(6)), (Integer.parseInt(textoCampo.substring(3, 5))) - 1, Integer.parseInt(textoCampo.substring(0, 2)));

            //ve se ano é valido(1º filme é de 1895, logo 1890 parece um bom valor)
            //da exceção se a data for invalida
            if (cal.get(Calendar.YEAR) < 1890) {

                data.setError(getString(R.string.data_invalida));
                data.requestFocus();
                Erros = true;
            }
        } catch (Exception e) {

            data.setError(getString(R.string.data_invalida));
            data.requestFocus();
            Erros = true;
        }

        //-------------------------------------------Verificação de episodios vistos-------------------------------------------
        textoCampo = epiVistos.getText().toString();

        epiVistos.setError("");
        int epi = -1;
        int epiVisto = -1;

        try {

            epiVisto = Integer.parseInt(textoCampo);
        } catch (NumberFormatException e) {

            epiVistos.setError(getString(R.string.AddEpiVistosFSErr));
            epiVistos.requestFocus();
            Erros = true;
        }

        //-------------------------------------------Verificação do numero de episodios-------------------------------------------
        textoCampo = num.getText().toString();

        try {
            epi = Integer.parseInt(textoCampo);
        } catch (NumberFormatException e) {

            num.setError(getString(R.string.AddNemFSErr));
            num.requestFocus();
            Erros = true;
        }

        //-------------------------------------------Verificar se o numero de episodios vistos não é maior do que o numero de episodios-------------------------------------------

        if (epiVisto > epi) {

            num.setError(getString(R.string.num_epi_epiVistos));
            num.requestFocus();
            Erros = true;
        } else if (epi == epiVisto && (epi != 0 && epi != -1)) {

            spins.setSelection(3);
        }

        //-------------------------------------------Se não existitem erros fechar a activity-------------------------------------------
        if (!Erros) {

            EditarBD();
            finish();
            Toast.makeText(this, "Data successfully saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void EditarBD() {

        BdFsOpenHelper openHelper = new BdFsOpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTable_Filmes_series tabelaFS = new BdTable_Filmes_series(db);

        fs.setNome(nome.getText().toString());
        int form = 0;

        if (RadioF.isChecked()) {

            form = 1;
        }

        fs.setFormato(form);
        fs.setnEpisodios(Integer.parseInt(num.getText().toString()));
        fs.setnEpiVistos(Integer.parseInt(epiVistos.getText().toString()));
        fs.setData(data.getText().toString());
        fs.setStatus(spins.getSelectedItem().toString());

        tabelaFS.update(fs.getContentValues(), BdTable_Filmes_series._ID + "=?", new String[]{String.valueOf(fs.getID())});
    }

    public void delete(View view) {

        //-------------------------------------------Declaração de variaveis-------------------------------------------
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //-------------------------------------------Construção do AlertDialog-------------------------------------------
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.Are_you_sure));
        builder.setMessage(getString(R.string.deleteMovie));

        builder.setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                BdFsOpenHelper openHelper = new BdFsOpenHelper(EditarFSActivity.this);
                SQLiteDatabase db = openHelper.getWritableDatabase();

                BdTable_Filmes_series tabelaFS = new BdTable_Filmes_series(db);

                tabelaFS.delete(BdTable_Filmes_series._ID + "=?", new String[]{String.valueOf(fs.getID())});

                finish();
                Toast.makeText(EditarFSActivity.this, "Entry deleted", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
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
    public Loader onCreateLoader(int id, @Nullable Bundle args) {

        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, FilmesContentProvider.ENDERECO_PESSOAS, BdTable_Pessoas.TODAS_COLUNAS, null, null, BdTable_Pessoas.CAMPO_NOME
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
     * in either {@link //CursorAdapter#FLAG_AUTO_REQUERY}
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

    public void showMenu(View view) {

        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_gerir_fs);

        if (flag) {

            popup.getMenu().getItem(0).setVisible(false);
            popup.getMenu().getItem(1).setVisible(true);
        } else {

            popup.getMenu().getItem(0).setVisible(true);
            popup.getMenu().getItem(1).setVisible(false);
        }

        popup.show();
    }

    /**
     * This method will be invoked when a menu item is clicked if the item
     * itself did not already handle the event.
     *
     * @param item the menu item that was clicked
     * @return {@code true} if the event was handled, {@code false}
     * otherwise
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_editar_FS:

                nome.setEnabled(true);
                num.setEnabled(true);
                epiVistos.setEnabled(true);
                data.setEnabled(true);
                spins.setEnabled(true);
                RadioF.setEnabled(true);
                RadioS.setEnabled(true);
                flag = true;
                showMenu(findViewById(R.id.menuFS));
                return true;
            case R.id.action_guardar_FS:

                Save();
                flag = false;
                showMenu(findViewById(R.id.menuFS));
                return true;
            case R.id.action_apagar_FS:

                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_Adicionar_P_FS:

                Toast.makeText(this, "Add a person", Toast.LENGTH_SHORT).show();
                return true;
            default:

                return false;
        }
    }
}
