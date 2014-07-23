package com.gmail.npnster.first_project;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CenterOnSpinnerAdapter extends ArrayAdapter<MapMarker> implements SpinnerAdapter {

	private final Context mContext;
	private final ArrayList<MapMarker> mMarkers;
	
	public CenterOnSpinnerAdapter(Context context, ArrayList<MapMarker> markers) {
		super(context, R.layout.center_on_spinner_row, R.id.center_on_name, markers );
		mContext = context;
		mMarkers = markers;
	}

	
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCommonView(position, convertView, parent);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCommonView(position, convertView, parent);
		
	}
	
	public View getCommonView(int position, View convertView, ViewGroup parent) {
		System.out.println("inside getCommonView for centeron spinner adapter");
		LayoutInflater inflater = (LayoutInflater) mContext
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.center_on_spinner_row, parent, false);
		    ImageView imageViewPerson = (ImageView) rowView.findViewById(R.id.center_on_image);
		    Picasso.with(mContext).load(mMarkers.get(position).getGravatarUrl()).into(imageViewPerson);
		    TextView textViewCenterOnDisplayName = (TextView) rowView.findViewById(R.id.center_on_name);		    		;
		    textViewCenterOnDisplayName.setText(mMarkers.get(position).getName());
		return rowView;
	}




}
