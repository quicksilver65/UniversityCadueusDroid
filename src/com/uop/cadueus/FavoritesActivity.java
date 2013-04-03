package com.uop.cadueus;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import BusinessLogic.DataStore;
import BusinessLogic.MedicalProvider;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class FavoritesActivity extends Activity {
	private ArrayList<MedicalProvider> favorites = new ArrayList<MedicalProvider>();
	private ProviderAdapter adapter;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		try {

			favorites = DataStore.GetFavorites(this
					.openFileInput(DataStore.storageName));
			ListView lstFavorites = (ListView) findViewById(R.id.lstFavorites);
			adapter = new ProviderAdapter(this, favorites);
			lstFavorites.setAdapter(adapter);
			registerForContextMenu(lstFavorites);

		} catch (Exception ex) {
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_favorites, menu);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		if (v.getId() == R.id.lstFavorites) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			MedicalProvider mp = favorites.get(info.position);
			menu.setHeaderTitle(mp.getFirstName() + " " + mp.getLastName());
			menu.add(0, mp.getId(), 0, "Make Call");
			menu.add(0, mp.getId(), 0, "Navigate");
			menu.add(0, mp.getId(), 0, "Remove Favorite");
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle() == "Make Call") {

			MedicalProvider mp = ProviderById(item.getItemId());
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + mp.getPhoneNumber()));
			startActivity(intent);

		} else if (item.getTitle() == "Navigate") {
			
			double[] coordinates = DataStore.GetLatLong(item.getItemId());
			String url = "google.navigation:ll="+coordinates[0]+","+ coordinates[1];
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url)); 
			startActivity(i);

		} else if (item.getTitle() == "Remove Favorite") {

			try {
				for (int x = favorites.size() - 1; x > -1; x--) {
					if (favorites.get(x).getId() == item.getItemId()) {
						favorites.remove(x);
						break;
					}

				}

				if (favorites != null)
					DataStore.SaveFavorites(this.openFileOutput(
							DataStore.storageName, Context.MODE_PRIVATE),
							favorites);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			adapter.notifyDataSetChanged();

			Toast.makeText(this, "Favorites updated!", Toast.LENGTH_SHORT)
					.show();

		} else {
			return false;
		}

		return true;
	}
	
	private MedicalProvider ProviderById(int id){
		if(favorites!=null && favorites.size()!=0){
			for(MedicalProvider obj:favorites){
				if(obj.getId()==id)
					return obj;	
			}
		}
		return null;
	
	}
}
