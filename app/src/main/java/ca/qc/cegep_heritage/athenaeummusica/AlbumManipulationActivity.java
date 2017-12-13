package ca.qc.cegep_heritage.athenaeummusica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class AlbumManipulationActivity extends AppCompatActivity {

    Spinner ddlGenres;
    Spinner ddlFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_manipulation);

        ddlGenres = findViewById(R.id.ddlGenre);
        ddlFormat = findViewById(R.id.ddlFormat);

        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>
                (this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.genres));

        ArrayAdapter<String> formatAdapter = new ArrayAdapter<>
                (this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.formats));

        ddlGenres.setAdapter(genreAdapter);
        ddlFormat.setAdapter(formatAdapter);

    }
}
