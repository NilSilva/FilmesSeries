package nil.filmesseries;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

        int DetecaoDeErros = 0;

        //-------------------------------------------Verificação da data-------------------------------------------
        editTextCampo = findViewById(R.id.editTextEditarDataP);
        textoCampo = editTextCampo.getText().toString();

        int dia = 0;
        int mes = 0;
        int ano = 0;
        int[] diasMax = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int anobis = 0;
        int erroData = 0;

        //Separar o string em dia, mes e ano se possivel
        try {

            dia = Integer.parseInt(textoCampo.substring(0, 2));
            mes = Integer.parseInt(textoCampo.substring(3, 5));
            ano = Integer.parseInt(textoCampo.substring(6));
        } catch (NumberFormatException e) {
            //se não forem numeros

            editTextCampo.setError(getString(R.string.date_format_error));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
            erroData = 1;
        } catch (StringIndexOutOfBoundsException e) {
            //se estiver vazio

            editTextCampo.setError(getString(R.string.birth_date_error));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
            erroData = 1;
        }

        //ve se o string é maior do que devia
        if (textoCampo.length() > 10 && erroData == 0) {

            editTextCampo.setError(getString(R.string.date_format_error));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
            erroData = 1;
        }

        //ve se o mes é valido
        if (mes <= 0 || mes > 12 && erroData == 0) {

            editTextCampo.setError(getString(R.string.data_invalida));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
            erroData = 1;
        }

        if (mes <= 0 || mes > 12) {

            mes = 1;
        }

        //ve se o ano é bissexto
        if (mes == 2 && ((ano % 400 == 0) || ano % 4 == 0 && ano % 100 != 0)) {

            anobis = 1;
        }

        //ve se o dia é valido
        if (dia <= 0 || dia > (diasMax[mes - 1] + anobis) && erroData == 0) {

            editTextCampo.setError(getString(R.string.data_invalida));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
            erroData = 1;
        }

        //ve se ano é valido(1º filme é de 1895, logo 1800 parece um bom valor)
        if (ano < 1800 && erroData == 0) {

            editTextCampo.setError(getString(R.string.data_invalida));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
        }

        //-------------------------------------------Verificação da função-------------------------------------------
        editTextCampo = findViewById(R.id.editTextEditarFunçãoP);
        textoCampo = editTextCampo.getText().toString();

        if (textoCampo.isEmpty()) {

            editTextCampo.setError(getString(R.string.add_job));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
        }

        //-------------------------------------------Verificação do nome-------------------------------------------
        editTextCampo = findViewById(R.id.editTextEditarNomeP);
        textoCampo = editTextCampo.getText().toString();

        if (textoCampo.isEmpty()) {

            editTextCampo.setError("Add a name");
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
        }

        //-------------------------------------------Se não existitem erros fechar a activity-------------------------------------------
        if (DetecaoDeErros == 0) {

            finish();
            Toast.makeText(this, getString(R.string.Sucesso), Toast.LENGTH_SHORT).show();
        }
    }

    public void Cancel(View view) {
        finish();
    }
}
