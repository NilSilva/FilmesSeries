package nil.filmesseries;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class EditarFSActivity extends AppCompatActivity {

    //Declaração de objetos
    EditText nome;
    EditText num;
    EditText epiVistos;
    EditText data;
    Button button;
    RadioGroup RadioG;
    RadioButton RadioF;
    RadioButton RadioS;
    Spinner spins;
    filmesSeries fs;

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

        if(fs.getFormato() == 0){
            RadioS.setChecked(true);
        }else{
            RadioF.setChecked(true);
        }

        nome.setText(fs.getNome());
        num.setText(fs.getnEpisodios() + "");
        epiVistos.setText(fs.getnEpiVistos() + "");
        data.setText(fs.getData());
        Log.d("EditarActivity", "spin count " + spins.getCount());
        for(int i = 1;i < spins.getCount();i++){
            Log.d("EditarActivity", "item na posição " + spins.getItemAtPosition(i) + " - " + spins.getItemAtPosition(i).toString());
            if(spins.getItemAtPosition(i).toString().equals(fs.getStatus())){
                spins.setSelection(i);
                break;
            }
        }
    }

    //é ativado quando o item selecionado no spinner muda
    private AdapterView.OnItemSelectedListener CampoSpinner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

            CamposPreenchidos();
            if(spins.getSelectedItemPosition() == 0){
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

        if(radioG != -1 && spins.getSelectedItemPosition() != 0){
            button.setEnabled(!nomeInput.isEmpty() && !numInput.isEmpty() && !epiVistosInput.isEmpty() && !dataInput.isEmpty());
        }
    }

    public void Cancel(View view) {

        finish();
    }

    public void Save(View view) {

        if(button.getText().toString().equals(getString(R.string.butãoSave))) {

            //-------------------------------------------Declaração de variaveis-------------------------------------------
            EditText editTextCampo;
            String textoCampo;
            TextView menErro;
            boolean Erros = false;
            int checked;
            RadioGroup RG;
            Calendar cal = Calendar.getInstance();

            //-------------------------------------------Detetar se um dos radiobuttons esta selecionado-------------------------------------------
            RG = findViewById(R.id.radioGroupEditarFS);
            checked = RG.getCheckedRadioButtonId();
            menErro = findViewById(R.id.textViewEditarErroFormato);

            if (checked == -1) {

                menErro.setText(getString(R.string.AddFormatFSErr));
                menErro.setError("");
                Erros = true;
            }

            //-------------------------------------------Detetar se o item selecionado no spinner é o primeiro-------------------------------------------
            Spinner spin = findViewById(R.id.spinnerEditarEstadoFS);
            TextView errorText = (TextView) spin.getSelectedView();

            if (spin.getSelectedItemPosition() == 0) {

                errorText.setError("");
                errorText.setText(getString(R.string.AddErrStatusFS));
                Erros = true;
            }

            //-------------------------------------------Verificação da data-------------------------------------------
            editTextCampo = findViewById(R.id.editTextEditarDataFS);
            textoCampo = editTextCampo.getText().toString();

            try {

                cal.setLenient(false);

                //da exceção se o ano, o mes e/ou o dia não forem validos
                cal.set(Integer.parseInt(textoCampo.substring(6)), (Integer.parseInt(textoCampo.substring(3, 5))) - 1, Integer.parseInt(textoCampo.substring(0, 2)));

                //ve se ano é valido(1º filme é de 1895, logo 1890 parece um bom valor)
                //da exceção se a data for invalida
                if (cal.get(Calendar.YEAR) < 1890) {

                    editTextCampo.setError(getString(R.string.data_invalida));
                    editTextCampo.requestFocus();
                    Erros = true;
                }
            } catch (Exception e) {

                editTextCampo.setError(getString(R.string.data_invalida));
                editTextCampo.requestFocus();
                Erros = true;
            }

            //-------------------------------------------Verificação de episodios vistos-------------------------------------------
            editTextCampo = findViewById(R.id.editTextEditarEpiVistosFS);
            textoCampo = editTextCampo.getText().toString();

            int epi = -1;
            int epiVistos = -1;

            try {
                epiVistos = Integer.parseInt(textoCampo);
            } catch (NumberFormatException e) {

                editTextCampo.setError(getString(R.string.AddEpiVistosFSErr));
                editTextCampo.requestFocus();
                Erros = true;
            }

            //-------------------------------------------Verificação do numero de episodios-------------------------------------------
            EditText editTextNum = findViewById(R.id.editTextEditarNumFS);
            textoCampo = editTextNum.getText().toString();

            try {
                epi = Integer.parseInt(textoCampo);
            } catch (NumberFormatException e) {

                editTextNum.setError(getString(R.string.AddNemFSErr));
                editTextNum.requestFocus();
                Erros = true;
            }

            //-------------------------------------------Verificar se o numero de episodios vistos não é maior do que o numero de episodios-------------------------------------------

            if (epiVistos > epi) {

                editTextCampo.setError(getString(R.string.num_epi_epiVistos));
                editTextCampo.requestFocus();
                Erros = true;
            } else if (epi == epiVistos && (epi != 0 && epi != -1)) {

                spin.setSelection(3);
            }

            //-------------------------------------------Verificação do nome-------------------------------------------
            editTextCampo = findViewById(R.id.editTextEditarNomeFS);
            textoCampo = editTextCampo.getText().toString();

            if (textoCampo.isEmpty()) {

                editTextCampo.setError(getString(R.string.AddNameFSErr));
                editTextCampo.requestFocus();
                Erros = true;
            }

            //-------------------------------------------Se não existitem erros fechar a activity-------------------------------------------
            if (!Erros) {

                EditarBD();
                finish();
                Toast.makeText(this, "Data successfully saved", Toast.LENGTH_SHORT).show();
            }
        }else{

            nome.setEnabled(true);
            num.setEnabled(true);
            epiVistos.setEnabled(true);
            data.setEnabled(true);
            spins.setEnabled(true);
            RadioF.setEnabled(true);
            RadioS.setEnabled(true);

            button.setText(R.string.butãoSave);
        }
    }

    private void EditarBD() {

        BdFsOpenHelper openHelper = new BdFsOpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTable_Filmes_series tabelaFS = new BdTable_Filmes_series(db);

        fs.setNome(nome.getText().toString());
        int form = 0;

        if(RadioF.isChecked()){
            form = 1;
        }

        fs.setFormato(form);
        fs.setnEpisodios(Integer.parseInt(num.getText().toString()));
        fs.setnEpiVistos(Integer.parseInt(epiVistos.getText().toString()));
        fs.setData(data.getText().toString());
        fs.setStatus(spins.getSelectedItem().toString());

        tabelaFS.update(fs.getContentValues(), BdTable_Filmes_series._ID + "=?", new String[]{String.valueOf(fs.getID())});
    }

    public void ClearError(View view) {

        TextView menErr = findViewById(R.id.textViewEditarErroFormato);
        menErr.setText("");
        menErr.setError(null);
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
}
