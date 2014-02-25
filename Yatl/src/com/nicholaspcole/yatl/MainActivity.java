package com.nicholaspcole.yatl;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private String[] destinations;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView drawerList;

    /**
     * Fragment that appears in the content frame (of the drawer layout) to show
     * a destination.
     */
    public static class DestinationFragment extends Fragment {
        public static final String ARG_DESTINATION_NUMBER = "destination_number";

        public DestinationFragment() {
            // An empty constructor is required for Fragment subclasses.
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            int i = getArguments().getInt(ARG_DESTINATION_NUMBER);

            View rootView;
            switch (i) {
                case 0:
                    rootView = inflater.inflate(R.layout.fragment_home,
                            container, false);
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                default:
                    rootView = inflater.inflate(R.layout.fragment_blank,
                            container, false);
                    break;
            }

            String destination = getResources()
                    .getStringArray(R.array.destinations)[i];

            getActivity().setTitle(destination);
            return rootView;
        }
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position,
                long id) {
            selectItem(position);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
        drawerToggle.onConfigurationChanged(newConfiguration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        destinations = getResources().getStringArray(R.array.destinations);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerList = (ListView) findViewById(R.id.navigation_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, destinations));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // TODO Examine the navigation drawer sample code in detail to determine
        // how exactly the mDrawerTitle and mTitle variables are used.
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            /** Called when the drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // getActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            /** Called when the drawer has settled in a completely opened state. */
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                // getActionBar().setTitle(title);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    private void selectItem(int position) {
        // Create a new fragment and specify the destination to show based on
        // the position.

        Fragment fragment = new DestinationFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(DestinationFragment.ARG_DESTINATION_NUMBER, position);
        fragment.setArguments(arguments);

        // Insert the fragment by replacing any existing fragment.

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).commit();

        // Highlight the selected destination, update the title, and close the
        // drawer.

        drawerList.setItemChecked(position, true);
        getActionBar().setTitle(destinations[position]);
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }
}
