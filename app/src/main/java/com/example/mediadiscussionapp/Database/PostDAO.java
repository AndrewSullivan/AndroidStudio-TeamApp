package com.example.mediadiscussionapp.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mediadiscussionapp.Models.Posts;

import java.util.List;

@Dao
public interface PostDAO {

    @Insert(onConflict = REPLACE)
    void insert(Posts posts);

    @Query("SELECT * FROM posts ORDER BY id DESC")
    List<Posts> getAll();

    @Query("UPDATE posts SET uname = :uname,  tag = :tag, title = :title, p_content = :p_content WHERE ID =:id")
    void update(int id, String uname, String tag, String title, String p_content);

    @Delete
    void delete(Posts posts);
}
