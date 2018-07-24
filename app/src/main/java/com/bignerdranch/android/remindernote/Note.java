package com.bignerdranch.android.remindernote;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

//Model Note
public class Note implements Serializable {
    private long DateTime;
    private String Title;
    private String Content;

    public Note(long datetime, String title, String content) {
        DateTime = datetime;
        Title = title;
        Content = content;
    }

    //Getter and Setter
    public void setDateTime(long datetime) {
        DateTime = datetime;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public long getDateTime() {
        return DateTime;
    }

    //Get Date and Time formatted
    public String getDateTimeFormatted(Context context) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"
                , context.getResources().getConfiguration().locale);
        f.setTimeZone(TimeZone.getDefault());
        return f.format(new Date(DateTime));
    }
}
