package nil.filmesseries;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

public class DeleteFSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_fs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        builder.setMessage(getString(R.string.deleteMovie));
        builder.setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
                Toast.makeText(DeleteFSActivity.this, "Entry deleted", Toast.LENGTH_SHORT).show();
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
