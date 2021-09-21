package com.openclassrooms.entrevoisins.utils;


import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

public class FavoritesUtils {

    public static List<Neighbour> filterFavorites(List<Neighbour> mNeighbours) {
        List<Neighbour> re = new ArrayList<>();
        for (Neighbour n : mNeighbours) {
            if (n.getFavorite()) re.add(n);
        }
        return re;
    }

    public static void addFavorite(Neighbour neighbour, List<Neighbour> mNeighbours) {
        int index = mNeighbours.indexOf(neighbour);
        mNeighbours.get(index).setFavorite(true);
    }


}
