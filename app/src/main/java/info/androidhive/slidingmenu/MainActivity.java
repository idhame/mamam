package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.adapter.NavDrawerListAdapter;
import info.androidhive.slidingmenu.model.NavDrawerItem;

import info.androidhive.slidingmenu.model.Faculty;
import info.androidhive.slidingmenu.model.Kantin;
import info.androidhive.slidingmenu.model.Review;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
import android.content.Context;


public class MainActivity extends Activity implements LocationListener{
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

    private ListView categoryListView;
    private ArrayAdapter categoryItemArrayAdapter;
    private static final String FACULTY_CACHE_FILE = "faculty_cache.ser";
    private static final String CANTEEN_CACHE_FILE = "canteen_cache.ser";
    private List<Faculty> listFacultyRead;
    private List<Kantin> listKantinRead;
    private Faculty facultyKantinView;
    private Kantin kantinView;
    private List<Kantin> listEatLater;
    private List<Review> listReview;

    private Stack<Fragment> fragmentStack;

    private double latitude;
    private double longitude;
    private Context con;
    LocationManager lm;
    TextView lt, ln;
    String provider;
    Location l;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean isGPSTrackingEnabled = false;
    private String provider_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        fragmentStack = new Stack<Fragment>();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// What's hot, We  will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
		

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
//		case R.id.action_settings:
//			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new SearchKantinFragment();
			break;
		case 2:
            getNearestKantin();
			fragment = new ListKantinFragment();
			break;
		case 3:
			fragment = new ListEatLaterFragment();
			break;
		case 4:
			fragment = new ListUserReviewFragment();
			break;
		case 5:
			fragment = new SettingFragment();
			break;
		default:
			break;
		}

		if (fragment != null) {

			FragmentManager fragmentManager = getFragmentManager();

            //empty stack
            for(int i=0; i< fragmentManager.getBackStackEntryCount(); i++) {
                fragmentManager.popBackStack();
            }

			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).addToBackStack("fragBack").commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);

		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

    public void viewFaculty(String faculty) {
        Fragment fragment = new ListKantinFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).addToBackStack("fragBack").commit();

        // update selected item and title, then close the drawer
        setTitle(faculty);
    }

    public void viewKantin(String kantin) {
        Fragment fragment = new DeskripsiKantinFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).addToBackStack("fragBack").commit();

        // update selected item and title, then close the drawer
        setTitle(kantin);
    }

    public void viewReviewKantin() {
        Fragment fragment = new ListReviewFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).addToBackStack("fragBack").commit();
    }

    public void viewWriteReview() {
        Fragment fragment = new WriteReviewFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).addToBackStack("fragBack").commit();
    }

    public void viewAbout() {
        Fragment fragment = new AboutFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).addToBackStack("fragBack").commit();
    }

    public void viewDeleteEatLater(List<Kantin> listEatLater) {
        this.listEatLater = listEatLater;
        Fragment fragment = new ListEatLaterCheckboxFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).addToBackStack("fragBack").commit();
    }

    public void viewDeleteReview(List<Review> listReview) {
        this.listReview = listReview;
        Fragment fragment = new ListUserReviewCheckboxFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).addToBackStack("fragBack").commit();
    }

    public void viewTutorial() {
    }

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()==1) {
            getFragmentManager().popBackStack();
            super.onBackPressed();
        }
        else {
            super.onBackPressed();
        }
    }


    public void renderFacultyList() {
        try {
            FileInputStream fis = openFileInput(FACULTY_CACHE_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listFacultyRead = (List<Faculty>) ois.readObject();
            fis.close();
            ois.close();
            fis = openFileInput(CANTEEN_CACHE_FILE);
            ois = new ObjectInputStream(fis);
            listKantinRead = (List<Kantin>) ois.readObject();
            fis.close();
            ois.close();
            FragmentManager fm = getFragmentManager();
            ((HomeFragment)fm.findFragmentById(R.id.frame_container)).renderList();
        } catch(Exception e) {
            new AsyncDataFetch(this).execute();
        }
    }


    public List<Faculty> getListFacultyRead() {
        return listFacultyRead;
    }

    public void setFacultyKantinView(Faculty f) {
        this.facultyKantinView = f;
    }

    public Faculty getFacultyKantinView() {
        return facultyKantinView;
    }

    public void setKantinView(Kantin k) {
        this.kantinView = k;
    }

    public Kantin getKantinView() {
        return kantinView;
    }

    public void getRate(int id) {
        new AsyncRateFetch(this).execute(id);
    }

    public List<Kantin> getListEatLater() {
        return listEatLater;
    }

    public List<Review> getListReview() {
        return listReview;
    }

    public void renderKantin(double rate) {

        if(getFragmentManager().findFragmentById(R.id.frame_container) instanceof DeskripsiKantinFragment)
        ((DeskripsiKantinFragment)getFragmentManager().findFragmentById(R.id.frame_container)).renderView(rate);
    }

    public void fetchKantinReview() {
        new AsyncReviewFetch(this).execute(kantinView.getId());
    }

    public void renderReview(List<Review> list) {
        if(getFragmentManager().findFragmentById(R.id.frame_container) instanceof ListReviewFragment)
        ((ListReviewFragment)getFragmentManager().findFragmentById(R.id.frame_container)).renderList((list));
    }

    public void postReview(String title, String rating, float ratingbar) {
        Review review = new Review(0, title, rating,17,ratingbar,kantinView.getId());
        new AsyncReviewPost(this).execute(review);
    }

    public void finishReview() {
        Toast.makeText(this,
                "Penilaian telah berhasil disimpan",
                Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }

    public void searchKantin(String keyword) {
        List<Kantin> result = new ArrayList<Kantin>();
        for(int i=0; i<listKantinRead.size();i++) {
            if(listKantinRead.get(i).getNama().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(listKantinRead.get(i));
            } else if (listKantinRead.get(i).getDeskripsi().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(listKantinRead.get(i));
            } else if (listKantinRead.get(i).getMenu().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(listKantinRead.get(i));
            }
        }
        if(getFragmentManager().findFragmentById(R.id.frame_container) instanceof SearchKantinFragment)
        ((SearchKantinFragment)getFragmentManager().findFragmentById(R.id.frame_container)).renderList(result);
    }

    public void fetchUserReview() {
        new AsyncUserReviewFetch(this).execute(17);
    }

    public void renderUserReview(List<Review> list) {
        if(getFragmentManager().findFragmentById(R.id.frame_container) instanceof ListUserReviewFragment)
        ((ListUserReviewFragment)getFragmentManager().findFragmentById(R.id.frame_container)).renderList(list);
    }

    public void addToEatLater() {
        ArrayList<String> ar = new ArrayList<String>();
        ar.add("17");
        ar.add(kantinView.getId()+"");
        new AsyncAddEatLater(this).execute(ar);
    }



    public void finishAddEatLater() {
        Toast.makeText(this,
                "Eat Later telah berhasil disimpan",
                Toast.LENGTH_LONG).show();
    }

    public void finishDeleteEatLater() {
        Toast.makeText(this,
                "Eat Later telah berhasil dihapus",
                Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }

    public void finishAddLaporan() {
        Toast.makeText(this,
                "Review telah dilaporkan",
                Toast.LENGTH_LONG).show();
    }

    public void finishDeleteReview() {
        Toast.makeText(this,
                "Review telah berhasil dihapus",
                Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }


    public void fetchEatLater() {
        new AsyncEatLaterFetch(this).execute(17);
    }

    public void renderListEatLater(List<Kantin> list) {
        if(getFragmentManager().findFragmentById(R.id.frame_container) instanceof ListEatLaterFragment)
        ((ListEatLaterFragment)getFragmentManager().findFragmentById(R.id.frame_container)).renderList(list);
    }

    public void addLaporan(int idPenilaian) {
        ArrayList<String> ar = new ArrayList<String>();
        ar.add("17");
        ar.add(idPenilaian+"");
        new AsyncAddLaporan(this).execute(ar);
    }

    public void deleteEatLater(ArrayList<String> toDelete) {
        toDelete.add("17");
        final ArrayList<String> delete = toDelete;
        final Activity parent = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apakah anda yakin mau menghapus eat later" +
                " ini?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new AsyncDeleteEatLater((MainActivity)parent).execute(delete);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void deleteReview(ArrayList<String> toDelete) {
        final ArrayList<String> delete = toDelete;
        final Activity parent = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apakah anda yakin mau menghapus review ini?");
        builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new AsyncDeleteReview((MainActivity)parent).execute(delete);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void getNearestKantin() {
        float min = Float.MAX_VALUE;
        String fakultas = "";
        getCurrentLocation();
        Log.i("Current Latitude", "" + latitude);
        Log.i("Current Longitude",""+longitude);
        ArrayList<Kantin> ar = new ArrayList<Kantin>();
        for(int i=0;i<listFacultyRead.size();i++) {
            double longfak = listFacultyRead.get(i).getLongitude();
            double latfak = listFacultyRead.get(i).getLatitude();
            Log.i("Latitude",""+latfak);
            Log.i("Longitude",""+longfak);
            float a = countDistance(latfak,longfak);
            if(a<300.0) {
                ar.addAll(listFacultyRead.get(i).getListKantin());
            }
            Log.i(listFacultyRead.get(i).getNama(), "" + countDistance(latfak, longfak));
        }
        stopUsingGPS();
        Log.i("MINIMAL",fakultas+" "+min);
        facultyKantinView = new Faculty(0,"",0.0,0.0);
        facultyKantinView.setListKantin(ar);
    }


    public void getCurrentLocation() {
        lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

//        if(isGPSEnabled) {
//            this.isGPSTrackingEnabled = true;
//            provider_info = LocationManager.GPS_PROVIDER;
//        } else
        if(isNetworkEnabled) {
            this.isGPSTrackingEnabled = true;
            provider_info = LocationManager.NETWORK_PROVIDER;
        }

        if (!provider_info.isEmpty()) {
            lm.requestLocationUpdates(
                    provider_info,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES,
                    this
            );

            if (lm != null) {
                l = lm.getLastKnownLocation(provider_info);
                updateGPSCoordinates();
            }
        }
    }

//    public List<Faculty> selectNearestKantin(List<Faculty> listFak) {
//        List<Faculty> listFakultas = listFak;
//
//        for(int i=0; i<listFakultas.size(); i++) {
//            float radius;
//            radius = countDistance(listFakultas.get(i).getLat(), listFakultas.get(i).getLng());
//
//            if(radius > 1000000.0) {
//                listFakultas.remove(i);
////                i--;
//            }
//        }
//
//        return listFakultas;
//    }

    public float countDistance(double latFak, double longFak) {
        Location l1=new Location("One");
        l1.setLatitude(latitude);
        l1.setLongitude(longitude);
        Location l2=new Location("Two");
        l2.setLatitude(latFak);
        l2.setLongitude(longFak);
        float distance_bw_one_and_two=l1.distanceTo(l2);
        return distance_bw_one_and_two;
    }

    public void updateGPSCoordinates() {
        if (l != null) {
            latitude = l.getLatitude();
            longitude = l.getLongitude();
        }
    }

    public double getLatitude() {
        if (l != null) {
            latitude = l.getLatitude();
        }

        return latitude;
    }

    public double getLongitude() {
        if (l != null) {
            longitude = l.getLongitude();
        }

        return longitude;
    }

    public boolean getIsGPSTrackingEnabled() {

        return this.isGPSTrackingEnabled;
    }

    public void stopUsingGPS() {
        if (lm != null) {
            lm.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }





}
