package ca.qc.cegep_heritage.athenaeummusica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private ListView listViewAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(PreferenceCodes.SETTINGS, Context.MODE_PRIVATE);

        // Check if any preferences have already been set. If no, generate the defaults.
        if(sharedPreferences.getInt(PreferenceCodes.THEME, 0) == 0) {
            SharedPreferences.Editor editSharedPrefs = sharedPreferences.edit();
            editSharedPrefs.putInt(PreferenceCodes.THEME, R.style.AppTheme);
            editSharedPrefs.apply();
        }

        setTheme(sharedPreferences.getInt(PreferenceCodes.THEME, R.style.AppTheme));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewAlbum = findViewById(R.id.list);
        listViewAlbum.setVisibility(View.INVISIBLE);

        db = new DatabaseHandler(this);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AlbumManipulationActivity.class),
                        IntentCodes.ADD_ALBUM);
            }
        });

        if(db.numberOfRows() != 0) {
            generateList();
        }

        listViewAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView albumId = view.findViewById(R.id.txtListId);
                Intent editIntent = new Intent(MainActivity.this, ViewAlbumActivity.class);
                editIntent.putExtra(IntentCodes.VIEW_ALBUM_ID,
                        Integer.parseInt(albumId.getText().toString()));
                startActivityForResult(editIntent, IntentCodes.VIEW_ALBUM);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case IntentCodes.ADD_ALBUM:
                if(resultCode == IntentCodes.ADD_ALBUM_SUCCESS) {
                    Snackbar.make(this.findViewById(R.id.mainContent), "New album added.", Snackbar.LENGTH_SHORT).show();
                    generateList();
                }
                if(resultCode == IntentCodes.ADD_ALBUM_FAIL) {
                    Snackbar.make(this.findViewById(R.id.mainContent), "An error has occurred.", Snackbar.LENGTH_SHORT).show();
                }
                if(resultCode == IntentCodes.ADD_ALBUM_CANCEL) {
                    Snackbar.make(this.findViewById(R.id.mainContent), "Add album canceled.", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case IntentCodes.VIEW_ALBUM:
                if(resultCode == IntentCodes.VIEW_ALBUM_DELETE) {
                    Snackbar.make(this.findViewById(R.id.mainContent), "Album deleted.", Snackbar.LENGTH_SHORT).show();
                    generateList();
                }
        }
    }

    private void generateList() {
        TextView txtViewEmpty = findViewById(R.id.txtViewEmpty);
        txtViewEmpty.setVisibility(View.INVISIBLE);
        List<Album> albums = db.getAllAlbums();
        ListAdapter albumListView = new ListAdapter(this, R.layout.album_list_layout, albums);
        listViewAlbum.setAdapter(albumListView);
        listViewAlbum.setVisibility(View.VISIBLE);
    }
}
