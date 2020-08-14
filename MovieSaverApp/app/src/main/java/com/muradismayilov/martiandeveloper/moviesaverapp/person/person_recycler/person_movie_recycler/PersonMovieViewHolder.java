package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_recycler.person_movie_recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.muradismayilov.martiandeveloper.moviesaverapp.R;

class PersonMovieViewHolder extends RecyclerView.ViewHolder {

    final ConstraintLayout mainCL;
    final ImageView posterIV;
    final TextView titleTV;
    final TextView vote_averageTV;
    final TextView releaseDateTV;

    PersonMovieViewHolder(@NonNull View itemView) {
        super(itemView);

        mainCL = itemView.findViewById(R.id.mainCL);
        posterIV = itemView.findViewById(R.id.posterIV);
        titleTV = itemView.findViewById(R.id.titleTV);
        vote_averageTV = itemView.findViewById(R.id.rateTV);
        releaseDateTV = itemView.findViewById(R.id.releaseDateTV);
    }
}
