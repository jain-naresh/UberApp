package com.uber.test.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uber.test.R;
import com.uber.test.data.image.ImageLoader;
import com.uber.test.data.model.FlickrImagePO;

import java.util.List;

public class FlickerImagesAdapter extends RecyclerView.Adapter<FlickerImagesAdapter.ViewHolder> {

    public static final String TAG = FlickerImagesAdapter.class.getSimpleName();

    private List<FlickrImagePO> flickrPhotoList;
    private ImageLoader imgLoader;

    public FlickerImagesAdapter(Context context, List<FlickrImagePO> flickrPhotos) {
        super();
        flickrPhotoList = flickrPhotos;
        imgLoader = new ImageLoader(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_photo, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        FlickrImagePO flickrImagePO = flickrPhotoList.get(position);
        viewHolder.setData(flickrImagePO, position);
    }

    @Override
    public int getItemCount() {
        return flickrPhotoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mPhotoImageView;
        TextView mPhotoTextView;

        private FlickrImagePO flickrImagePO;

        ViewHolder(View itemView) {
            super(itemView);
            mPhotoImageView = itemView.findViewById(R.id.ivPhotoItem);
            mPhotoTextView = itemView.findViewById(R.id.tvPhotoItem);
        }

        void setData(final FlickrImagePO photo, final int position) {
            flickrImagePO = photo;

            if (flickrImagePO.getImageURL() != null) {
                mPhotoImageView.setImageDrawable(null);
                imgLoader.displayImage(flickrImagePO.getImageURL(), R.mipmap.ic_launcher, mPhotoImageView);
            }
            mPhotoTextView.setText(flickrImagePO.getTitle());
        }
    }
}
