// Generated by view binder compiler. Do not edit!
package com.example.pokemonsapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.pokemonsapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class PokemonCardBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final ImageView pokemonImage;

  @NonNull
  public final TextView pokemonName;

  private PokemonCardBinding(@NonNull CardView rootView, @NonNull ImageView pokemonImage,
      @NonNull TextView pokemonName) {
    this.rootView = rootView;
    this.pokemonImage = pokemonImage;
    this.pokemonName = pokemonName;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static PokemonCardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static PokemonCardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.pokemon_card, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static PokemonCardBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.pokemon_image;
      ImageView pokemonImage = ViewBindings.findChildViewById(rootView, id);
      if (pokemonImage == null) {
        break missingId;
      }

      id = R.id.pokemon_name;
      TextView pokemonName = ViewBindings.findChildViewById(rootView, id);
      if (pokemonName == null) {
        break missingId;
      }

      return new PokemonCardBinding((CardView) rootView, pokemonImage, pokemonName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}