package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_recycler.profile_tv_recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muradismayilov.martiandeveloper.moviesaverapp.R;

class ProfileTvViewHolder extends RecyclerView.ViewHolder {

    final ImageView posterIV;
    final TextView titleTV;
    final TextView yearTV;

    ProfileTvViewHolder(@NonNull View itemView) {
        super(itemView);

        posterIV = itemView.findViewById(R.id.posterIV);
        titleTV = itemView.findViewById(R.id.titleTV);
        yearTV = itemView.findViewById(R.id.yearTV);
    }
}
