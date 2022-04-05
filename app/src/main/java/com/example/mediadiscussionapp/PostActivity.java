package com.example.mediadiscussionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mediadiscussionapp.Models.Posts;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostActivity extends AppCompatActivity {

    EditText ET_uname, ET_tag, ET_title, ET_pc;
    ImageView IV_Push;
    Posts posts;
    boolean isOldPost = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        IV_Push = findViewById(R.id.IV_Push);
        ET_uname = findViewById(R.id.ET_uname);
        ET_tag = findViewById(R.id.ET_tag);
        ET_title = findViewById(R.id.ET_title);
        ET_pc = findViewById(R.id.ET_pc);

        posts = new Posts();
        try {
            posts = (Posts) getIntent().getSerializableExtra("old_post");
            ET_uname.setText(posts.getUser());
            ET_tag.setText(posts.getTag());
            ET_title.setText(posts.getTitle());
            ET_pc.setText(posts.getP_content());

            isOldPost = true;
        }catch (Exception e) {
            e.printStackTrace();
        }

        IV_Push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = ET_uname.getText().toString();
                String tag = ET_tag.getText().toString();
                String title = ET_title.getText().toString();
                String content = ET_pc.getText().toString();

                if (user.isEmpty() || tag.isEmpty() || title.isEmpty()){
                    Toast.makeText(PostActivity.this, "Username, post type or title are missing", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy");
                Date date = new Date();

                if (!isOldPost){
                    posts = new Posts();
                }

                posts.setUser(user);
                posts.setTag(tag);
                posts.setTitle(title);
                posts.setP_content(content);
                posts.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("post", posts);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}