package nil.filmesseries;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
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
    private Button butaoCancelar;

    private Menu menu;

    private ImageView imageView;
    private byte[] imagem;

    private static final int RESULT_IMAGE = 1;

    private Bitmap btm;
    private boolean imagemMuda = false;

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
        butaoCancelar = findViewById(R.id.butãoCancelP);
        imageView = findViewById(R.id.imageViewP);

        Intent intent = getIntent();
        P = intent.getParcelableExtra("P");

        preencheCampos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar_pessoas, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_editar_P) {

            nome.setEnabled(true);
            funcao.setEnabled(true);
            data.setEnabled(true);
            item.setVisible(false);
            button.setVisibility(View.VISIBLE);
            butaoCancelar.setText(R.string.butãoCancel);
            return true;
        } else if (id == R.id.action_apagar_P) {
            Delete();
        }

        return super.onOptionsItemSelected(item);
    }

    public void Save(View view) {

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

        //-------------------------------------------Verificação da função-------------------------------------------
        textoCampo = funcao.getText().toString();

        if (textoCampo.isEmpty()) {

            funcao.setError(getString(R.string.add_job));
            funcao.requestFocus();
            Erros = true;
        }

        //-------------------------------------------Verificação do nome-------------------------------------------
        textoCampo = nome.getText().toString();

        if (textoCampo.isEmpty()) {

            nome.setError("Add a name");
            nome.requestFocus();
            Erros = true;
        }

        //-------------------------------------------Se não existitem erros fechar a activity-------------------------------------------
        if (!Erros) {

            EditarBD();
            Toast.makeText(this, "Data successfully saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void EditarBD() {

        BdFsOpenHelper openHelper = new BdFsOpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        BdTable_Pessoas tabelaP = new BdTable_Pessoas(db);

        P.setNome(nome.getText().toString());
        P.setFuncao(funcao.getText().toString());
        P.setDataNascimento(data.getText().toString());
        P.setImagem(imagem);

        tabelaP.update(P.getContentValues(), BdTable_Pessoas._ID + "=?", new String[]{String.valueOf(P.getID())});
    }

    public void Cancel(View view) {
        if (button.getVisibility() == View.VISIBLE) {

            menu.findItem(R.id.action_editar_P).setVisible(true);
            button.setVisibility(View.INVISIBLE);
            butaoCancelar.setText(R.string.butãoVoltar);
            preencheCampos();
        } else {
            finish();
        }
    }

    private void preencheCampos() {

        Cursor cursor = getContentResolver().query(
                FilmesContentProvider.ENDERECO_PESSOAS,
                BdTable_Pessoas.TODAS_COLUNAS,
                BdTable_Pessoas._ID + "=?",
                new String[]{String.valueOf(P.getID())},
                null
        );

        Pessoas p;

        while (cursor.moveToNext()) {

            p = Pessoas.fromCursor(cursor);

            imagem = p.getImagem();
        }

        nome.setText(P.getNome());
        funcao.setText(P.getFuncao());
        data.setText(P.getDataNascimento());
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);

        if (!imagemMuda) {

            imageView.setImageBitmap(bitmap);
            btm = bitmap;
        } else {

            imageView.setImageBitmap(btm);
        }

        nome.setEnabled(false);
        funcao.setEnabled(false);
        data.setEnabled(false);
    }

    public void Delete() {

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

    public void getImage(View view) {

        if (button.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_IMAGE);
            imagemMuda = true;
        }
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

        if (requestCode == RESULT_IMAGE && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imagem = stream.toByteArray();
        }
    }
}
