package com.bignerdranch.android.remindernote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//Fragment of Main Activity
//List of Notes
public class ListFragment extends Fragment {
    private ListView LNotes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_list,container,false);
        LNotes = (ListView) v.findViewById(R.id.list);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        LNotes.setAdapter(null);
        ArrayList<Note> note = Function.getListNotes(getActivity());

        //Arrange list of note following date and time
        Collections.sort(note, new Comparator<Note>() {
            @Override
            public int compare(Note x, Note y) {
                if(x.getDateTime() < y.getDateTime()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        //If list is not empty,load the note information if user clicks on the note.
        if(note.size() > 0) {
            final NoteInfo list = new NoteInfo(getActivity(), R.layout.info_note, note);
            LNotes.setAdapter(list);

            LNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View v, int w, long id) {

                    String NoteTitle = ((Note) LNotes.getItemAtPosition(w)).getDateTime()
                            + Function.FILE_EXTENSION;
                    Intent i = new Intent(getActivity(), NoteActivity.class);
                    i.putExtra(Function.EXTRAS_NOTE_FILENAME, NoteTitle);
                    startActivity(i);
                }
            });
        }
    }

}

