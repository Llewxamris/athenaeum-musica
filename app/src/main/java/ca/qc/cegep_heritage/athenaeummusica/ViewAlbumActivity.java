package ca.qc.cegep_heritage.athenaeummusica;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewAlbumActivity extends AppCompatActivity {

    DatabaseHandler db = new DatabaseHandler(this);
    Album album;
    TextView txtViewAlbumName;
    TextView txtViewArtist;
    TextView txtViewPrice;
    TextView txtViewReleaseDate;
    TextView txtViewGenre;
    TextView txtViewFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_album);

        txtViewAlbumName = findViewById(R.id.txtViewAlbumName);
        txtViewArtist = findViewById(R.id.txtViewArtist);
        txtViewPrice = findViewById(R.id.txtViewPrice);
        txtViewReleaseDate= findViewById(R.id.txtViewReleaseDate);
        txtViewGenre = findViewById(R.id.txtViewGenre);
        txtViewFormat = findViewById(R.id.txtViewFormat);

        ImageButton imgbtnEdit = findViewById(R.id.imgbtnEdit);
        ImageButton imgbtnDelete = findViewById(R.id.imgbtnDelete);

        album = db.getAlbum(getIntent().getIntExtra(IntentCodes.VIEW_ALBUM_ID, 1));

        generateAlbumInfo();

        imgbtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ViewAlbumActivity.this,
                                AlbumManipulationActivity.class)
                                    .putExtra(IntentCodes.VIEW_ALBUM_ID, album.getId()),
                                IntentCodes.EDIT_ALBUM);
            }
        });

        imgbtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewAlbumActivity.this);
                builder.setMessage("Are you sure you want to delete this album?")
                        .setTitle("Delete Album");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteAlbum(album.getId());
                        setResult(IntentCodes.VIEW_ALBUM_DELETE);
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case IntentCodes.EDIT_ALBUM:
                if(resultCode == IntentCodes.EDIT_ALBUM_SUCCESS) {
                    Snackbar.make(this.findViewById(R.id.viewContent), "Album edited.", Snackbar.LENGTH_SHORT).show();
                    album = db.getAlbum(getIntent().getIntExtra(IntentCodes.VIEW_ALBUM_ID, 1));
                    generateAlbumInfo();
                }
                if(resultCode == IntentCodes.EDIT_ALBUM_FAIL) {
                    Snackbar.make(this.findViewById(R.id.viewContent), "An error has occurred.", Snackbar.LENGTH_SHORT).show();
                }
                if(resultCode == IntentCodes.EDIT_ALBUM_CANCEL) {
                    Snackbar.make(this.findViewById(R.id.viewContent), "Edit album canceled.", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void generateAlbumInfo() {
        txtViewAlbumName.setText(album.getName());
        txtViewFormat.setText(album.getFormat());
        txtViewGenre.setText(album.getGenre());

        if (album.getArtist() != null) {
            txtViewArtist.setText(album.getArtist());
        } else {
            txtViewArtist.setText(" - ");
        }

        if(album.getPrice() != -1) {
            txtViewPrice.setText(String.valueOf(album.getPrice()));
        } else {
            txtViewPrice.setText(" - ");
        }

        if(album.getReleaseYear() != -1) {
            txtViewReleaseDate.setText(String.valueOf(album.getReleaseYear()));
        } else {
            txtViewReleaseDate.setText(" - ");
        }
    }
}
