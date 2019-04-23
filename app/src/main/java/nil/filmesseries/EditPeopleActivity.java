package nil.filmesseries;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditPeopleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_people);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void Save(View view) {

        /**
         * Declaração de variaveis
         */
        EditText editTextCampo;
        String textoCampo;

        int DetecaoDeErros = 0;

        /**
         * Verificação da data
         */
        editTextCampo = findViewById(R.id.editTextDate);
        textoCampo = editTextCampo.getText().toString();

        if(textoCampo.isEmpty()){

            editTextCampo.setError(getString(R.string.birth_date_error));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
        }

        /**
         * Verificação da função
         */
        editTextCampo = findViewById(R.id.editTextJob);
        textoCampo = editTextCampo.getText().toString();

        if(textoCampo.isEmpty()){

            editTextCampo.setError(getString(R.string.add_job));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
        }

        /**
         * Verificação do nome
         */
        editTextCampo = findViewById(R.id.editTextNome);
        textoCampo = editTextCampo.getText().toString();

        if(textoCampo.isEmpty()){

            editTextCampo.setError("Add a name");
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
}
