package com.cs407.llamalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    void changeActivity(LlamaProfile llama) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("name", llama.name);
        intent.putExtra("system_prompt", llama.systemPrompt);
        intent.putExtra("imgSrc", llama.imgSrc);
        startActivity(intent);
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();


        LlamaProfile[] llamas = {
                new LlamaProfile(0, "Happy", "happy_llama.jpg", ""),
                new LlamaProfile(1, "Sporty", "jock_llama.jpg", "Pretend you are a jock"),
                new LlamaProfile(2, "Emo", "emo_llama.jpg", "Pretend you are an emo person but are not sad"),
                new LlamaProfile(3, "Evil", "evil_llama.jpg", "Give the exact opposite answer"),
        };

        LlamaProfileAdapter adapter = new LlamaProfileAdapter(this, llamas, (LlamaProfile profile) -> changeActivity(profile));

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);


        Button signout = findViewById(R.id.sign_out_Button);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                goToLogin();
            }
        });
    }
}