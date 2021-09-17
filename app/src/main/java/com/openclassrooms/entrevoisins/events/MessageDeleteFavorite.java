package com.openclassrooms.entrevoisins.events;


import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Event fired when a user deletes a Favorites
 */
public class MessageDeleteFavorite {

    public final Neighbour neighbour;
    public MessageDeleteFavorite(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}