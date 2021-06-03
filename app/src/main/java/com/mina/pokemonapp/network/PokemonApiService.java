package com.mina.pokemonapp.network;

import com.mina.pokemonapp.model.PokemonResponse;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface PokemonApiService {

    // using rxJava to create raping to response in background thread .. use Observable<> from reactivex
    @GET("pokemon")     // using @ retrofit to get pokemon from BASE_URL
    Observable<PokemonResponse> getPokemons();


}
