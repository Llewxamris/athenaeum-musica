package ca.qc.cegep_heritage.athenaeummusica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "athenaeum_musica.db";
    // Albums table setup
    private static final String ALBUMS_TABLE_NAME = "albums";
    private static final String ALBUMS_COLUMN_ID = "id";
    private static final String ALBUMS_COLUMN_NAME = "name";
    private static final String ALBUMS_COLUMN_ARTIST = "artist";
    private static final String ALBUMS_COLUMN_GENRE = "genre";
    private static final String ALBUMS_COLUMN_RELEASE_YEAR = "release_year";
    private static final String ALBUMS_COLUMN_FORMAT = "format";
    private static final String ALBUMS_COLUMN_PRICE = "price";
    private static final String ALBUMS_COLUMN_IMAGE = "image";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + ALBUMS_TABLE_NAME +
                        " (" + ALBUMS_COLUMN_ID + " integer primary key autoincrement, " +
                        ALBUMS_COLUMN_NAME + " text not null, " +
                        ALBUMS_COLUMN_ARTIST + " text, " +
                        ALBUMS_COLUMN_GENRE + " text, " +
                        ALBUMS_COLUMN_RELEASE_YEAR + " integer, " +
                        ALBUMS_COLUMN_FORMAT + " text, " +
                        ALBUMS_COLUMN_PRICE + " real, " +
                        ALBUMS_COLUMN_IMAGE + " blob )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + ALBUMS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertAlbum(Album album)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ALBUMS_COLUMN_NAME, album.getName());
        contentValues.put(ALBUMS_COLUMN_FORMAT, album.getArtist());
        contentValues.put(ALBUMS_COLUMN_GENRE, album.getGenre());

        if(album.getArtist() != null) {
            contentValues.put(ALBUMS_COLUMN_ARTIST, album.getArtist());
        }

        if(album.getReleaseYear() == -1) {
            contentValues.put(ALBUMS_COLUMN_RELEASE_YEAR, album.getReleaseYear());
        }

        if(album.getPrice() == -1) {
            contentValues.put(ALBUMS_COLUMN_PRICE, album.getPrice());
        }

        if(album.getImage() != null) {
            contentValues.put(ALBUMS_COLUMN_IMAGE, album.getImage());
        }

        try {
            db.insert(ALBUMS_TABLE_NAME, null, contentValues);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Album getAlbum(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + ALBUMS_TABLE_NAME + " where id="+id+"", null );
        if (cursor.moveToFirst()) {
            return new Album(
                    cursor.getInt(cursor.getColumnIndex(ALBUMS_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(ALBUMS_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(ALBUMS_COLUMN_ARTIST)),
                    cursor.getString(cursor.getColumnIndex(ALBUMS_COLUMN_GENRE)),
                    cursor.getInt(cursor.getColumnIndex(ALBUMS_COLUMN_RELEASE_YEAR)),
                    cursor.getString(cursor.getColumnIndex(ALBUMS_COLUMN_FORMAT)),
                    cursor.getDouble(cursor.getColumnIndex(ALBUMS_COLUMN_PRICE)),
                    cursor.getBlob(cursor.getColumnIndex(ALBUMS_COLUMN_IMAGE)));
        } else {
            return null;
        }
    }

//    public int numberOfRows(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        return (int) DatabaseUtils.queryNumEntries(db, ALBUMS_TABLE_NAME);
//    }

    public boolean updateAlbum (Album album)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ALBUMS_COLUMN_NAME, album.getName());
        contentValues.put(ALBUMS_COLUMN_FORMAT, album.getFormat());
        contentValues.put(ALBUMS_COLUMN_GENRE, album.getGenre());

        if(album.getArtist() != null) {
            contentValues.put(ALBUMS_COLUMN_ARTIST, album.getArtist());
        }

        if(album.getReleaseYear() == -1) {
            contentValues.put(ALBUMS_COLUMN_RELEASE_YEAR, album.getReleaseYear());
        }

        if(album.getPrice() == -1) {
            contentValues.put(ALBUMS_COLUMN_PRICE, album.getPrice());
        }

        if(album.getImage() != null) {
            contentValues.put(ALBUMS_COLUMN_IMAGE, album.getImage());
        }

        try {
            db.update(ALBUMS_TABLE_NAME, contentValues, "id = ? ", new String[] {
                    Integer.toString(album.getId())
            });
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public boolean deleteAlbum (int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ALBUMS_TABLE_NAME, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }


    public ArrayList getAllAlbums()
    {
        ArrayList<Album> albumList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + ALBUMS_TABLE_NAME, null );
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            albumList.add(new Album(
                    cursor.getInt(cursor.getColumnIndex(ALBUMS_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(ALBUMS_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(ALBUMS_COLUMN_ARTIST)),
                    cursor.getString(cursor.getColumnIndex(ALBUMS_COLUMN_GENRE)),
                    cursor.getInt(cursor.getColumnIndex(ALBUMS_COLUMN_RELEASE_YEAR)),
                    cursor.getString(cursor.getColumnIndex(ALBUMS_COLUMN_FORMAT)),
                    cursor.getDouble(cursor.getColumnIndex(ALBUMS_COLUMN_PRICE)),
                    cursor.getBlob(cursor.getColumnIndex(ALBUMS_COLUMN_IMAGE))));
            cursor.moveToNext();
        }
        return albumList;
    }

}
