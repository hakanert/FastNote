package com.fastnote.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.fastnote.app.utils.DatabaseHelper;
import com.fastnote.app.interfaces.EmptyCheck;
import com.fastnote.app.adapters.NotesAdapter;
import com.fastnote.app.models.NotesModel;
import com.fastnote.app.R;
import com.fastnote.app.databinding.FragmentNotesBinding;

import java.util.ArrayList;
import java.util.Collections;

public class NotesFragment extends Fragment implements EmptyCheck {

    private FragmentNotesBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentNotesBinding.inflate(inflater, container, false);

        F_GetList();

        return binding.getRoot();

    }
    void F_GetList() {
        DatabaseHelper db = new DatabaseHelper(getContext());
        ArrayList<NotesModel> noteList = db.getNoteList();

        if (noteList.size() == 0){
            binding.empty.setVisibility(View.VISIBLE);
        }else{
            binding.empty.setVisibility(View.GONE);

            Collections.reverse(noteList);


            NavHostFragment navHostFragment =(NavHostFragment) requireActivity().getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment_content_main);

            if (navHostFragment != null) {
                NavController navController = navHostFragment.getNavController();
                NotesAdapter adp = new NotesAdapter(getContext(), noteList,navController,this);



                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                binding.recycle.setLayoutManager(staggeredGridLayoutManager);
                binding.recycle.setHasFixedSize(true);
                binding.recycle.setAdapter(adp);

                db.close();
            }
        }

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void empty() {
        binding.empty.setVisibility(View.VISIBLE);
    }
}