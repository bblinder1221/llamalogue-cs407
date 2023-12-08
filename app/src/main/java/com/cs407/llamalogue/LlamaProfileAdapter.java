package com.cs407.llamalogue;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

interface ChangeActivity {
    void changeActivity();
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

//        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        if (llama != null) {
            textView.setText(llama.name);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity.changeActivity();
            }
        });

        return convertView;
    }
}
