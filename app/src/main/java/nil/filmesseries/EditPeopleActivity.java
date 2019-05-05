package nil.filmesseries;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

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

        //-------------------------------------------Declaração de variaveis-------------------------------------------
        EditText editTextCampo;

        String textoCampo;

        boolean Erros = false;

        Calendar cal = Calendar.getInstance();

        //-------------------------------------------Verificação da data-------------------------------------------
        editTextCampo = findViewById(R.id.editTextEditarDataP);
        textoCampo = editTextCampo.getText().toString();

        try {

            cal.setLenient(false);

            //da exceção se o ano, o mes e/ou o dia não forem numeros
            cal.set(Integer.parseInt(textoCampo.substring(6)), (Integer.parseInt(textoCampo.substring(3, 5))) - 1, Integer.parseInt(textoCampo.substring(0, 2)));

            //ve se ano é valido(1º filme é de 1895, logo 1800 parece um bom valor)
            //da exceção se a data for invalida
            if (cal.get(Calendar.YEAR) < 1800) {

                editTextCampo.setError(getString(R.string.data_invalida));
                editTextCampo.requestFocus();
                Erros = true;
            }
        } catch (Exception e) {

            editTextCampo.setError(getString(R.string.data_invalida));
            editTextCampo.requestFocus();
            Erros = true;
        }

        //-------------------------------------------Verificação da função-------------------------------------------
        editTextCampo = findViewById(R.id.editTextEditarFunçãoP);
        textoCampo = editTextCampo.getText().toString();

        if (textoCampo.isEmpty()) {

            editTextCampo.setError(getString(R.string.add_job));
            editTextCampo.requestFocus();
            Erros = true;
        }

        //-------------------------------------------Verificação do nome-------------------------------------------
        editTextCampo = findViewById(R.id.editTextEditarNomeP);
        textoCampo = editTextCampo.getText().toString();

        if (textoCampo.isEmpty()) {

            editTextCampo.setError("Add a name");
            editTextCampo.requestFocus();
            Erros = true;
        }

        //-------------------------------------------Se não existitem erros fechar a activity-------------------------------------------
        if (!Erros) {

            finish();
            Toast.makeText(this, "Data successfully saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void Cancel(View view) {
        finish();
    }
}
