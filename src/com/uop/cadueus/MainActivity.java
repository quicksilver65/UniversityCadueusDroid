package com.uop.cadueus;


import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;

import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	public static TabHost activityTabHost;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TabHost tabHost = getTabHost();
		MainActivity.activityTabHost = tabHost;

		// Tab for Home
		TabSpec homesspec = tabHost.newTabSpec("Home");
		homesspec.setIndicator("Home",
				getResources().getDrawable(R.drawable.icon_home));
		Intent homeIntent = new Intent(this, HomeActivity.class);
		homesspec.setContent(homeIntent);

		// Tab for Favorites
		TabSpec favoritesspec = tabHost.newTabSpec("Favorites");
		favoritesspec.setIndicator("Favorites",
				getResources().getDrawable(R.drawable.icon_favorites));
		Intent favoritesIntent = new Intent(this, FavoritesActivity.class);
		favoritesspec.setContent(favoritesIntent);

		// Tab for Search
		TabSpec searchspec = tabHost.newTabSpec("Search");
		searchspec.setIndicator("Search",
				getResources().getDrawable(R.drawable.icon_search));
		Intent searchIntent = new Intent(this, SearchActivity.class);
		searchspec.setContent(searchIntent);

		// Tab for Information
		TabSpec informationspec = tabHost.newTabSpec("Info");
		informationspec.setIndicator("Info",
				getResources().getDrawable(R.drawable.icon_information));
		Intent informationIntent = new Intent(this, InformationActivity.class);
		informationspec.setContent(informationIntent);

		// Tab for Information
		TabSpec resultspec = tabHost.newTabSpec("Results");
		resultspec.setIndicator("Results",
				getResources().getDrawable(R.drawable.icon_information));
		Intent resultIntent = new Intent(this, ResultsActivity.class);
		resultspec.setContent(resultIntent);
		

		// Adding all TabSpec to TabHost
		tabHost.addTab(homesspec); // Adding home tab
		tabHost.addTab(searchspec); // Adding search tab
		tabHost.addTab(favoritesspec); // Adding favorite tab
		tabHost.addTab(informationspec); // Adding favorite tab
		tabHost.addTab(resultspec); // Adding favorite tab
	
		tabHost.getTabWidget().getChildAt(4).setVisibility(View.GONE);
	

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
