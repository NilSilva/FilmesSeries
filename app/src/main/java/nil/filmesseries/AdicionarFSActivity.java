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

import java.util.Calendar;

public class AdicionarFSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_fs);
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

        boolean Erros = false;
        int checked;

        RadioGroup RG;

        int dia = 0;
        int mes = 0;
        int ano = 0;
        Calendar cal = Calendar.getInstance();

        //-------------------------------------------Detetar se um dos radiobuttons esta selecionado-------------------------------------------
        RG = findViewById(R.id.radioGroupAdicionarFS);
        checked = RG.getCheckedRadioButtonId();
        menErro = findViewById(R.id.textViewAdicionarErroFormato);

        if (checked == -1) {

            menErro.setText(getString(R.string.AddFormatFSErr));
            menErro.setError("");
            Erros = true;
        }

        //-------------------------------------------Detetar se o item selecionado no spinner é o primeiro-------------------------------------------
        Spinner spin = findViewById(R.id.spinnerAdicionarEstadoFS);
        TextView errorText = (TextView) spin.getSelectedView();

        if (spin.getSelectedItemPosition() == 0) {

            errorText.setError("");
            errorText.setText(getString(R.string.AddErrStatusFS));
            Erros = true;
        }

        //-------------------------------------------Verificação da data-------------------------------------------
        editTextCampo = findViewById(R.id.editTextAdicionarDataFS);
        textoCampo = editTextCampo.getText().toString();

        try {

            dia = Integer.parseInt(textoCampo.substring(0, 2));
            mes = Integer.parseInt(textoCampo.substring(3, 5));
            ano = Integer.parseInt(textoCampo.substring(6));

            cal.setLenient(false);

            cal.set(ano, mes - 1, dia);

            cal.get(Calendar.YEAR);
        } catch (Exception e){

            editTextCampo.setError(getString(R.string.data_invalida));
            editTextCampo.requestFocus();
            Erros = true;
        }

        //-------------------------------------------Verificação de episodios vistos-------------------------------------------
        editTextCampo = findViewById(R.id.editTextAdicionarEpiVistosFS);
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
        EditText editTextNum = findViewById(R.id.editTextAdicionarNumFS);
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
        editTextCampo = findViewById(R.id.editTextAdicionarNomeFS);
        textoCampo = editTextCampo.getText().toString();

        if (textoCampo.isEmpty()) {

            editTextCampo.setError(getString(R.string.AddNameFSErr));
            editTextCampo.requestFocus();
            Erros = true;
        }

        //-------------------------------------------Se não existitem erros fechar a activity-------------------------------------------
        if (!Erros) {

            finish();
            Toast.makeText(this, "Data successfully saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void ClearError(View view) {

        TextView menErr = findViewById(R.id.textViewAdicionarErroFormato);
        menErr.setText("");
        menErr.setError(null);
    }
}
