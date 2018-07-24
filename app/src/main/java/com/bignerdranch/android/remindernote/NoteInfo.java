package com.bignerdranch.android.remindernote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

//Arrange Information Shown in The List.
public class NoteInfo extends ArrayAdapter<Note> {

    public NoteInfo(Context context, int a, List<Note> items) {
        super(context, a, items);
    }

    @Override
    public View getView(int b, View v, ViewGroup z) {

        if(v == null) {
            v = LayoutInflater.from(getContext())
                    .inflate(R.layout.info_note, null);
        }

        Note note = getItem(b);

        if(note != null) {
            TextView datetime = (TextView) v.findViewById(R.id.datetime);
            TextView title = (TextView) v.findViewById(R.id.title);

            title.setText(note.getTitle());
            datetime.setText(note.getDateTimeFormatted(getContext()));
        }

        return v;
    }
}
