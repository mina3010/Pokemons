package com.mina.pokemonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mina.pokemonapp.adapters.PokemonnAdapter;
import com.mina.pokemonapp.model.Pokemon;
import com.mina.pokemonapp.viewModel.PokemonViewModel;

import java.util.ArrayList;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private PokemonViewModel viewModel;
    private RecyclerView rv;
    private PokemonnAdapter adapter;
    private Button fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.pokemon_rv);
        fav = findViewById(R.id.btn_fav);
        adapter = new PokemonnAdapter(this);

        rv.setAdapter(adapter);
        setupSwipe();

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this ,FavActivity.class));
            }
        });

        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getPokemons();

        viewModel.getPokemonList().observe(this, new Observer<ArrayList<Pokemon>>() {
            @Override
            public void onChanged(ArrayList<Pokemon> pokemons) {
                adapter.setPokemonViewHolderList(pokemons);
            }
        });
    }

    public void setupSwipe(){

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipePokemonPosition = viewHolder.getAdapterPosition(); // get position
                Pokemon swipePokemon = adapter.getPokemonAt(swipePokemonPosition); // get pokemon
                viewModel.insertPokemon(swipePokemon);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Pokemon added to favourite", Toast.LENGTH_SHORT).show();

            }
        };

        // to attach with recyclerview
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rv);
    }
}