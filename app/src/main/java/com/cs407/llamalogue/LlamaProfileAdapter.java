package com.cs407.llamalogue;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LlamaProfileAdapter extends ArrayAdapter<LlamaProfile> {
    LlamaProfileAdapter(Activity context, LlamaProfile[] llamas) {
        super(context, R.layout.llama_profile, llamas);
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

        return convertView;
    }
}
