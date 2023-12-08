package com.cs407.llamalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class ProfileActivity extends AppCompatActivity {
    void changeActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        LlamaProfile[] llamas = {
                new LlamaProfile(0, "Llama 1"),
                new LlamaProfile(1, "Llama 2"),
                new LlamaProfile(2, "Llama 3"),
                new LlamaProfile(3, "Llama 4"),
        };

        LlamaProfileAdapter adapter = new LlamaProfileAdapter(this, llamas, () -> changeActivity());

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}