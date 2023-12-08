package com.cs407.llamalogue;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

interface ChangeActivity {
    void changeActivity(LlamaProfile profile);
}

public class LlamaProfileAdapter extends ArrayAdapter<LlamaProfile> {
    ChangeActivity changeActivity;

    LlamaProfileAdapter(Activity context, LlamaProfile[] llamas, ChangeActivity changeIntent) {
        super(context, R.layout.llama_profile, llamas);
        this.changeActivity = changeIntent;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.llama_profile, parent, false);
        }

        LlamaProfile llama = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        if (llama != null) {
            textView.setText(llama.name);

            switch (llama.imgSrc) {
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
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity.changeActivity(llama);
            }
        });

        return convertView;
    }
}
