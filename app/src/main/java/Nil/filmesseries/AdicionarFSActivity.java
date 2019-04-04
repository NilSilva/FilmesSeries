package Nil.filmesseries;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AdicionarFSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_fs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void AddCancelarF(View view) {

        finish();
    }

    public void AddGuardarF(View view) {

        int flag = 0;
        RadioGroup RG = findViewById(R.id.radioGroupAddFS);
        int checked = RG.getCheckedRadioButtonId();
        TextView menErro = findViewById(R.id.textViewAddFormErrFS);

        //Verificar se esta um formato selecionado
        if(checked == -1){

            menErro.setText(getString(R.string.AddFormatFSErr));
            menErro.setError("");
            flag = 1;
        }

        EditText editTextCampo = findViewById(R.id.editTextAddNomeFS);
        String textoCampo = editTextCampo.getText().toString();

        //Verificar se o campo nome não esta vazio
        if(textoCampo.isEmpty()){

            editTextCampo.setError(getString(R.string.AddNameFSErr));
            editTextCampo.requestFocus();
            flag = 1;
        }

        editTextCampo = findViewById(R.id.editTextAddDataFS);
        textoCampo = editTextCampo.getText().toString();

        //Verificar se o campo data não esta vazio
        if(textoCampo.isEmpty()){

            editTextCampo.setError(getString(R.string.AddDataFSErr));
            editTextCampo.requestFocus();

            flag = 1;
        }

        editTextCampo = findViewById(R.id.editTextAddNumFS);
        textoCampo = editTextCampo.getText().toString();

        //Verificar se o campo nome não esta vazio
        if(textoCampo.isEmpty()){

            editTextCampo.setError(getString(R.string.AddNemFSErr));
            editTextCampo.requestFocus();
            flag = 1;
        }

        editTextCampo = findViewById(R.id.editTextAddEpiVistosFS);
        textoCampo = editTextCampo.getText().toString();

        if(textoCampo.isEmpty()){

            editTextCampo.setError(getString(R.string.AddEpiVistosFSErr));
            editTextCampo.requestFocus();
            flag = 1;
        }

        Spinner spin = findViewById(R.id.spinnerAddStatusF);
        TextView errorText = (TextView)spin.getSelectedView();

        if(spin.getSelectedItemPosition() == 0){
            errorText.setError("");
            errorText.setText(getString(R.string.AddErrStatusFS));
            flag = 1;
        }

        if(flag == 0){
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
