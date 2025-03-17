package com.digi.digiteacher;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<CourseModel> courses = new ArrayList<>();
    String token;
    private Context mContext;

    public void setCourses(Context context,List<CourseModel> courses) {
        mContext = context;
        this.courses = courses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CourseModel course = courses.get(position);
        String id = course.getSu_id();
        String name = course.getSu_name();
        String desc = course.getDescription();
        String image = course.getBook_image();

        holder.courseName.setText(name);
        holder.courseDescription.setText(desc);

        try {
            Glide.with(mContext).load(image).placeholder(R.drawable.image_src).into(holder.courseImage);
        } catch (Exception e) {
        }

        holder.goBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChaptersActivity.class);
                intent.putExtra("sbId", id);
                intent.putExtra("sbName", name);
                intent.putExtra("sbDescription", desc);
                intent.putExtra("sbImage", image);
                intent.putExtra("token",token);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        TextView courseDescription;
        ShapeableImageView courseImage;
        ImageView goBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName);
            courseDescription = itemView.findViewById(R.id.description);
            courseImage = itemView.findViewById(R.id.courseImage);
            goBtn = itemView.findViewById(R.id.goBtn);
        }
    }
}
