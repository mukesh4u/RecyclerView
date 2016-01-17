package solutions.developer.android.com.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by Mukesh on 12/20/2015.
 */
public class MediaRVAdapter extends RecyclerView.Adapter<MediaRVAdapter.MediaListRowHolder> {


    private List<MediaFileInfo> itemList;

    private Context mContext;

    public MediaRVAdapter(Context context, List<MediaFileInfo> itemList) {
        this.itemList = itemList;
        this.mContext = context;
    }

    @Override
    public MediaListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, null);
        MediaListRowHolder mh = new MediaListRowHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(MediaListRowHolder mediaListRowHolder, int i) {
        try {
            MediaFileInfo item = itemList.get(i);
            mediaListRowHolder.title.setText(item.getFileName());
            Uri uri = Uri.fromFile(new File(item.getFilePath()));
            if (item.getFileType().equalsIgnoreCase("audio")) {
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(item.getFilePath());
                try {
                    if (mmr != null) {
                        byte[] art = mmr.getEmbeddedPicture();
                        if (art != null && art.length > 0) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(art, 0, art.length);
                            if (bmp != null) {
                                //bmp = ThumbnailUtils.extractThumbnail(bmp, 80, 50);
                                mediaListRowHolder.thumbnail.setImageBitmap(bmp);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }

    public class MediaListRowHolder extends RecyclerView.ViewHolder {
        protected ImageView thumbnail;
        protected TextView title;

        public MediaListRowHolder(View view) {
            super(view);
            this.thumbnail = (ImageView) view.findViewById(R.id.image);
            this.title = (TextView) view.findViewById(R.id.name);
        }
    }
}