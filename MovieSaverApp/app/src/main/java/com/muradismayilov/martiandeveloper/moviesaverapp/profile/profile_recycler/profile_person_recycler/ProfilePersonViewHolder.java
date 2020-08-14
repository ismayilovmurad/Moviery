package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_recycler.profile_person_recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muradismayilov.martiandeveloper.moviesaverapp.R;

class ProfilePersonViewHolder extends RecyclerView.ViewHolder {

    final ImageView profileIV;
    final TextView nameTV;

    ProfilePersonViewHolder(@NonNull View itemView) {
        super(itemView);

        profileIV = itemView.findViewById(R.id.profileIV);
        nameTV = itemView.findViewById(R.id.nameTV);
    }
}

