package com.openclassrooms.entrevoisins.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import android.support.v4.app.Fragment;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.FavoritesFragment;
import com.openclassrooms.entrevoisins.utils.FavoritesUtils;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Objects;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;
    private Fragment favoritesFragment;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
        favoritesFragment = new FavoritesFragment();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(expectedNeighbours.toArray())));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void filterFavorites() {
        long expectedNumFaborites = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.stream().filter(Neighbour::getFavorite).count();
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> favorites = FavoritesUtils.filterFavorites(neighbours);
        assertEquals(favorites.size(), expectedNumFaborites);
    }

    @Test
    public void addFavorite() {
        List<Neighbour> neighbours = service.getNeighbours();
        FavoritesUtils.addFavorite(neighbours.get(0), neighbours);
        assertTrue(neighbours.get(0).getFavorite());
    }
}
