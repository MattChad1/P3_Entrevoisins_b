
package com.openclassrooms.entrevoisins.neighbour_list;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.IsNull.notNullValue;

import static java.util.EnumSet.allOf;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.utils.TextClicAction;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int itemsCount;
    private List<Neighbour> listNeighbours = DUMMY_NEIGHBOURS;


    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);


    @Before
    public void setUp() {
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
        onView(withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        onView(withId(R.id.list_neighbours)).check(withItemCount(itemsCount));
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(5, new DeleteViewAction()));
        onView(withId(R.id.list_neighbours)).check(withItemCount(itemsCount - 1));
    }

    @Test
    public void myNeighboursList_goTo_ShowNeighbourActivity() {
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new TextClicAction()));
        onView(ViewMatchers.withId(R.id.tv_nom))
                .check(matches(withText(DUMMY_NEIGHBOURS.get(0).getName())));
    }

    @Test
    public void myNeighboursList_goTo_TheGoodNeighbour() {
        String name = listNeighbours.get(0).getName();
        //onData(CoreMatchers.allOf(instanceOf(String.class)).

        onView(Matchers.allOf(withId(R.id.item_list_name), withText(name))).perform(click());
        onView(ViewMatchers.withId(R.id.tv_nom))
                .check(matches(withText(name)));

//        onData(allOf(is(instanceOf(Map.class)),
//                hasEntry(equalTo(mActivity.ROW_TEXT), is(name))));
//
//        onView(allOf(withId(R.id.item_list_name), withText(name)));


    }
}

