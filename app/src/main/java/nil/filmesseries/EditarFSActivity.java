package nil.filmesseries;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditarFSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_fs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void AddCancelarF(View view) {

        finish();
    }

    public void AddGuardarF(View view) {

        /**
         * Declaração de variaveis
         */
        EditText editTextCampo;
        String textoCampo;
        TextView menErro;

        int DetecaoDeErros = 0;
        int checked;

        RadioGroup RG;

        /**
         * Detetar se um dos radiobuttons esta selecionado
         */
        RG = findViewById(R.id.radioGroupAddFS);
        checked = RG.getCheckedRadioButtonId();
        menErro = findViewById(R.id.textViewAddFormErrFS);

        if(checked == -1){

            menErro.setText(getString(R.string.AddFormatFSErr));
            menErro.setError("");
            DetecaoDeErros = 1;
        }

        /**
         * Detetar se o item selecionado no spinner é o primeiro
         */
        Spinner spin = findViewById(R.id.spinnerAddStatusF);
        TextView errorText = (TextView)spin.getSelectedView();

        if(spin.getSelectedItemPosition() == 0){
            errorText.setError("");
            errorText.setText(getString(R.string.AddErrStatusFS));
            DetecaoDeErros = 1;
        }

        /**
         * Verificação da data
         */
        editTextCampo = findViewById(R.id.editTextAddDataFS);
        textoCampo = editTextCampo.getText().toString();

        if(textoCampo.isEmpty()){

            editTextCampo.setError(getString(R.string.AddDataFSErr));
            editTextCampo.requestFocus();

            DetecaoDeErros = 1;
        }

        /**
         * Verificação de episodios vistos
         */
        editTextCampo = findViewById(R.id.editTextAddEpiVistosFS);
        textoCampo = editTextCampo.getText().toString();

        if(textoCampo.isEmpty()){

            editTextCampo.setError(getString(R.string.AddEpiVistosFSErr));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
        }

        /**
         * Verificação do numero de episodios
         */
        editTextCampo = findViewById(R.id.editTextAddNumFS);
        textoCampo = editTextCampo.getText().toString();

        if(textoCampo.isEmpty()){

            editTextCampo.setError(getString(R.string.AddNemFSErr));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
        }

        /**
         * Verificação do nome
         */
        editTextCampo = findViewById(R.id.editTextAddNomeGen);
        textoCampo = editTextCampo.getText().toString();

        if(textoCampo.isEmpty()){

            editTextCampo.setError(getString(R.string.AddNameFSErr));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
        }

        /**
         * Se não existitem erros fechar a activity
         */
        if(DetecaoDeErros == 0){
            finish();
            Toast.makeText(this, getString(R.string.Sucesso), Toast.LENGTH_SHORT).show();
        }
    }

    public void ClearError(View view) {
        TextView menErr = findViewById(R.id.textViewAddFormErrFS);
        menErr.setText("");
        menErr.setError(null);
    }
}
