package com.cs407.llamalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class ProfileActivity extends AppCompatActivity {
    void changeActivity(LlamaProfile llama) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("name", llama.name);
        intent.putExtra("system_prompt", llama.systemPrompt);
        intent.putExtra("imgSrc", llama.imgSrc);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        LlamaProfile[] llamas = {
                new LlamaProfile(0, "Happy", "happy_llama.jpg", ""),
                new LlamaProfile(1, "Sporty", "jock_llama.jpg", "Pretend you are a jock"),
                new LlamaProfile(2, "Emo", "emo_llama.jpg", "Pretend you are an emo person but are not sad"),
                new LlamaProfile(3, "Evil", "evil_llama.jpg", "Give the exact opposite answer"),
        };

        LlamaProfileAdapter adapter = new LlamaProfileAdapter(this, llamas, (LlamaProfile profile) -> changeActivity(profile));

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}