package com.cs407.llamalogue;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class ChatAdapter extends ArrayAdapter<Message> {

    ChatAdapter(Activity context, List<Message> msgs) {
        super(context, R.layout.llama_profile, msgs);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.message_item, parent, false);
        }

        Message msg = getItem(position);


        if (msg != null) {
            if (msg.id == 1) {
                TextView textView = convertView.findViewById(R.id.bot);
                textView.setText(msg.text);

                TextView otherView = convertView.findViewById(R.id.user);
                otherView.setVisibility(View.INVISIBLE);
            }

            else {
                TextView textView = convertView.findViewById(R.id.user);
                textView.setText(msg.text);

                TextView otherView = convertView.findViewById(R.id.bot);
                otherView.setVisibility(View.INVISIBLE);
            }
        }

        else {
            System.out.println("NULL");
        }

        return convertView;
    }
}
