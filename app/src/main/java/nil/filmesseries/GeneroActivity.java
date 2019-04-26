package nil.filmesseries;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
        EditText editTextCampo = findViewById(R.id.editTextAddNomeGen);
        String textoCampo = editTextCampo.getText().toString();

        if(textoCampo.isEmpty()){

            editTextCampo.setError("Add a name");
            editTextCampo.requestFocus();
        }else{

            editTextCampo.setText("");
            editTextCampo.setError(null);
            editTextCampo.clearFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            Toast.makeText(this, "Genero adicionado", Toast.LENGTH_SHORT).show();
        }
    }

    public void Delete(View view) {
        Toast.makeText(this, "Genero removido", Toast.LENGTH_SHORT).show();
    }

    public void Save(View view) {
        EditText editTextCampo = findViewById(R.id.editTextChange);
        String textoCampo = editTextCampo.getText().toString();

        if(textoCampo.isEmpty()){

            editTextCampo.setError("Add a name");
            editTextCampo.requestFocus();
        }else{

            editTextCampo.setText("");
            editTextCampo.setError(null);
            editTextCampo.clearFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            Toast.makeText(this, "Alteração guardada", Toast.LENGTH_SHORT).show();
        }
    }
}
