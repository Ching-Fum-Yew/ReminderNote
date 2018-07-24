package com.bignerdranch.android.remindernote;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private boolean ViewOrEdit;
    private long DateTime;
    private String NoteTitle;
    private Note LoadNote = null;

    private EditText Title;
    private EditText Content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Title = (EditText) findViewById(R.id.title);
        Content = (EditText) findViewById(R.id.content);


        NoteTitle = getIntent().getStringExtra(Function.EXTRAS_NOTE_FILENAME);

        //User Edits The Note
        if(NoteTitle != null && !NoteTitle.isEmpty() && NoteTitle.endsWith(Function.FILE_EXTENSION)) {
            LoadNote = Function.getLoadNote(getApplicationContext(), NoteTitle);
            if (LoadNote != null) {
                Title.setText(LoadNote.getTitle());
                Content.setText(LoadNote.getContent());
                DateTime = LoadNote.getDateTime();
                ViewOrEdit = true;
            }

        }
        //User Add A New Note
        else {
            DateTime = System.currentTimeMillis();
            ViewOrEdit = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate Option Menu for View or Edit
        if(ViewOrEdit) {
            getMenuInflater().inflate(R.menu.menu_view_edit, menu);
        }
        //Inflate Option Menu for Add Note
        else {
            getMenuInflater().inflate(R.menu.menu_save, menu);
        }
        return true;
    }

    @Override
    //Perform Operation If Option Menu Is Click
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //Save Note
            case R.id.save:
                Save();
                break;

            //Delete Note
            case R.id.delete:
                Delete();
                break;

            //Return to List
            case R.id.back:
                Back();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //Delete Function
    private void Delete() {
        //Get User Permission to Delete Note
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Do you want to delete this note?")

                //Delete Note If User Click Yes
                .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int c) {
                        if(LoadNote != null && Function.deleteNote(getApplicationContext(), NoteTitle)) {
                            Toast.makeText(NoteActivity.this, LoadNote.getTitle() + " is deleted."
                                    , Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NoteActivity.this, "Unable to delete the note '" + LoadNote.getTitle() + "'"
                                    , Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                })

                //User Click No
                .setPositiveButton("NO", null);

        dialogDelete.show();
    }

    //Back Function
    private void Back() {
            finish();
    }

    //Save Function
    private void Save() {

        String title = Title.getText().toString();
        String content = Content.getText().toString();

        //Check User Input For Title And Content
        if(title.isEmpty()) {
            Toast.makeText(NoteActivity.this, "Please enter title!"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        if(content.isEmpty()) {
            Toast.makeText(NoteActivity.this, "Please enter content!"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        //Date and Time For Old Note Remain The Same
        if(LoadNote != null) {
            DateTime = LoadNote.getDateTime();
        }
        //Set Date and Time For New Note
        else {
            DateTime = System.currentTimeMillis();
        }

        //Save Note
        if(Function.saveNote(this, new Note(DateTime, title, content))) {
            Toast.makeText(this, "Note has been successfully saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unable to save the note " , Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
