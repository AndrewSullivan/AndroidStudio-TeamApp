package com.example.mediadiscussionapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mediadiscussionapp.Adapters.PostsListAdapter;
import com.example.mediadiscussionapp.Database.PostDAO;
import com.example.mediadiscussionapp.Database.RoomDB;
import com.example.mediadiscussionapp.Models.Posts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    PostsListAdapter postsListAdapter;
    List<Posts> posts = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;
    SearchView SV_home;
    Posts selectedPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);
        SV_home = findViewById(R.id.SV_home);
        database = RoomDB.getInstance(this);
        posts = database.postDAO().getAll();


        updateRecycler(posts);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        SV_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<Posts> filteredList = new ArrayList<>();
        for (Posts singlePost: posts){
            if(singlePost.getTitle().toLowerCase().contains(newText.toLowerCase())
            || singlePost.getTag().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(singlePost);
            }
        }
        postsListAdapter.filterList(filteredList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            if(resultCode == Activity.RESULT_OK){
                Posts new_posts = (Posts) data.getSerializableExtra("post");
                database.postDAO().insert(new_posts);
                posts.clear();
                posts.addAll(database.postDAO().getAll());
                postsListAdapter.notifyDataSetChanged();
            }
        }
        else if(requestCode == 102){
            if(resultCode == Activity.RESULT_OK){
                Posts new_posts = (Posts) data.getSerializableExtra("post");
                database.postDAO().update(new_posts.getID(), new_posts.getUser(), new_posts.getTag(), new_posts.getTitle(), new_posts.getP_content());
                posts.clear();
                posts.addAll(database.postDAO().getAll());
                postsListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Posts> posts) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        postsListAdapter = new PostsListAdapter(MainActivity.this, posts, postsClickListener);
        recyclerView.setAdapter(postsListAdapter);
    }

    private final PostsClickListener postsClickListener = new PostsClickListener() {
        @Override
        public void onClick(Posts posts) {
            Intent intent = new Intent(MainActivity.this, PostActivity.class);
            intent.putExtra("old_post", posts);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Posts posts, CardView cardView) {
            selectedPost = new Posts();
            selectedPost = posts;
            showPopup(cardView);
        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:

            case R.id.delete:
                database.postDAO().delete(selectedPost);
                posts.remove(selectedPost);
                postsListAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Post Removed", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;

        }
    }
}