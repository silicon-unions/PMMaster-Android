package com.codesignet.pmp.practices.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codesignet.pmp.BuildConfig;
import com.codesignet.pmp.R;
import com.codesignet.pmp.practices.ui.view_holder.LevelViewHolder;
import com.codesignet.pmp.practices.view.PracticesView;

import java.util.ArrayList;

public class LevelAdapter extends RecyclerView.Adapter<LevelViewHolder> {

    private PracticesView listeners;
    private ArrayList<String> level;
    private String levelData;
    private float currentLevelData;

    public LevelAdapter(PracticesView listeners, ArrayList<String> questionsList, String levelData, float curentLeveData) {
        this.listeners = listeners;
        this.level = questionsList;
        this.levelData = levelData;
        this.currentLevelData = curentLeveData;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_level_item, parent, false);
        return new LevelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {
        String title = "Level " + level.get(position);
        holder.level_number.setText(title);
        if (Integer.valueOf(levelData) > Integer.valueOf(level.get(position))) {
            holder.level_progress.setPercentage(360);
            holder.lock.setVisibility(View.INVISIBLE);
            holder.level_progress.setStepCountText("100");
            holder.level_question.setText("You have successfully completed this level.");
            holder.level_number.setTextColor(Color.parseColor("#000000"));
        } else if (Integer.valueOf(levelData) == Integer.valueOf(level.get(position))) {
            if (BuildConfig.FLAVOR.equals("Free") && Integer.valueOf(levelData) != 1) {
                holder.level_question.setText("Upgrade to have access to this level.");
                holder.lock.setVisibility(View.VISIBLE);
                holder.level_progress.setPercentage(0);
                holder.level_number.setTypeface(holder.level_question.getTypeface(), Typeface.ITALIC);
                holder.level_number.setTextColor(Color.parseColor("#808caa"));
            } else {
                holder.lock.setVisibility(View.INVISIBLE);
                holder.level_progress.setPercentage((int) (currentLevelData * 3.6));
                holder.level_progress.setStepCountText(String.valueOf(currentLevelData));
                holder.level_question.setText("This is your current level");
                holder.level_number.setTextColor(Color.parseColor("#808caa"));
            }
        } else {
            if (BuildConfig.FLAVOR.equals("Free") && Integer.valueOf(level.get(position)) > 1)
                holder.level_question.setText("Upgrade to have access to this level.");
            else
                holder.level_question.setText("This level is locked.");
            holder.lock.setVisibility(View.VISIBLE);
            holder.level_progress.setPercentage(0);
            holder.level_number.setTypeface(holder.level_question.getTypeface(), Typeface.ITALIC);
            holder.level_number.setTextColor(Color.parseColor("#808caa"));
        }
        holder.itemView.setOnClickListener(v -> {
            listeners.onLevelClicked(Integer.valueOf(level.get(position)));
        });
    }

    @Override
    public int getItemCount() {
        return level.size();
    }
}
