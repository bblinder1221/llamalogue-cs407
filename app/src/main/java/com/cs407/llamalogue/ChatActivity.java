package com.cs407.llamalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    ChatAdapter chatAdapter;
    List<Message> messages = new ArrayList<>();
    String name;
    String systemPrompt;
    String imgSrc;
    private FirebaseAuth mAuth;

    class PostTask extends AsyncTask<String, Void, String> {
        Message thinking = new Message("...", 1);

        private String makeRequest(String data) {
            String urlString = "https://llama-logue.netlify.app/api/prompt";

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] body = data.getBytes("utf-8");
                    os.write(body, 0, body.length);
                }

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line.trim());
                }

                JSONObject jsonObject = new JSONObject(response.toString());

                String responseText = jsonObject.getString("msg");

                return responseText;
            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPreExecute() {
            chatAdapter.add(thinking);
        }

        @Override
        protected String doInBackground(String... params) {
            return makeRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            chatAdapter.remove(thinking);
            Message msg = new Message(result, 1);

            chatAdapter.add(msg);
            chatAdapter.notifyDataSetChanged();

            System.out.println(msg.text);
        }
    }

    private void runPrompt(String promptText) {
        if (promptText.isEmpty()) {
            return;
        }

        String data = "{\"prompt\": \"" + promptText + "\", \"system_prompt\": \"" + systemPrompt + "\"}";
        PostTask postTask = new PostTask();
        postTask.execute(data);
    }

    private void changeActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        systemPrompt = intent.getStringExtra("system_prompt");
        imgSrc = intent.getStringExtra("imgSrc");

        TextView textView = findViewById(R.id.llama_name_header);
        textView.setText(name);

        ImageView imageView = findViewById(R.id.llama_avatar_header);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity();
            }
        });

        switch (imgSrc) {
            case "jock_llama.jpg":
                imageView.setImageResource(R.drawable.jock_llama);
                break;
            case "emo_llama.jpg":
                imageView.setImageResource(R.drawable.emo_llama);
                break;
            case "evil_llama.jpg":
                imageView.setImageResource(R.drawable.evil_llama);
                break;
            default:
                imageView.setImageResource(R.drawable.happy_llama);
                break;
        }

        chatAdapter = new ChatAdapter(this, messages);

        ListView listView = findViewById(R.id.messages);
        listView.setAdapter(chatAdapter);

        EditText prompt = findViewById(R.id.et_message);
        Button send = findViewById(R.id.btn_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String promptText = prompt.getText().toString();
                if (promptText.isEmpty()) {
                    return;
                }

                Message msg = new Message(promptText, 0);

                chatAdapter.add(msg);
                chatAdapter.notifyDataSetChanged();

                runPrompt(promptText);

                prompt.setText("");
            }
        });

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