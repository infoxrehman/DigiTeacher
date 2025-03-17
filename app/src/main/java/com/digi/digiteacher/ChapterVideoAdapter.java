package com.digi.digiteacher;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.digi.digiteacher.ChapterVideoModel;
import com.digi.digiteacher.R;
import java.util.List;

public class ChapterVideoAdapter extends RecyclerView.Adapter<ChapterVideoAdapter.ViewHolder> {

    private final List<ChapterVideoModel> chapterVideos;
    private final Context mContext;

    public ChapterVideoAdapter(List<ChapterVideoModel> chapterVideos, Context context) {
        this.chapterVideos = chapterVideos;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChapterVideoModel video = chapterVideos.get(position);
        String videoUrl = video.getAv_url();

        holder.webView.loadUrl(videoUrl);
        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.getSettings().setDomStorageEnabled(true);
        holder.webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public int getItemCount() {
        return chapterVideos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        WebView webView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.video_view);
        }
    }
}
