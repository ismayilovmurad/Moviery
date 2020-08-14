package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_created_by_recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.muradismayilov.martiandeveloper.moviesaverapp.R;

class TvCreatedByViewHolder extends RecyclerView.ViewHolder {

    final ConstraintLayout mainCL;
    final ImageView profileIV;
    final TextView nameTV;

    TvCreatedByViewHolder(@NonNull View itemView) {
        super(itemView);

        mainCL = itemView.findViewById(R.id.mainCL);
        profileIV = itemView.findViewById(R.id.profileIV);
        nameTV = itemView.findViewById(R.id.nameTV);
    }
}
