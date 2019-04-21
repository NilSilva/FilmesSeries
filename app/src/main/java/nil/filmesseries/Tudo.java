package nil.filmesseries;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class Tudo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tudo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void AdicionarFS(View view) {

        Intent intent = new Intent(this, AdicionarFSActivity.class);
        startActivity(intent);
    }

    public void EditarFS(View view) {

        Intent intent = new Intent(this, EditarFSActivity.class);
        startActivity(intent);
    }

    public void DeleteFS(View view) {

        Intent intent = new Intent(this, DeleteFSActivity.class);
        startActivity(intent);
    }
}
