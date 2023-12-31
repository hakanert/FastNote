package com.fastnote.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fastnote.app.databinding.FragmentNoteaeBinding;


public class NoteAEFragment extends Fragment {

    public FragmentNoteaeBinding binding;

    private static NoteAEFragment instance;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        instance= this;
        binding = FragmentNoteaeBinding.inflate(inflater, container, false);
        if (this.getArguments() != null) {
            String title = this.getArguments().getString("title");
            String note = this.getArguments().getString("note");
            String id = this.getArguments().getString("id");
            String date = this.getArguments().getString("date");

            binding.text.setText(note);
            binding.title.setText(title);
            binding.id.setText(id);
            binding.date.setText(date);
        }

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static NoteAEFragment GetInstance()
    {
        return instance;
    }

}