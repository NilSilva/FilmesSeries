package nil.filmesseries;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mostraTudo) {
            Intent intent = new Intent(this, Tudo.class);
            startActivity(intent);
        }else if(id == R.id.action_mostraPessoas){
            Intent intent = new Intent(this, PessoasActivity.class);
            startActivity(intent);
        }else if(id == R.id.action_genero){
            Intent intent = new Intent(this, GeneroActivity.class);
            startActivity(intent);
        }else if(id == R.id.action_aMinhaLista){
            Toast.makeText(this, "A minha lista", Toast.LENGTH_LONG).show();
        }else if(id == R.id.action_opcoes){
            Toast.makeText(this, "Opções", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Sobre", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
