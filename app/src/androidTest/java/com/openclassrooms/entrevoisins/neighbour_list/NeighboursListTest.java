
package com.openclassrooms.entrevoisins.neighbour_list;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.TestsUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int itemsCount;
    private List<Neighbour> listNeighbours;
    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);


    @Before
    public void setUp() {
        listNeighbours = DUMMY_NEIGHBOURS;
        itemsCount = DUMMY_NEIGHBOURS.size();
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.list_neighbours)).check(matches(hasMinimumChildCount(1)));
    }

    //test v??rifiant que lorsqu???on clique sur un ??l??ment de la liste, l?????cran de
    //d??tails est bien lanc?? (le TextView tv_nom est pr??sent)
    @Test
    public void myNeighboursList_goTo_ShowNeighbourActivity() {
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, TestsUtils.clickInItemView(R.id.item_list_name)));
        onView(withId(R.id.tv_nom)).check(matches(withText(DUMMY_NEIGHBOURS.get(0).getName())));
    }


    //test v??rifiant qu???au d??marrage de ce nouvel ??cran, le TextView indiquant
    //le nom de l???utilisateur en question est bien rempli ;

    @Test
    public void myNeighboursList_goTo_TheGoodNeighbour() {
        String name = listNeighbours.get(0).getName();
        onView(allOf(withId(R.id.item_list_name), withText(name), withParent(withParent(withId(R.id.list_neighbours))))).perform(click());
        onView(ViewMatchers.withId(R.id.tv_nom)).check(matches(withText(name)));
    }

    //test v??rifiant qu???au clic sur le bouton de suppression, la liste d???utilisateurs
    //compte bien un utilisateur en moins ;
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        onView(withId(R.id.list_neighbours)).check(withItemCount(itemsCount));
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(1, TestsUtils.clickInItemView(R.id.item_list_delete_button)));
        onView(withId(R.id.list_neighbours)).check(withItemCount(itemsCount - 1));
    }

    //test v??rifiant que l???onglet Favoris n???affiche que les voisins marqu??s comme
    //favoris

    @Test
    public void favoriteList_showTheGoodNeighbors() {

        //V??rification ??tat initial = 0 favoris
        onView(withId(R.id.tabs)).perform(swipeLeft());
        onView(withId(R.id.recycler_list_favorites)).check(withItemCount(0));

        // Affichage d'1 favori
        String name = listNeighbours.get(0).getName();
        onView(withId(R.id.tabs)).perform(swipeRight());
        onView(allOf(withId(R.id.item_list_name), withText(name))).perform(click());
        onView(withId(R.id.fab_favoris)).perform(click());
        onView(withId(R.id.backToMain)).perform(click());
        onView(withId(R.id.tabs)).perform(swipeLeft());
        onView(withId(R.id.recycler_list_favorites)).check(withItemCount(1));
        onView(withId(R.id.recycler_list_favorites)).check(matches(TestsUtils.atPosition(0, hasDescendant(withText(name)))));

    }


}

