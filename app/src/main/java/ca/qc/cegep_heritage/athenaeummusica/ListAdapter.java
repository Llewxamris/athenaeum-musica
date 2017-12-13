package ca.qc.cegep_heritage.athenaeummusica;


import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class ListAdapter extends ArrayAdapter<Album> {

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<Album> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.album_list_layout, null);
        }

        TextView txtAlbumName = v.findViewById(R.id.txtListName);
        TextView txtListArtist = v.findViewById(R.id.txtListArtist);
        ImageView imgAlbum = v.findViewById(R.id.imgAlbum);

        Album album = getItem(position);

        if (album != null) {
            txtAlbumName.setText(album.getName());

            if(album.getArtist() != null) {
                txtListArtist.setText(album.getArtist());
            } else {
                txtListArtist.setText("");
            }

            if(album.getImage() != null){
                // Add Image
                byte[] b = album.getImage();
                Drawable image = new BitmapDrawable(v.getResources(), BitmapFactory.decodeByteArray(b, 0, b.length));
                imgAlbum.setBackground(image);
            }
        }

        return v;
    }

}
