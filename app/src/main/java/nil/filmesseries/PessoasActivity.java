package nil.filmesseries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PessoasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void Add(View view) {

        Intent intent = new Intent(this, AddPeopleActivity.class);
        startActivity(intent);
    }

    public void Edit(View view) {

        Intent intent = new Intent(this, EditPeopleActivity.class);
        startActivity(intent);
    }

    public void Delete(View view) {

        Intent intent = new Intent(this, DeletePeopleActivity.class);
        startActivity(intent);
    }
}
