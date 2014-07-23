package com.gmail.npnster.first_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.Marker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MapMarker implements Target {
	Marker mMarker;
	public String getName() {
		return mMarker.getName();
	}

	public String getGravatarUrl() {
		return mMarker.getGravatarUrl();
	}



	GoogleMap mMap;

	public MapMarker(Context context, Marker marker, GoogleMap map) {
		mMarker = marker;
		mMap = map;
		Picasso.with(context).load(marker.getGravatarUrl()).into(this);
	}

	@Override
	public void onBitmapFailed(Drawable arg0) {
		// TODO Auto-generated method stub    
		System.out.println("failed to load bit map for gravatar");
		com.google.android.gms.maps.model.Marker googleMapMarker = mMap.addMarker(new MarkerOptions()
	     .position(new LatLng(mMarker.getLatitude(), mMarker.getLongitude()))
	     .title(mMarker.getName()));
	
		
	}

	@Override
	public void onBitmapLoaded(Bitmap bitMap, LoadedFrom loadedFrom) {
		// TODO Auto-generated method stub
		System.out.println("loaded bit map for gravatar");
		System.out.println("bit map was loaded from");
		System.out.println(loadedFrom.toString());
		com.google.android.gms.maps.model.Marker googleMapMarker = mMap.addMarker(new MarkerOptions()
	     .position(new LatLng(mMarker.getLatitude(), mMarker.getLongitude()))
	     .title(mMarker.getName())
	     .icon(BitmapDescriptorFactory.fromBitmap(bitMap)));
	    
	}
	


	@Override
	public void onPrepareLoad(Drawable arg0) {
		// TODO Auto-generated method stub
		System.out.println("inside MapMarker onPrepareLoad");
		
	}

}
