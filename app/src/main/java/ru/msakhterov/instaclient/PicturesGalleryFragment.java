package ru.msakhterov.instaclient;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.msakhterov.instaclient.model.Picture;
import ru.msakhterov.instaclient.utils.Constants;
import ru.msakhterov.instaclient.utils.PictureLab;


public class PicturesGalleryFragment extends Fragment {

    private static final String TAG = "PicturesGalleryFragment";
    private RecyclerView mRecyclerView;
    private PictureGalleryListener mPictureGalleryListener;
    private PictureGalleryAdapter mPictureGalleryAdapter;
    private PictureLab mPictureLab;
    private int spanCount;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPictureGalleryListener = (PictureGalleryListener) context;
        mPictureLab = new PictureLab(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            spanCount = Constants.SPAN_COUNT_VERTICAL;
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            spanCount = Constants.SPAN_COUNT_HORIZONTAL;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
        updateUI();
        return view;
    }

    public interface PictureGalleryListener {
        void onPictureSelected(Picture picture);
    }

    private class PictureHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Picture picture;
        private ImageView mItemImageView;

        PictureHolder(View itemView) {
            super(itemView);
            mItemImageView = itemView.findViewById(R.id.item_image_view);
            itemView.setOnClickListener(this);
        }

        public void bind(Picture picture) {
            this.picture = picture;
            Picasso.with(getContext()).load(picture.getPath()).resize(mPictureLab.getImagePreviewSize(spanCount), mPictureLab.getImagePreviewSize(spanCount)).into(mItemImageView);
        }

        @Override
        public void onClick(View view) {
            mPictureGalleryListener.onPictureSelected(picture);
        }
    }

    private class PictureGalleryAdapter extends RecyclerView.Adapter<PictureHolder> {

        private List<Picture> pictures;

        PictureGalleryAdapter(List<Picture> pictures) {
            this.pictures = pictures;
        }

        @NonNull
        @Override
        public PictureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.picture_gallery_item, parent, false);
            return new PictureHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final PictureHolder holder, int position) {
            Picture picture = pictures.get(position);
            holder.mItemImageView.setVisibility(View.VISIBLE);
            holder.bind(picture);
        }

        @Override
        public int getItemCount() {
            return pictures.size();
        }

        public void setPictures(List<Picture> pictures) {
            this.pictures = pictures;
        }
    }

    public void updateUI() {
        List<Picture> pictures = mPictureLab.getPicturesList();
        if (mPictureGalleryAdapter == null) {
            mPictureGalleryAdapter = new PictureGalleryAdapter(pictures);
        } else {
            mPictureGalleryAdapter.setPictures(pictures);
            mPictureGalleryAdapter.notifyDataSetChanged();
        }
        mRecyclerView.setAdapter(mPictureGalleryAdapter);
    }
}
