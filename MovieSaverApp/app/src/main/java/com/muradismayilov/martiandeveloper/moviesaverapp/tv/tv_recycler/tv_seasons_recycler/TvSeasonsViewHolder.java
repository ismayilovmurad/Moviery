package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_seasons_recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.muradismayilov.martiandeveloper.moviesaverapp.R;

class TvSeasonsViewHolder extends RecyclerView.ViewHolder {

    final ConstraintLayout mainCL;
    final ImageView posterIV;
    final TextView nameTV;
    final TextView dateTV;

    TvSeasonsViewHolder(@NonNull View itemView) {
        super(itemView);

        mainCL = itemView.findViewById(R.id.mainCL);
        posterIV = itemView.findViewById(R.id.posterIV);
        nameTV = itemView.findViewById(R.id.nameTV);
        dateTV = itemView.findViewById(R.id.dateTV);
    }
}
