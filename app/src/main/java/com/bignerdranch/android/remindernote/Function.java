package com.bignerdranch.android.remindernote;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Function {
    public static final String EXTRAS_NOTE_FILENAME = "EXTRAS_NOTE_FILENAME";
    public static final String FILE_EXTENSION = ".bin";

    //Save Note to Storage
    public static boolean saveNote(Context context, Note note) {

        String NoteTitle = String.valueOf(note.getDateTime()) + FILE_EXTENSION;

        FileOutputStream FOS;
        ObjectOutputStream OOS;

        try{
            FOS = context.openFileOutput(NoteTitle, context.MODE_PRIVATE);
            OOS= new ObjectOutputStream(FOS);
            OOS.writeObject(note);
            OOS.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    //Read The Saved Notes and Add To List
    static ArrayList<Note> getListNotes(Context context) {
        ArrayList<Note> notes = new ArrayList<>();

        File filesDir = context.getFilesDir();
        ArrayList<String> noteFiles = new ArrayList<>();

        for(String file : filesDir.list()) {
            if(file.endsWith(FILE_EXTENSION)) {
                noteFiles.add(file);
            }
        }


        FileInputStream FIS;
        ObjectInputStream OIS;

        for (int x = 0; x < noteFiles.size(); x++) {
            try{
                FIS = context.openFileInput(noteFiles.get(x));
                OIS = new ObjectInputStream(FIS);

                notes.add((Note) OIS.readObject());
                FIS.close();
                OIS.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        return notes;
    }


    //Load Note From Storage
    public static Note getLoadNote(Context context, String NoteTitle) {

        File file = new File(context.getFilesDir(), NoteTitle);
        if(file.exists() && !file.isDirectory()) {

            Log.w("UTILITIES", "File exist = " + NoteTitle);

            FileInputStream FIS;
            ObjectInputStream OIS;

            try {
                FIS = context.openFileInput(NoteTitle);
                OIS = new ObjectInputStream(FIS);
                Note note = (Note) OIS.readObject();
                FIS.close();
                OIS.close();

                return note;

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }
    }

    //Delete Note From Storage
    public static boolean deleteNote(Context context, String NoteTitle) {
        File dirFiles = context.getFilesDir();
        File file = new File(dirFiles, NoteTitle);

        if(file.exists() && !file.isDirectory()) {
            return file.delete();
        }

        return false;
    }
}
