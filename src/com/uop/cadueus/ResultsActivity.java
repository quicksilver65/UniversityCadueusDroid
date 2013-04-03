package com.uop.cadueus;


import java.io.File;
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
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.Toast;

public class ResultsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);

		ListView lstProviders = (ListView) findViewById(R.id.lstProviders);
		lstProviders.setAdapter(new ProviderAdapter(this, DataStore.Providers));
		registerForContextMenu(lstProviders);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_results, menu);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

		if (v.getId() == R.id.lstProviders) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			MedicalProvider mp = DataStore.Providers.get(info.position);
			menu.setHeaderTitle(mp.getFirstName() + " " + mp.getLastName());
			menu.add(0, mp.getId(), 0, "Make Call");
			menu.add(0, mp.getId(), 0, "Navigate");
			menu.add(0, mp.getId(), 0, "Add Favorite");
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle() == "Make Call") {
			
			MedicalProvider mp = DataStore.ProviderById(item.getItemId());
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + mp.getPhoneNumber()));
			startActivity(intent);

			
		} else if (item.getTitle() == "Navigate") {
			
			double[] coordinates = DataStore.GetLatLong(item.getItemId());
			String url = "google.navigation:ll="+coordinates[0]+","+ coordinates[1];
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url)); 
			startActivity(i);
			
		} else if (item.getTitle() == "Add Favorite") {
			MedicalProvider mp = DataStore.ProviderById(item.getItemId());
			try {
				ArrayList<MedicalProvider> list = new ArrayList<MedicalProvider>();
				File file = getFileStreamPath(DataStore.storageName);
				if(file.exists()){
					list = DataStore.GetFavorites(this.openFileInput(DataStore.storageName));
					if(list!=null){
						for(MedicalProvider obj: list){
							if(obj.getId()==mp.getId())
								return false;
						}
						list.add(mp);
					}
					else{
						list = new ArrayList<MedicalProvider>();
						list.add(mp);
					}
				}
				else{
					list.add(mp);
				}
				DataStore.SaveFavorites(this.openFileOutput(DataStore.storageName, Context.MODE_PRIVATE), list);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Toast.makeText(this, "Favorites updated!", Toast.LENGTH_SHORT).show();
			
		} else {
			return false;
		}

		return true;
	}

}
