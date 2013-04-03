package com.uop.cadueus;

import BusinessLogic.DataStore;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SearchActivity extends Activity {

	private Button searchButton;
	private ProgressDialog dialog = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTheme(android.R.style.Theme_Light);
		setContentView(R.layout.activity_search);

		dialog = new ProgressDialog(this);
		dialog.show();
		new AsyncCriteriaLoader().execute(dialog);

		searchButton = (Button) findViewById(R.id.btnSearch);
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog = new ProgressDialog(arg0.getContext());
				dialog.show();
				new AsyncResultsLoad().execute(dialog);

			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_search, menu);
		return true;
	}

	private class AsyncResultsLoad extends AsyncTask<ProgressDialog, String, Boolean> {
		ProgressDialog _dialog;
		@Override
		protected void onPostExecute(Boolean result) {
			_dialog.dismiss();
			if (result.booleanValue()) {	
				MainActivity.activityTabHost.setCurrentTab(4);
			}
			
		}

		@Override
		protected void onProgressUpdate(String... values) {
			_dialog.setTitle(values[0]);
		}

		@Override
		protected Boolean doInBackground(ProgressDialog... params) {
			_dialog = params[0];
			_dialog.setMessage("Loading providers");
			Boolean returnValue = false;
			try {
				AutoCompleteTextView act = (AutoCompleteTextView) findViewById(R.id.txtCompleteSpecialty);
				String specialty = act.getText().toString();

				act = (AutoCompleteTextView) findViewById(R.id.txtCompleteCity);
				String city = act.getText().toString();

				EditText et = (EditText) findViewById(R.id.txtName);
				String lastName = et.getText().toString();
				publishProgress("Loading providers");
				DataStore.LoadProviders(specialty, city, lastName);
				
				returnValue=true;

			} catch (Exception ex) {

			}
			return returnValue;
		}

	}

	//Async example 
	private class AsyncCriteriaLoader extends
			AsyncTask<ProgressDialog, String, Boolean> {
		ProgressDialog _dialog;

		@Override
		protected void onPostExecute(Boolean result) {
			if (result.booleanValue()) {
				ArrayAdapter<String> specialtyAdapter = new ArrayAdapter<String>(
						_dialog.getContext(),
						android.R.layout.simple_dropdown_item_1line,
						DataStore.Specialites);
				AutoCompleteTextView specialtyView = (AutoCompleteTextView) findViewById(R.id.txtCompleteSpecialty);
				specialtyView.setAdapter(specialtyAdapter);

				ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(
						_dialog.getContext(),
						android.R.layout.simple_dropdown_item_1line,
						DataStore.Cities);
				AutoCompleteTextView cityView = (AutoCompleteTextView) findViewById(R.id.txtCompleteCity);
				cityView.setAdapter(cityAdapter);
			}
			_dialog.dismiss();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(String... values) {
			_dialog.setTitle(values[0]);
		}

		@Override
		protected Boolean doInBackground(ProgressDialog... params) {
			_dialog = params[0];
			_dialog.setMessage("Loading data");
			Boolean returnValue = false;
			try {
				if (DataStore.Specialites.size() == 0) {
					publishProgress("Loading cities");
					DataStore.LoadCities();
					publishProgress("Loading specialties");
					DataStore.LoadSpecialties();
				}

				returnValue = true;
			} catch (Exception ex) {
			}

			return returnValue;
		}

	}

}
