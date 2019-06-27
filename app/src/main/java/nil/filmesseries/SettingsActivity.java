package nil.filmesseries;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.muddzdev.styleabletoast.StyleableToast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class SettingsActivity extends AppCompatActivity {

    private String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createFolder() {
        String folderSD = Environment.getExternalStorageDirectory() + "//FSBackup";
        File sd = new File(folderSD);
        if (!sd.exists()) {
            sd.mkdir();
            Log.d(TAG, "pasta criada");
        } else {
            Log.d(TAG, "pasta existe");
        }
    }

    public void Backup(View view) {

        try {
            createFolder();
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            Log.d(TAG, "canWrite - " + sd.canWrite());
            if (sd.canWrite()) {
                String currentDBPath = "//data//nil.filmesseries//databases//Fs.db";
                String backupDBPath = "//FSBackup//FSbackupDB.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                Log.d(TAG, "exists - " + currentDB.exists());
                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    long i = dst.transferFrom(src, 0, src.size());
                    Log.d(TAG, "tamanho - " + i);
                    src.close();
                    dst.close();
                    StyleableToast.makeText(this, getString(R.string.SucessoExportarDB), R.style.SucessoBD).show();
                }
            }
        } catch (Exception e) {

            Log.d(TAG, e + "");
            Log.d(TAG, "error a fazer backup");
            StyleableToast.makeText(this, getString(R.string.exportingDB), R.style.ErroBD).show();
        }
    }

    public void Import(View view) {

        String currentDBPath = "//data//nil.filmesseries//databases//Fs.db";

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            File currentDB = new File(data, currentDBPath);

            Log.d(TAG, "canWrite - " + currentDB.canWrite());
            if (currentDB.canWrite()) {
                String backupDBPath = "//FSBackup//FSbackupDB.db";
                File backupDB = new File(sd, backupDBPath);

                Log.d(TAG, "exists - " + currentDB.exists());
                if (currentDB.exists()) {
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    FileChannel src = new FileInputStream(backupDB).getChannel();
                    long i = dst.transferFrom(src, 0, src.size());
                    Log.d(TAG, "tamanho - " + i);
                    src.close();
                    dst.close();
                    StyleableToast.makeText(this, getString(R.string.SucessoImportarDB), R.style.SucessoBD).show();
                }
            }
        } catch (Exception e) {

            Log.d(TAG, e + "");
            Log.d(TAG, "error a fazer backup");
            StyleableToast.makeText(this, getString(R.string.importingDB), R.style.ErroBD).show();
        }
    }
}
