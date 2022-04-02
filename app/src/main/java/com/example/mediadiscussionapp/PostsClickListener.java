package com.example.mediadiscussionapp;

import androidx.cardview.widget.CardView;

import com.example.mediadiscussionapp.Models.Posts;

public interface PostsClickListener {
    void onClick(Posts posts);
    void onLongClick(Posts posts, CardView cardView);
}
