package ru.msakhterov.instaclient;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.msakhterov.instaclient.model.Picture;
import ru.msakhterov.instaclient.utils.Constants;
import ru.msakhterov.instaclient.utils.PictureLab;


public class PicturesGalleryFragment extends Fragment implements UpdatableFragment {

    public static final String TAG_ALL = "all";
    public static final String TAG_DB = "db";
    public static final String TAG_WEB = "web";

    private static final String TAG = "PicturesGalleryFragment";
    private static final String ARG_FRAGMENT_TYPE = "fragment_type";
    private RecyclerView mRecyclerView;
    private PictureGalleryListener mPictureGalleryListener;
    private PictureGalleryAdapter mPictureGalleryAdapter;
    private PictureLab mPictureLab;
    private int spanCount;
    private int fragmentType;

    public static PicturesGalleryFragment newInstance(int fragmentType) {
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT_TYPE, fragmentType);

        PicturesGalleryFragment fragment = new PicturesGalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPictureGalleryListener = (PictureGalleryListener) context;
        mPictureLab = new PictureLab(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            spanCount = Constants.SPAN_COUNT_VERTICAL;
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            spanCount = Constants.SPAN_COUNT_HORIZONTAL;
        fragmentType = getArguments().getInt(ARG_FRAGMENT_TYPE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume " + "fragmentType = " + fragmentType);
        updateUI();
    }

    public void updateUI() {
        List<Picture> pictures;
        if (fragmentType == Constants.ALL_FRAGMENT_TYPE)
            pictures = mPictureLab.getPicturesList();
        else pictures = mPictureLab.getFavouritesPicturesList();
        if (mPictureGalleryAdapter == null) {
            mPictureGalleryAdapter = new PictureGalleryAdapter(pictures);
        } else {
            mPictureGalleryAdapter.setPictures(pictures);
            mPictureGalleryAdapter.notifyDataSetChanged();
        }
        mRecyclerView.setAdapter(mPictureGalleryAdapter);
    }

    public interface PictureGalleryListener {
        void onPictureSelected(Picture picture);
    }

    private class PictureHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Picture picture;
        private ImageView mItemImageView;
        private ToggleButton mToggleButton;

        PictureHolder(View itemView) {
            super(itemView);
            mItemImageView = itemView.findViewById(R.id.item_image_view);
            mToggleButton = itemView.findViewById(R.id.item_favourites_btn);

            mItemImageView.setOnClickListener(this);
            mToggleButton.setOnClickListener(this);
        }

        public void bind(Picture picture) {
            this.picture = picture;
            Picasso.with(getContext()).load(picture.getPath()).resize(mPictureLab.getImagePreviewSize(spanCount), mPictureLab.getImagePreviewSize(spanCount)).into(mItemImageView);
            mToggleButton.setChecked(picture.isFavorite() == Constants.IS_FAVORITE);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_image_view:
                    mPictureGalleryListener.onPictureSelected(picture);
                    break;
                case R.id.item_favourites_btn:
                    if (mToggleButton.isChecked()) {
                        picture.setFavorite(Constants.IS_FAVORITE);
                        Log.d(TAG, "setFavorite");
                    } else {
                        picture.setFavorite(Constants.IS_NOT_FAVORITE);
                        Log.d(TAG, "setNoFavorite");
                    }
                    mPictureLab.setFavourites(picture);
                    break;
            }
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
}
