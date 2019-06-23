package nil.filmesseries;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddPeopleActivity extends AppCompatActivity {

    //Declaração de objetos e variaveis
    private String TAG = "AddPeopleActivity";

    private EditText nome;
    private EditText funcao;
    private EditText data;

    private static final int RESULT_IMAGE = 1;

    private ImageView imageView;
    private byte imageInByte[];

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

        imageView = findViewById(R.id.imageViewPfoto);
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

            convertToByte();
            p.setImagem(imageInByte);
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

    public void getImage(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_IMAGE);
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_IMAGE && resultCode == RESULT_OK && data != null){

            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            convertToByte();
        }
    }

    private void convertToByte() {
        Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        imageInByte = stream.toByteArray();
    }
}
