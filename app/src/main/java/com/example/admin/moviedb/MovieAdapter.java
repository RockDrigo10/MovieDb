package com.example.admin.moviedb;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.moviedb.Model.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String MOVIES_IMG = "https://image.tmdb.org/t/p/w500/";
    List<Result> resultList = new ArrayList<>();
    Context context;
    private int lastPosition = -1;

    public MovieAdapter(List<Result> resultList) {
        this.resultList = resultList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvText, tvText2, tvText3, tvText5;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            tvText2 = (TextView) itemView.findViewById(R.id.tvText2);
            tvText3 = (TextView) itemView.findViewById(R.id.tvText3);
            tvText5 = (TextView) itemView.findViewById(R.id.tvText5);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Result item = resultList.get(position);
        holder.tvText.setText(item.getTitle());
        holder.tvText3.setText(String.valueOf(item.getVoteAverage()));
        holder.tvText2.setText(item.getReleaseDate());
        holder.tvText5.setText(item.getOriginalLanguage());
        Picasso.with(context).load(MOVIES_IMG + item.getPosterPath()).into(holder.ivImage);
        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialogCustom = new Dialog(view.getContext());
                dialogCustom.setContentView(R.layout.moviesdetail);
                ImageView ivDetail = (ImageView) dialogCustom.findViewById(R.id.ivImage);
                TextView tvText = (TextView) dialogCustom.findViewById(R.id.tvText);
                TextView tvText2 = (TextView) dialogCustom.findViewById(R.id.tvText2);
                TextView tvText3 = (TextView) dialogCustom.findViewById(R.id.tvText3);
                TextView tvText4 = (TextView) dialogCustom.findViewById(R.id.tvText4);
                TextView tvText5 = (TextView) dialogCustom.findViewById(R.id.tvText5);
                tvText.setText(item.getTitle());
                tvText2.setText(item.getReleaseDate());
                tvText3.setText(String.valueOf(item.getVoteAverage()));
                tvText4.setText(item.getOriginalLanguage());
                tvText5.setText(item.getOverview());
                Picasso.with(view.getContext()).load(MOVIES_IMG + item.getPosterPath()).into(ivDetail);
                dialogCustom.show();
            }
        });

    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.ZORDER_BOTTOM, 0.5f, Animation.ZORDER_BOTTOM, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }


}
