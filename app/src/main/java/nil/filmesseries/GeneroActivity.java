package nil.filmesseries;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GeneroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genero);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void Add(View view) {

        //-------------------------------------------Declaração de variaveis-------------------------------------------
        EditText editTextCampo = findViewById(R.id.editTextAdicionarG);
        String textoCampo = editTextCampo.getText().toString();

        //ve se foi introduzido um nome
        if (textoCampo.isEmpty()) {

            editTextCampo.setError("Add a name");
            editTextCampo.requestFocus();
        } else {

            editTextCampo.setText("");
            editTextCampo.setError(null);
            editTextCampo.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            Toast.makeText(this, "Genero adicionado", Toast.LENGTH_SHORT).show();
        }
    }

    public void Delete(View view) {

        //-------------------------------------------Declaração de variaveis-------------------------------------------
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final Spinner spin = findViewById(R.id.spinnerApagarG);
        TextView errorText = (TextView) spin.getSelectedView();

        //-------------------------------------------Construção do AlertDialog-------------------------------------------
        if (spin.getSelectedItemPosition() == 0) {

            errorText.setError("");
            errorText.setText(getString(R.string.Spin_erro_genero));
        }else {

            builder.setTitle(getString(R.string.Are_you_sure));
            builder.setMessage("This will delete this person.");
            builder.setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    spin.setSelection(0);
                    Toast.makeText(GeneroActivity.this, "Genero removido", Toast.LENGTH_SHORT).show();
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

    public void Save(View view) {

        //-------------------------------------------Declaração de variaveis-------------------------------------------
        EditText editTextCampo = findViewById(R.id.editTextEditarG);
        String textoCampo = editTextCampo.getText().toString();

        //ve se foi introduzido um nome
        if (textoCampo.isEmpty()) {

            editTextCampo.setError("Add a name");
            editTextCampo.requestFocus();
        } else {

            editTextCampo.setText("");
            editTextCampo.setError(null);
            editTextCampo.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            Toast.makeText(this, "Alteração guardada", Toast.LENGTH_SHORT).show();
        }
    }
}
