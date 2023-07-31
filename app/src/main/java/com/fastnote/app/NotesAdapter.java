package com.fastnote.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    Context context;
    List<NotesModel> list;
    NavController navController;
    DatabaseHelper db;

    EmptyCheck emptyCheck;
    public NotesAdapter(Context context, List<NotesModel> list, NavController navController,EmptyCheck emptyCheck) {
        db = new DatabaseHelper(context);
        this.context = context;
        this.navController = navController;
        this.list = list;
        this.emptyCheck = emptyCheck;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        NotesModel n = list.get(position);
        holder.date.setText(n.getDate());
        holder.note.setText(n.getText());
        holder.title.setText(n.getTitle());


        holder.card.setOnLongClickListener(v -> {
            new MaterialAlertDialogBuilder(context)
                    .setMessage(context.getString(R.string.sure_delete))
                    .setPositiveButton(context.getText(R.string.delete), (dialog, which) -> {
                        db.deleteNote(n.getId());
                        notifyItemRemoved(position);
                        list.remove(position);
                        notifyItemRangeChanged(position, getItemCount());

                        if (list.size() == 0){
                            emptyCheck.empty();
                        }
                    })
                    .setNegativeButton(context.getString(R.string.cancel), (DialogInterface.OnClickListener) (dialog, which) -> dialog.dismiss())
                    .show();
            return true;
        });

        holder.card.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", n.getTitle());
            bundle.putString("note", n.getText());
            bundle.putString("date", n.getDate());
            bundle.putString("id", n.getId());
            navController.navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView note,date,title;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            note = itemView.findViewById(R.id.note);
            date = itemView.findViewById(R.id.date);
            title = itemView.findViewById(R.id.title);
        }
    }
}
