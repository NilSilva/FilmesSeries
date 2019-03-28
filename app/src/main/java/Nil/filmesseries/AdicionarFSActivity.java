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
        RadioGroup RG = findViewById(R.id.radioGroupAddF);
        int checked = RG.getCheckedRadioButtonId();
        TextView menErro = findViewById(R.id.textViewAddFormErr);

        if(checked == -1){
            menErro.setText(getString(R.string.menErr));
            flag = 1;
        }
        EditText editTextCampo = findViewById(R.id.editTextAddNome);
        String textoCampo = editTextCampo.getText().toString();

        if(textoCampo.trim().length() == 0){

            editTextCampo.setError(getString(R.string.menErr));
            editTextCampo.requestFocus();
            flag = 1;
        }

        editTextCampo = findViewById(R.id.editTextAddData);
        textoCampo = editTextCampo.getText().toString();

        if(textoCampo.trim().length() == 0){

            editTextCampo.setError(getString(R.string.menErr));
            editTextCampo.requestFocus();

            flag = 1;
        }

        editTextCampo = findViewById(R.id.editTextAddNum);
        textoCampo = editTextCampo.getText().toString();

        if(textoCampo.trim().length() == 0){

            editTextCampo.setError(getString(R.string.menErr));
            editTextCampo.requestFocus();

            flag = 1;
        }

        editTextCampo = findViewById(R.id.editTextAddEpiVistos);
        textoCampo = editTextCampo.getText().toString();

        if(textoCampo.trim().length() == 0){

            editTextCampo.setError(getString(R.string.menErr));
            editTextCampo.requestFocus();

            flag = 1;
        }

        Spinner spin = findViewById(R.id.spinnerAddF);
        String spinSel = spin.getSelectedItem().toString();

        if(spinSel.equals(getString(R.string.SelEstado))){
            Toast.makeText(this, getString(R.string.SelEstado), Toast.LENGTH_SHORT).show();
            flag = 1;
        }

        if(flag == 0){
            finish();
            Toast.makeText(this, getString(R.string.Sucesso), Toast.LENGTH_SHORT).show();
        }
    }
}
