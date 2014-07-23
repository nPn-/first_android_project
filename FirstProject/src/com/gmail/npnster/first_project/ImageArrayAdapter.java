package com.gmail.npnster.first_project;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ImageArrayAdapter extends ArrayAdapter<Integer> {
	private Integer[] images;

	public ImageArrayAdapter(Context context, Integer[] images) {
		super(context, android.R.layout.simple_spinner_item, images);
		this.images = images;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getImageForPosition(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getImageForPosition(position);
	}

	private View getImageForPosition(int position) {
		ImageView imageView = new ImageView(getContext());
		imageView.setBackgroundResource(images[position]);
		imageView.setLayoutParams(new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		
		return imageView;
	}
}