package com.mina.pokemonapp.viewModel;

import android.annotation.SuppressLint;
import android.util.Log;

import com.mina.pokemonapp.model.Pokemon;
import com.mina.pokemonapp.model.PokemonResponse;
import com.mina.pokemonapp.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PokemonViewModel extends ViewModel {
    private Repository repository;  // object to get data
    private MutableLiveData<ArrayList<Pokemon>> pokemonList =new MutableLiveData<>();   // list to store data
    private LiveData<List<Pokemon>> favList =null;

    public LiveData<List<Pokemon>> getFavList() {
        return favList;
    }

    @ViewModelInject    // to pass data from api to repository in viewModel by daggerHilt
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Pokemon>> getPokemonList() {
        return pokemonList;
    }

    // using rxJava to get data from repository in viewModel
    @SuppressLint("CheckResult")
    public void getPokemons(){
        // used subscribeOn .. io(in background) // to return observable to make it in background thread
        repository.getPokemons()
                .subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() { // using map to get data and change url to get images
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        ArrayList<Pokemon> list = pokemonResponse.getResults();

                        for (Pokemon pokemon : list){
                            String url = pokemon.getUrl();
                            String [] pokemon_index = url.split("/");
                            pokemon.setUrl("https://pokeres.bastionbot.org/images/pokemon/"+pokemon_index[pokemon_index.length-1]+".png");
                        }
                        return list;
                    }
                }).observeOn(AndroidSchedulers.mainThread()) //to add data in main Thread
                .subscribe(result->pokemonList.setValue(result), // to check if done or error happened
                        error-> Log.e("viewModel",error.getMessage()));
    }

    public void insertPokemon(Pokemon pokemon){
        repository.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName){repository.deletePokemon(pokemonName);}

    public void getFavPokemon(){
        favList =repository.getFavPokemons();
    }
}
