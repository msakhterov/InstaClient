package ru.msakhterov.instaclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.msakhterov.instaclient.model.Picture;


public class PicturesGalleryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PictureGalleryListener mPictureGalleryListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPictureGalleryListener = (PictureGalleryListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.setAdapter(new PictureGalleryAdapter(new ArrayList<Picture>()));
        return view;
    }

    public interface PictureGalleryListener {
        void onPictureSelected(Picture picture);
    }

    private class PictureHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Picture picture;

        PictureHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.picture_gallery_item, parent, false));
            itemView.setOnClickListener(this);
        }

        public void bind(Picture picture) {
            this.picture = picture;
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

        @Override
        public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new PictureHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(final PictureHolder holder, int position) {
            Picture picture = pictures.get(position);
            holder.bind(picture);
        }

        @Override
        public int getItemCount() {
            return pictures.size();
        }
    }
}
