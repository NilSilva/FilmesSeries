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

    public void Cancel(View view) {

        finish();
    }

    public void Save(View view) {

        //-------------------------------------------Declaração de variaveis-------------------------------------------
        EditText editTextCampo;

        String textoCampo;

        TextView menErro;

        int DetecaoDeErros = 0;
        int checked;

        RadioGroup RG;

        //-------------------------------------------Detetar se um dos radiobuttons esta selecionado-------------------------------------------
        RG = findViewById(R.id.radioGroupEditarFS);
        checked = RG.getCheckedRadioButtonId();
        menErro = findViewById(R.id.textViewEditarErroFormato);

        if (checked == -1) {

            menErro.setText(getString(R.string.AddFormatFSErr));
            menErro.setError("");
            DetecaoDeErros = 1;
        }

        //-------------------------------------------Detetar se o item selecionado no spinner é o primeiro-------------------------------------------
        Spinner spin = findViewById(R.id.spinnerEditarEstadoFS);
        TextView errorText = (TextView) spin.getSelectedView();

        if (spin.getSelectedItemPosition() == 0) {

            errorText.setError("");
            errorText.setText(getString(R.string.AddErrStatusFS));
            DetecaoDeErros = 1;
        }

        //-------------------------------------------Verificação da data-------------------------------------------
        editTextCampo = findViewById(R.id.editTextEditarDataFS);
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

        //ve se ano é valido(1º filme é de 1895, logo 1890 parece um bom valor)
        if (ano < 1890 && erroData == 0) {

            editTextCampo.setError(getString(R.string.data_invalida));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
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
            DetecaoDeErros = 1;
        }

        //-------------------------------------------Verificação do numero de episodios-------------------------------------------
        EditText editTextNum = findViewById(R.id.editTextEditarNumFS);
        textoCampo = editTextNum.getText().toString();

        try {
            epi = Integer.parseInt(textoCampo);
        } catch (NumberFormatException e) {

            editTextNum.setError(getString(R.string.AddNemFSErr));
            editTextNum.requestFocus();
            DetecaoDeErros = 1;
        }

        //-------------------------------------------Verificar se o numero de episodios vistos não é maior do que o numero de episodios-------------------------------------------

        if (epiVistos > epi) {

            editTextCampo.setError(getString(R.string.num_epi_epiVistos));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
        } else if (epi == epiVistos && (epi != 0 && epi != -1)) {

            spin.setSelection(3);
        }

        //-------------------------------------------Verificação do nome-------------------------------------------
        editTextCampo = findViewById(R.id.editTextEditarNomeFS);
        textoCampo = editTextCampo.getText().toString();

        if (textoCampo.isEmpty()) {

            editTextCampo.setError(getString(R.string.AddNameFSErr));
            editTextCampo.requestFocus();
            DetecaoDeErros = 1;
        }

        //-------------------------------------------Se não existitem erros fechar a activity-------------------------------------------
        if (DetecaoDeErros == 0) {

            finish();
            Toast.makeText(this, "Data successfully saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void ClearError(View view) {

        TextView menErr = findViewById(R.id.textViewEditarErroFormato);
        menErr.setText("");
        menErr.setError(null);
    }
}
