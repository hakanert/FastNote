package com.fastnote.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.fastnote.app.databinding.ActivityMainBinding;
import com.fastnote.app.fragments.NoteAEFragment;
import com.fastnote.app.utils.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setImageDrawable(getDrawable(R.drawable.round_add));

        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.NotesFragment){
                binding.fab.setImageDrawable(getDrawable(R.drawable.round_add));
            }else {
                binding.fab.setImageDrawable(getDrawable(R.drawable.round_save));
            }
        });



        binding.fab.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.NotesFragment){
                navController.navigate(R.id.action_NotesFragment_to_NoteAEFragment);
            }else{

                NoteAEFragment f = NoteAEFragment.GetInstance();

                if (f != null){
                    String title = f.binding.title.getText().toString();
                    String note = f.binding.text.getText().toString();
                    String id = f.binding.id.getText().toString();
                    String date = f.binding.date.getText().toString();

                    if (id.length() > 0){
                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        db.editNote(note,title,id,date);

                        db.close();
                        Snackbar snackbar = Snackbar.make(view, getString(R.string.saved), Snackbar.LENGTH_SHORT);
                        snackbar.setAnchorView(binding.fab);
                        snackbar.show();
                    } else if (note.length() > 0){
                        String currentDate = new SimpleDateFormat("HH:mm dd/MM", Locale.getDefault()).format(new Date());

                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        db.addNote(note,title,currentDate);

                        db.close();
                        Snackbar snackbar = Snackbar.make(view, getString(R.string.added), Snackbar.LENGTH_SHORT);
                        snackbar.setAnchorView(binding.fab);
                        snackbar.show();

                    }else {
                        Snackbar snackbar = Snackbar.make(view, getString(R.string.too_short), Snackbar.LENGTH_SHORT);
                        snackbar.setAnchorView(binding.fab);
                        snackbar.show();
                    }
                }
            }
        });

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}