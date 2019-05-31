package nil.filmesseries;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class EditPeopleActivity extends AppCompatActivity {

    //Declaração de objetos e variaveis
    private String TAG = "EditPeopleActivity";

    private TextView nome;
    private TextView funcao;
    private TextView data;
    private Button button;
    private Pessoas P = new Pessoas();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_people);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        nome = findViewById(R.id.editTextEditarNomeP);
        funcao = findViewById(R.id.editTextEditarFunçãoP);
        data = findViewById(R.id.editTextEditarDataP);
        button = findViewById(R.id.butãoEditar_Guardar);

        nome.setEnabled(false);
        funcao.setEnabled(false);
        data.setEnabled(false);

        Intent intent = getIntent();

        P = intent.getParcelableExtra("P");

        nome.setText(P.getNome());
        funcao.setText(P.getFuncao());
        data.setText(P.getDataNascimento());
    }

    public void Save(View view) {

        if (button.getText().toString().equals(getString(R.string.butãoSave))) {

            //-------------------------------------------Declaração de variaveis-------------------------------------------
            String textoCampo;
            boolean Erros = false;
            Calendar cal = Calendar.getInstance();

            //-------------------------------------------Verificação da data-------------------------------------------
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

            P.setDataNascimento(textoCampo);

            //-------------------------------------------Verificação da função-------------------------------------------
            textoCampo = funcao.getText().toString();

            if (textoCampo.isEmpty()) {

                funcao.setError(getString(R.string.add_job));
                funcao.requestFocus();
                Erros = true;
            }

            P.setFuncao(textoCampo);

            //-------------------------------------------Verificação do nome-------------------------------------------
            textoCampo = nome.getText().toString();

            if (textoCampo.isEmpty()) {

                nome.setError("Add a name");
                nome.requestFocus();
                Erros = true;
            }

            P.setNome(textoCampo);

            //-------------------------------------------Se não existitem erros fechar a activity-------------------------------------------
            if (!Erros) {

                EditarBD();
                Toast.makeText(this, "Data successfully saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {

            nome.setEnabled(true);
            funcao.setEnabled(true);
            data.setEnabled(true);
            button.setText(R.string.butãoSave);
        }
    }

    private void EditarBD() {

        BdFsOpenHelper openHelper = new BdFsOpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        BdTable_Pessoas tabelaP = new BdTable_Pessoas(db);

        P.setNome(nome.getText().toString());
        P.setFuncao(funcao.getText().toString());
        P.setDataNascimento(data.getText().toString());

        tabelaP.update(P.getContentValues(), BdTable_Pessoas._ID + "=?", new String[]{String.valueOf(P.getID())});
    }

    public void Cancel(View view) {

        finish();
    }

    public void Delete(View view) {

        //-------------------------------------------Declaração de variaveis-------------------------------------------
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //-------------------------------------------Construção do AlertDialog-------------------------------------------
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.Are_you_sure));
        builder.setMessage(getString(R.string.deletePessoa));

        builder.setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                BdFsOpenHelper openHelper = new BdFsOpenHelper(context);
                SQLiteDatabase db = openHelper.getWritableDatabase();

                BdTable_Pessoas tabelaP = new BdTable_Pessoas(db);

                tabelaP.delete(BdTable_Pessoas._ID + "=?", new String[]{String.valueOf(P.getID())});

                Toast.makeText(context, "Entry deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }
}
