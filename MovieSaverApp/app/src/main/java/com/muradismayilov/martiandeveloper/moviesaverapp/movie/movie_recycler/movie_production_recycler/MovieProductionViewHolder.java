package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_recycler.movie_production_recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muradismayilov.martiandeveloper.moviesaverapp.R;

class MovieProductionViewHolder extends RecyclerView.ViewHolder {

    final LinearLayout mainLL;
    final ImageView logoIV;
    final TextView productionNameTV;

    MovieProductionViewHolder(@NonNull View itemView) {
        super(itemView);

        mainLL = itemView.findViewById(R.id.mainLL);
        logoIV = itemView.findViewById(R.id.logoIV);
        productionNameTV = itemView.findViewById(R.id.productionNameTV);
    }
}
