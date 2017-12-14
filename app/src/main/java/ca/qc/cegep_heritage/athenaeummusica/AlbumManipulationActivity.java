package ca.qc.cegep_heritage.athenaeummusica;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class AlbumManipulationActivity extends AppCompatActivity {


    private static final String INSTANCE_NAME = "name";
    private static final String INSTANCE_PRICE = "price";
    private static final String INSTANCE_ARTIST = "artist";
    private static final String INSTANCE_RELEASE_YEAR = "releaseYear";
    private static final String INSTANCE_GENRE = "genre";
    private static final String INSTANCE_FORMAT = "format";
    private static final String INSTANCE_IMAGE = "image";
    private static final int CAMERA_REQUEST = 1888;

    private EditText edtxtName;
    private EditText edtxtArtist;
    private EditText edtxtPrice;
    private EditText edtxtReleaseYear;
    private ImageButton imgBtnImage;
    private Spinner ddlGenres;
    private Spinner ddlFormat;
    private DatabaseHandler db = new DatabaseHandler(this);;
    private boolean imageWasSet;
    private boolean isEdit = false;
    private Album editAlbum = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_manipulation);


        Button btnCommit = findViewById(R.id.btnCommit);
        Button btnCancel = findViewById(R.id.btnCancle);



        if(getIntent().getIntExtra(IntentCodes.VIEW_ALBUM_ID, -1) != -1) {
            isEdit = true;
            editAlbum = db.getAlbum(getIntent().getIntExtra(IntentCodes.VIEW_ALBUM_ID, -1));
        }

        edtxtName = findViewById(R.id.edtxtName);
        edtxtArtist = findViewById(R.id.edtxtArtist);
        edtxtPrice = findViewById(R.id.edtxtPrice);
        edtxtReleaseYear = findViewById(R.id.edtxtReleaseYear);
        imgBtnImage = findViewById(R.id.imgBtnImage);
        ddlGenres = findViewById(R.id.ddlGenre);
        ddlFormat = findViewById(R.id.ddlFormat);
        db = new DatabaseHandler(this);

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

        imgBtnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE),
                       CAMERA_REQUEST);
            }
        });

        if(isEdit) {
            int genrePosition = genreAdapter.getPosition(editAlbum.getGenre());
            int formatPosition = formatAdapter.getPosition(editAlbum.getFormat());

            edtxtName.setText(editAlbum.getName());
            ddlGenres.setSelection(genrePosition);
            ddlFormat.setSelection(formatPosition);

            if(editAlbum.getArtist() != null) {
                edtxtArtist.setText(editAlbum.getArtist());
            }

            if(editAlbum.getReleaseYear() != -1) {
                edtxtReleaseYear.setText(String.valueOf(editAlbum.getReleaseYear()));
            }

            if(editAlbum.getPrice() != -1) {
                edtxtPrice.setText(String.valueOf(editAlbum.getPrice()));
            }

            btnCommit.setText(getResources().getString(R.string.edit_album));

            TextView txtModifyHeader = findViewById(R.id.txtModifyHeader);
            txtModifyHeader.setText("Edit an Album");
        }

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Album album = new Album(edtxtName.getText().toString(),
                        ddlGenres.getSelectedItem().toString(),
                        ddlFormat.getSelectedItem().toString());

                if (!edtxtArtist.getText().toString().equals("")) {
                    album.setArtist(edtxtArtist.getText().toString());
                }

                if (!edtxtPrice.getText().toString().equals("")) {
                    album.setPrice(Double.valueOf(edtxtPrice.getText().toString()));
                }

                if(!edtxtReleaseYear.getText().toString().equals("")) {
                    album.setReleaseYear(Integer.valueOf(edtxtReleaseYear.getText().toString()));
                }

                if(imageWasSet) {
                    album.setImage(convertDrawableToByteArray(imgBtnImage.getDrawable()));
                }

                if(isEdit) {
                    try {
                        album.setId(editAlbum.getId());
                        db.updateAlbum(album);
                        setResult(IntentCodes.EDIT_ALBUM_SUCCESS);
                        finish();
                    } catch (Exception e) {
                        setResult(IntentCodes.EDIT_ALBUM_FAIL);
                        finish();
                    }
                } else {
                    try {
                        db.insertAlbum(album);
                        setResult(IntentCodes.ADD_ALBUM_SUCCESS);
                        finish();
                    } catch (Exception e) {
                        setResult(IntentCodes.ADD_ALBUM_FAIL);
                        finish();
                    }
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEdit) {
                    setResult(IntentCodes.EDIT_ALBUM_CANCEL);
                    finish();
                } else {
                    setResult(IntentCodes.ADD_ALBUM_CANCEL);
                    finish();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imgBtnImage.setImageBitmap(photo);
            } catch (NullPointerException ex) {
                // Error
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        /* Save state on exit/orientation change. */
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString(INSTANCE_NAME, edtxtName.getText().toString());
        savedInstanceState.putString(INSTANCE_ARTIST, edtxtArtist.getText().toString());
        savedInstanceState.putString(INSTANCE_PRICE, edtxtPrice.getText().toString());
        savedInstanceState.putString(INSTANCE_RELEASE_YEAR, edtxtReleaseYear.getText().toString());
        savedInstanceState.putInt(INSTANCE_GENRE, ddlGenres.getSelectedItemPosition());
        savedInstanceState.putInt(INSTANCE_FORMAT, ddlFormat.getSelectedItemPosition());
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgBtnImage.getBackground();
//        savedInstanceState.putParcelable(INSTANCE_IMAGE, bitmapDrawable.getBitmap());
    } // onSavedInstanceSate(...)

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        /* Restore state on exit/orientation change. */
        super.onRestoreInstanceState(savedInstanceState);

        edtxtName.setText(savedInstanceState.getString(INSTANCE_NAME));
        edtxtArtist.setText(savedInstanceState.getString(INSTANCE_ARTIST));
        edtxtPrice.setText(savedInstanceState.getString(INSTANCE_PRICE));
        edtxtReleaseYear.setText(savedInstanceState.getString(INSTANCE_RELEASE_YEAR));
        ddlGenres.setSelection(savedInstanceState.getInt(INSTANCE_GENRE));
        ddlFormat.setSelection(savedInstanceState.getInt(INSTANCE_FORMAT));
//        imgBtnImage.setImageBitmap((Bitmap) savedInstanceState.getParcelable(INSTANCE_IMAGE));
    } // onRestoreInstanceState(...)

    private byte[] convertDrawableToByteArray(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

}
