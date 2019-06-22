package nil.filmesseries;

import android.content.ContentUris;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AdicionarFSActivity extends AppCompatActivity {

    //Declaração de objetos
    private String TAG = "AdicionarFSActivity";

    private EditText nome;
    private EditText num;
    private EditText epiVistos;
    private EditText data;
    private Button button;
    private RadioGroup RadioG;
    private RadioButton RadioF;
    private RadioButton RadioS;
    private Spinner spins;

    private static final int RESULT_IMAGE = 1;

    private ImageView imageView;
    private byte imageInByte[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_fs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //inicialização dos objetos
        nome = findViewById(R.id.editTextAdicionarNomeFS);
        num = findViewById(R.id.editTextAdicionarNumFS);
        epiVistos = findViewById(R.id.editTextAdicionarEpiVistosFS);
        data = findViewById(R.id.editTextAdicionarDataFS);
        button = findViewById(R.id.butãoAdicionarSaveFS);
        RadioG = findViewById(R.id.radioGroupAdicionarFS);
        spins = findViewById(R.id.spinnerAdicionarEstadoFS);
        RadioF = findViewById(R.id.radioButtonAdicionarFSFilme);
        RadioS = findViewById(R.id.radioButtonAdicionarFSSerie);
        imageView = findViewById(R.id.imageViewAdicionarPoster);

        //controlo do butão para guardar
        //alguma da verificação tambem é feita aqui, nomeadamente ver se os campos estão preenchidos
        nome.addTextChangedListener(Campos);
        num.addTextChangedListener(Campos);
        epiVistos.addTextChangedListener(Campos);
        data.addTextChangedListener(Campos);
        RadioG.setOnCheckedChangeListener(CampoRadioG);
        spins.setOnItemSelectedListener(CampoSpinner);
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
    private OnCheckedChangeListener CampoRadioG = new OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            CamposPreenchidos();
        }
    };

    //ativa o butão guardar se todos os campos estiverem preenchidos
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

    public void Save(View view) {

        //TODO: esconder opções de numero de episodios se for filme

        //-------------------------------------------Declaração de variaveis-------------------------------------------
        String textoCampo;
        boolean Erros = false;
        Calendar cal = Calendar.getInstance();
        int form = 0;
        filmesSeries FS = new filmesSeries();

        //verifica se é filme
        if (RadioF.isChecked()) {

            form = 1;
        }

        FS.setNome(nome.getText().toString().trim());
        FS.setFormato(form);
        FS.setStatus(spins.getSelectedItem().toString());

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

            FS.setImagem(imageInByte);
            Uri uri = getContentResolver().insert(FilmesContentProvider.ENDERECO_FS, FS.getContentValues());
            long id = ContentUris.parseId(uri);
            Log.d(TAG, "id ao inserir: " + id);

            Toast.makeText(this, "Data successfully saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void getImage(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_IMAGE);
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_IMAGE && resultCode == RESULT_OK && data != null){

            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imageInByte = stream.toByteArray();
        }
    }
}
