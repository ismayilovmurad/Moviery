package com.muradismayilov.martiandeveloper.moviesaverapp.search.search_recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.muradismayilov.martiandeveloper.moviesaverapp.R;

class SearchPersonViewHolder extends RecyclerView.ViewHolder {

    final ConstraintLayout mainCL;
    final ImageView posterIV;
    final TextView titleTV;

    SearchPersonViewHolder(@NonNull View itemView) {
        super(itemView);

        mainCL = itemView.findViewById(R.id.mainCL);
        posterIV = itemView.findViewById(R.id.posterIV);
        titleTV = itemView.findViewById(R.id.titleTV);
    }
}
