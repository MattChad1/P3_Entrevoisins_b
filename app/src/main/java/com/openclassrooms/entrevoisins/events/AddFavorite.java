package com.openclassrooms.entrevoisins.events;


import com.openclassrooms.entrevoisins.model.Neighbour;

public class AddFavorite {

    public Neighbour neighbour;

    public AddFavorite(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}