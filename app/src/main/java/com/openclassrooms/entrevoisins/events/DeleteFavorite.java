package com.openclassrooms.entrevoisins.events;


import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Event fired when a user deletes a Favorites
 */
public class DeleteFavorite {

    public final Neighbour neighbour;

    public DeleteFavorite(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}