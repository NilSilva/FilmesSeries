package nil.filmesseries;

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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class AdicionarFSActivity extends AppCompatActivity {

    EditText nome;
    EditText num;
    EditText epiVistos;
    EditText data;
    Button button;
    RadioGroup RadioG;
    RadioButton RadioF;
    RadioButton RadioS;
    Spinner spins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_fs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nome = findViewById(R.id.editTextAdicionarNomeFS);
        num = findViewById(R.id.editTextAdicionarNumFS);
        epiVistos = findViewById(R.id.editTextAdicionarEpiVistosFS);
        data = findViewById(R.id.editTextAdicionarDataFS);
        button = findViewById(R.id.butãoAdicionarSaveFS);
        RadioG = findViewById(R.id.radioGroupAdicionarFS);
        spins = findViewById(R.id.spinnerAdicionarEstadoFS);
        RadioF = findViewById(R.id.radioButtonAdicionarFSFilme);
        RadioS = findViewById(R.id.radioButtonAdicionarFSSerie);

        nome.addTextChangedListener(Campos);
        num.addTextChangedListener(Campos);
        epiVistos.addTextChangedListener(Campos);
        data.addTextChangedListener(Campos);
        RadioG.setOnCheckedChangeListener(CampoRadioG);
        spins.setOnItemSelectedListener(CampoSpinner);
    }

    private AdapterView.OnItemSelectedListener CampoSpinner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

            CamposPreenchidos();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }

    };

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

    private OnCheckedChangeListener CampoRadioG = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {CamposPreenchidos();}
    };

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

        int form = 0;

        filmesSeries FS = new filmesSeries();

        Log.d("AFSA - isSelected", "" + RadioF.isChecked());

        if(RadioF.isChecked()){
            form = 1;
            Log.d("AFSA", "Entrou no if....");
        }

        FS.setNome(nome.getText().toString().trim());
        Log.d("AFSA - nome", nome.getText().toString().trim());
        FS.setFormato(form);
        Log.d("AFSA - formato", form + "");
        FS.setStatus(spins.getSelectedItem().toString());
        Log.d("AFSA - estado", spins.getSelectedItem().toString());

        //-------------------------------------------Declaração de variaveis-------------------------------------------
        String textoCampo;
        boolean Erros = false;
        Calendar cal = Calendar.getInstance();

        //-------------------------------------------Verificação da data-------------------------------------------
        textoCampo = data.getText().toString();

        try {

            cal.setLenient(false);

            //da exceção se o ano, o mes e/ou o dia não forem numeros
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


        FS.setData(textoCampo);
        Log.d("AFSA - data", textoCampo);

        //-------------------------------------------Verificação de episodios vistos-------------------------------------------
        textoCampo = epiVistos.getText().toString();

        int epi = -1;
        int epiV = -1;

        try {
            epiV = Integer.parseInt(textoCampo);
        } catch (NumberFormatException e) {

            epiVistos.setError(getString(R.string.AddEpiVistosFSErr));
            epiVistos.requestFocus();
            Erros = true;
        }


        FS.setnEpiVistos(epiV);
        Log.d("AFSA - epi vistos", epiV + "");

        //-------------------------------------------Verificação do numero de episodios-------------------------------------------
        textoCampo = num.getText().toString();

        try {
            epi = Integer.parseInt(textoCampo);
        } catch (NumberFormatException e) {

            num.setError(getString(R.string.AddNemFSErr));
            num.requestFocus();
            Erros = true;
        }


        FS.setnEpisodios(epi);
        Log.d("AFSA - episodios", epi + "");

        //-------------------------------------------Verificar se o numero de episodios vistos não é maior do que o numero de episodios-------------------------------------------

        if (epiV > epi) {

            epiVistos.setError(getString(R.string.num_epi_epiVistos));
            epiVistos.requestFocus();
            Erros = true;
        } else if (epi == epiV && (epi != 0 && epi != -1)) {

            spins.setSelection(3);
        }

        //-------------------------------------------Se não existitem erros fechar a activity-------------------------------------------
        if (!Erros) {

            finish();
            inserirBD(FS);
            Toast.makeText(this, "Data successfully saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void inserirBD(filmesSeries fs) {

        BdFsOpenHelper openHelper = new BdFsOpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTable_Filmes_series tabelaFS = new BdTable_Filmes_series(db);
        tabelaFS.insert(fs.getContentValues());
    }

    public void ClearError(View view) {

        TextView menErr = findViewById(R.id.textViewAdicionarErroFormato);
        menErr.setText("");
        menErr.setError(null);
    }
}
