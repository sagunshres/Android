package com.example.sagun.inote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    Context context;
    ArrayList<Note> arrayList = new ArrayList<>();

    public RecyclerAdapter(ArrayList<Note> arrayList, Context context){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        Note note = arrayList.get(position);
        holder.txtNote.setText(note.getNoteText());

      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(context, EditActivity.class);
              intent.putExtra("NOTE_ID",arrayList.get(position).getNoteID());
              intent.putExtra("NOTE_TEXT", arrayList.get(position).getNoteText());
              context.startActivity(intent);
          }
      });
    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtNote;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtNote = (TextView) itemView.findViewById(R.id.txtNotes);
        }
    }

    public void deleteNote(final int pos) {
        Note note = arrayList.get(pos);
        int id = note.getNoteID();

        if (pos != -1 && pos < arrayList.size()) {
            DBOpenHelper helper = new DBOpenHelper(context);
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                helper.deleteNote(id, db, context);
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //update recyclerview content
            arrayList.remove(pos);
            this.notifyItemRemoved(pos);
            notifyDataSetChanged();
            notifyItemRangeChanged(pos, arrayList.size());
            Log.i("Item Deleted", "deleteNote: " + pos);
            Toast.makeText(context, "You reached me", Toast.LENGTH_SHORT).show();

        }
    }
}
