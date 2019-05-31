package nil.filmesseries;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class AddPeopleActivity extends AppCompatActivity {

    //Declaração de objetos e variaveis
    private String TAG = "AddPeopleActivity";

    private EditText nome;
    private EditText funcao;
    private EditText data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //inicialização de objetos
        nome = findViewById(R.id.editTextAdicionarNomeP);
        funcao = findViewById(R.id.editTextAdicionarFunçãoP);
        data = findViewById(R.id.editTextAdicionarDataP);
    }

    public void Save(View view) {

        //-------------------------------------------Declaração de variaveis-------------------------------------------

        Pessoas p = new Pessoas();
        EditText editTextCampo;
        String textoCampo;
        boolean Erros = false;
        Calendar cal = Calendar.getInstance();

        //-------------------------------------------Verificação da data
        textoCampo = data.getText().toString();

        try {

            cal.setLenient(false);

            //da exceção se o ano, o mes e/ou o dia não forem numeros
            cal.set(Integer.parseInt(textoCampo.substring(6)), (Integer.parseInt(textoCampo.substring(3, 5))) - 1, Integer.parseInt(textoCampo.substring(0, 2)));

            //ve se ano é valido(1º filme é de 1895, logo 1800 parece um bom valor)
            //da exceção se a data for invalida
            if (cal.get(Calendar.YEAR) < 1800) {

                data.setError(getString(R.string.data_invalida));
                data.requestFocus();
                Erros = true;
            }
        } catch (Exception e) {

            data.setError(getString(R.string.data_invalida));
            data.requestFocus();
            Erros = true;
        }

        p.setDataNascimento(textoCampo);

        //-------------------------------------------Verificação da função-------------------------------------------
        textoCampo = funcao.getText().toString();

        if (textoCampo.isEmpty()) {

            funcao.setError(getString(R.string.add_job));
            funcao.requestFocus();
            Erros = true;
        }

        p.setFuncao(textoCampo);

        //-------------------------------------------Verificação do nome-------------------------------------------
        textoCampo = nome.getText().toString();

        if (textoCampo.isEmpty()) {

            nome.setError("Add a name");
            nome.requestFocus();
            Erros = true;
        }

        p.setNome(textoCampo);

        //-------------------------------------------Se não existitem erros fechar a activity-------------------------------------------
        if (!Erros) {

            inserirBD(p);
            Toast.makeText(this, "Data successfully saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void inserirBD(Pessoas p) {

        BdFsOpenHelper openHelper = new BdFsOpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        BdTable_Pessoas tabelaP = new BdTable_Pessoas(db);

        tabelaP.insert(p.getContentValues());
    }

    public void Cancel(View view) {

        finish();
    }
}
