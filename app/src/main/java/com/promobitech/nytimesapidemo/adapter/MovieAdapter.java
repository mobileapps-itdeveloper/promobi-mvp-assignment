package com.promobitech.nytimesapidemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.promobitech.nytimesapidemo.R;
import com.promobitech.nytimesapidemo.ReadOnWebActivity;
import com.promobitech.nytimesapidemo.classe.NetworkUtils;
import com.promobitech.nytimesapidemo.room.tables.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private List<Movie> mDataset;
    private static Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.txtDisplayTitle)
        TextView txtDisplayTitle;
        @BindView(R.id.displayImage)
        ImageView displayImage;
        @BindView(R.id.txtHeadline)
        TextView txtHeadline;
        @BindView(R.id.txtSummaryShort)
        TextView txtSummaryShort;
        @BindView(R.id.txtPublicationDate)
        TextView txtPublicationDate;
        @BindView(R.id.txtByline)
        TextView txtByline;
        @BindView(R.id.txtDateUpdated)
        TextView txtDateUpdated;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MovieAdapter(Context context, List<Movie> myDataset) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Movie movie = mDataset.get(position);
        holder.txtDisplayTitle.setText(movie.getDisplay_title());
        holder.txtByline.setText(context.getString(R.string.by) + " " + movie.getByline());
        holder.txtDateUpdated.setText(context.getString(R.string.update_on) + " " + movie.getDate_updated());
        holder.txtHeadline.setText(movie.getHeadline());
        holder.txtPublicationDate.setText(context.getString(R.string.publish_on) + " " + movie.getPublication_date());
        holder.txtSummaryShort.setText(movie.getSummary_short());
        /**
         * Image is very small it should not display in full size, still demo purpose it shows in full size.
         * "width": 210,
         "  height": 140
         */
        if (movie.getMultimedia() != null) {
            Glide.with(context).load(movie.getMultimedia().getSrc())
                    .crossFade()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.displayImage);
        }
        holder.itemView.setOnClickListener(v -> {
            if(NetworkUtils.isNetworkConnected(context)) {
                Movie article = mDataset.get(position);
                if (article.getLink() != null && !TextUtils.isEmpty(article.getLink().getUrl())) {
                    Intent intent = new Intent(context, ReadOnWebActivity.class);
                    intent.putExtra("url", article.getLink().getUrl());
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, R.string.message_link_error, Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(context, R.string.message_activie_internet_required, Toast.LENGTH_SHORT).show();
            }

        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}