package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_recycler.profile_expand_tv_recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_tools.ProfileItemClickListener;

class ProfileExpandTvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    final ImageView posterIV;
    final TextView titleTV, yearTV;
    final ConstraintLayout mainCL;

    private ProfileItemClickListener itemClickListener;

    ProfileExpandTvViewHolder(@NonNull View itemView) {
        super(itemView);

        posterIV = itemView.findViewById(R.id.posterIV);
        titleTV = itemView.findViewById(R.id.titleTV);
        yearTV = itemView.findViewById(R.id.yearTV);
        mainCL = itemView.findViewById(R.id.mainCL);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    void setItemClickListener(ProfileItemClickListener ic) {
        this.itemClickListener = ic;
    }
}
