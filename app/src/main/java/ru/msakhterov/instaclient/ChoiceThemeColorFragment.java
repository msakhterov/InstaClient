package ru.msakhterov.instaclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;


public class ChoiceThemeColorFragment extends Fragment {

    private static final String TAG = "ChoiceThemeColorFragmentTag";

    private RecyclerView mRecyclerView;
    private ThemeColorListener mThemeColorListener;
    private List<Integer> colors = Arrays.asList(Color.RED, Color.GREEN, Color.BLUE);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mThemeColorListener = (ThemeColorListener) context;
    }

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Фрагмент выбора темы запущен");
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.setAdapter(new ThemeColorAdapter(colors));
        return view;
    }

    /**
     * Интерфейс взаимодействия с активностью
     */
    public interface ThemeColorListener {
        void onColorSelected(Integer color);
    }

    /**
     * Холдер
     */
    private class ThemeColorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Integer color;

        ThemeColorHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.choice_theme_color_item, parent, false));

            itemView.setOnClickListener(this);
        }

        public void bind(Integer color) {
            itemView.setBackgroundColor(color);
            this.color = color;
        }

        @Override
        public void onClick(View view) {
            mThemeColorListener.onColorSelected(color);
        }
    }

    /**
     * Адаптер
     */
    private class ThemeColorAdapter extends RecyclerView.Adapter<ThemeColorHolder> {

        private List<Integer> colors;

        ThemeColorAdapter(List<Integer> colors) {
            this.colors = colors;
        }

        @Override
        public ThemeColorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ThemeColorHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(final ThemeColorHolder holder, int position) {
            Integer color = colors.get(position);
            holder.bind(color);
        }

        @Override
        public int getItemCount() {
            return colors.size();
        }
    }
}
