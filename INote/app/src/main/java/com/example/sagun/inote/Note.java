package com.example.sagun.inote;

public class Note {
    private int noteID;
    private String noteText;
    private String noteDateTime;

    public Note(String noteText){
        this.noteText = noteText;
    }

    public Note(String noteText, String noteDateTime) {
        this.noteText = noteText;
        this.noteDateTime = noteDateTime;
    }

    public Note(int noteID, String noteText, String noteDateTime) {
        this.noteID = noteID;
        this.noteText = noteText;
        this.noteDateTime = noteDateTime;
    }

    public int getNoteID() {return  noteID; }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteDateTime() {
        return noteDateTime;
    }

    public void setNoteDateTime(String noteDateTime) {
        this.noteDateTime = noteDateTime;
    }
}
