package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_main_recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.muradismayilov.martiandeveloper.moviesaverapp.R;

class TvMainViewHolder extends RecyclerView.ViewHolder {

    final ConstraintLayout mainCL;
    final ImageView posterIV;
    final TextView titleTV, rateTV, yearTV;

    TvMainViewHolder(@NonNull View itemView) {
        super(itemView);

        mainCL = itemView.findViewById(R.id.mainCL);
        posterIV = itemView.findViewById(R.id.posterIV);
        titleTV = itemView.findViewById(R.id.titleTV);
        rateTV = itemView.findViewById(R.id.rateTV);
        yearTV = itemView.findViewById(R.id.yearTV);
    }
}
