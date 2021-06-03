package com.mina.pokemonapp.repository;

import com.mina.pokemonapp.db.PokemonDao;
import com.mina.pokemonapp.model.Pokemon;
import com.mina.pokemonapp.model.PokemonResponse;
import com.mina.pokemonapp.network.PokemonApiService;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import io.reactivex.rxjava3.core.Observable;


public class Repository {
    private PokemonApiService pokemonApiService;
    private PokemonDao pokemonDao;

    @Inject //to make dagger using this class ... iam using inject because this my class made be me .. to dagger get data without viewModel
    public Repository(PokemonApiService pokemonApiService , PokemonDao pokemonDao) {
        this.pokemonApiService = pokemonApiService;
        this.pokemonDao =  pokemonDao;
    }

    public Observable<PokemonResponse> getPokemons(){
        return pokemonApiService.getPokemons();
    }

    public void insertPokemon(Pokemon pokemon){
        pokemonDao.insertPokemon(pokemon);
    }

    public void deletePokemon (String pokemonName){pokemonDao.deletePokemon(pokemonName);}

    public LiveData<List<Pokemon>> getFavPokemons(){
        return pokemonDao.getPokemons();
    }
}
