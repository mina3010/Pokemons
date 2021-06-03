package com.mina.pokemonapp.adapters;

import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.mina.pokemonapp.MainActivity;
import com.mina.pokemonapp.R;
import com.mina.pokemonapp.model.Pokemon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonnAdapter extends RecyclerView.Adapter<PokemonnAdapter.ViewHolder> {



    private List<Pokemon> PokemonViewHolderList = new ArrayList<>();
    private Context context;

    public void setPokemonViewHolderList(List<Pokemon> pokemonViewHolderList) {
        PokemonViewHolderList = pokemonViewHolderList;
        notifyDataSetChanged();
    }

    //get any pokemon from position
    public Pokemon getPokemonAt( int position){
        return PokemonViewHolderList.get(position);
    }

    public PokemonnAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txt.setText(PokemonViewHolderList.get(position).getName());

        if (PokemonViewHolderList.get(position).getUrl() != null) {
            //using Glide images
            Glide.with(context).load(PokemonViewHolderList.get(position).getUrl())
                    .into(holder.img);

        }

    }

    @Override
    public int getItemCount() {
        return PokemonViewHolderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        TextView txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_pokemon);
            txt = itemView.findViewById(R.id.txt_pokemon);
        }
    }
}
