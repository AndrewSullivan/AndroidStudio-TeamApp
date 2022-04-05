package com.example.mediadiscussionapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediadiscussionapp.Models.Posts;
import com.example.mediadiscussionapp.PostsClickListener;
import com.example.mediadiscussionapp.R;

import java.util.List;


public class PostsListAdapter extends RecyclerView.Adapter<PostsViewHolder>{

    Context context;
    List<Posts> list;
    PostsClickListener listener;

    public PostsListAdapter(Context context, List<Posts> list, PostsClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostsViewHolder(LayoutInflater.from(context).inflate(R.layout.posts_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {

        holder.tv_uname.setText(list.get(position).getUser());
        holder.tv_uname.setSelected(true);

        holder.tv_tag.setText(list.get(position).getTag());
        holder.tv_tag.setSelected(true);

        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_title.setSelected(true);

        holder.tv_pc.setText(list.get(position).getP_content());

        holder.tv_date.setText(list.get(position).getDate());
        holder.tv_date.setSelected(true);

        holder.posts_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.posts_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.posts_container);
                return true;
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Posts> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }
}
class PostsViewHolder extends RecyclerView.ViewHolder {

    CardView posts_container;
    TextView tv_uname, tv_tag, tv_title, tv_pc, tv_date, tv_comments;

    public PostsViewHolder(@NonNull View itemView) {
        super(itemView);
        posts_container = itemView.findViewById(R.id.posts_container);
        tv_uname = itemView.findViewById(R.id.tv_uname);
        tv_tag = itemView.findViewById(R.id.tv_tag);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_pc = itemView.findViewById(R.id.tv_pc);
        tv_date = itemView.findViewById(R.id.tv_date);
        tv_comments = itemView.findViewById(R.id.tv_comments);
    }
}
