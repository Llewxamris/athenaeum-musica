package ca.qc.cegep_heritage.athenaeummusica;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private ListView listViewAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            return true;
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
