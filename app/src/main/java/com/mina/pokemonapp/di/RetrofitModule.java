package com.mina.pokemonapp.di;

import com.mina.pokemonapp.network.PokemonApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// to create object from retrofit to used from dagger hilt
@Module
@InstallIn(ApplicationComponent.class)
public class RetrofitModule {


    @Provides
    @Singleton
    public static PokemonApiService providePokemonApiService(){
        return new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(PokemonApiService.class);
    }

}
