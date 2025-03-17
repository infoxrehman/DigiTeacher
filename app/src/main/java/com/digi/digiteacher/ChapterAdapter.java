package com.digi.digiteacher;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

    private List<ChapterModel> chapterModelArrayList = new ArrayList<>();
    private Context context;

    public ChapterAdapter(List<ChapterModel> chapterModel, Context context) {
        this.chapterModelArrayList = chapterModel;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChapterModel chapterModel = chapterModelArrayList.get(position);
        String id = chapterModel.getFk_subject_id();
        String ch_name = chapterModel.getCh_name();
        String sb_name = chapterModel.getSu_name();
        String ch_id = chapterModel.getCh_id();

        holder.chapterName.setText((position + 1) + ".  " + ch_name);

        int colorResId = position % 2 == 0 ? R.color.evenColor : R.color.oddColor;
        holder.itemView.setBackgroundResource(colorResId);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChapterVideoActivity.class);
                intent.putExtra("sbId", id);
                intent.putExtra("chName", ch_name);
                intent.putExtra("sbName", sb_name);
                intent.putExtra("ch_id", ch_id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chapterModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chapterName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterName = itemView.findViewById(R.id.chapterName);
        }
    }
}
