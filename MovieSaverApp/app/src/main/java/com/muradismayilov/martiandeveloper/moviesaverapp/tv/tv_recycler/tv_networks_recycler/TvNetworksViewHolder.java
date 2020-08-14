package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_networks_recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muradismayilov.martiandeveloper.moviesaverapp.R;

class TvNetworksViewHolder extends RecyclerView.ViewHolder {

    final LinearLayout mainLL;
    final ImageView logoIV;
    final TextView networkNameTV;

    TvNetworksViewHolder(@NonNull View itemView) {
        super(itemView);

        mainLL = itemView.findViewById(R.id.mainLL);
        logoIV = itemView.findViewById(R.id.logoIV);
        networkNameTV = itemView.findViewById(R.id.networkNameTV);
    }
}
