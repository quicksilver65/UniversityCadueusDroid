package com.uop.cadueus;

import java.util.ArrayList;

import BusinessLogic.MedicalProvider;
import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class ProviderAdapter extends BaseAdapter{
	private final ArrayList<MedicalProvider> searchArrayList;

	private LayoutInflater mInflater;

	public ProviderAdapter(final Context context,
			final ArrayList<MedicalProvider> results) {
		searchArrayList = results;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return searchArrayList.size();
	}

	public Object getItem(int position) {
		return searchArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.medicalproviderrow,
					null);
			holder = new ViewHolder();
			holder.lblFirstName = (TextView) convertView
					.findViewById(R.id.lblFirstName);
			holder.lblLastName = (TextView) convertView
					.findViewById(R.id.lblLastName);
			holder.lblSpecialty = (TextView) convertView
					.findViewById(R.id.lblSpecialty);
			holder.lblAddress = (TextView) convertView
					.findViewById(R.id.lblAddress);
			holder.lblSuite = (TextView) convertView
					.findViewById(R.id.lblSuite);
			holder.lblCity = (TextView) convertView
					.findViewById(R.id.lblCity);
			holder.lblState = (TextView) convertView
					.findViewById(R.id.lblState);
			holder.lblZipCode = (TextView) convertView
					.findViewById(R.id.lblZipCode);
			holder.lblPhoneNumber = (TextView) convertView
					.findViewById(R.id.lblPhoneNumber);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.lblFirstName.setText(searchArrayList.get(position)
				.getFirstName());
		holder.lblLastName.setText(searchArrayList.get(position)
				.getLastName());
		holder.lblSpecialty.setText(searchArrayList.get(position)
				.getSpecialty());
		holder.lblAddress.setText(searchArrayList.get(position)
				.getAddress());
		holder.lblSuite.setText(searchArrayList.get(position).getSuite());
		holder.lblCity.setText(searchArrayList.get(position).getCity());
		holder.lblState.setText(searchArrayList.get(position).getState());
		holder.lblZipCode.setText(String.valueOf(searchArrayList.get(
				position).getZipCode()));

		String phn = "" + searchArrayList.get(position).getPhoneNumber();

		holder.lblPhoneNumber.setText(PhoneNumberUtils.formatNumber(phn));

		return convertView;
	}

	private class ViewHolder {
		TextView lblFirstName;
		TextView lblLastName;
		TextView lblSpecialty;
		TextView lblAddress;
		TextView lblSuite;
		TextView lblCity;
		TextView lblState;
		TextView lblZipCode;
		TextView lblPhoneNumber;
	}
}
