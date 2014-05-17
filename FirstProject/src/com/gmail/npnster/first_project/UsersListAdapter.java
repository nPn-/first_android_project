package com.gmail.npnster.first_project;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UsersListAdapter extends ArrayAdapter<UserListItem> {
	private final Context context;
	private final ArrayList<UserListItem> values;

	public UsersListAdapter(Context context, ArrayList<UserListItem> values) {
		super(context, R.layout.row_layout, values);
		this.context = context;
		this.values = values;
	}

	static class ViewHolder {
		public TextView textView;
		public ImageView imageView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.row_layout, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) rowView.findViewById(R.id.label);
			viewHolder.imageView = (ImageView) rowView.findViewById(R.id.icon);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.textView.setText(values.get(position).getName());
		ImageView imageView = holder.imageView;
		String gravatarURL = "http://www.gravatar.com/avatar/"
				+ values.get(position).getGravatarId();
		Picasso.with(context).load(gravatarURL).into(imageView);

		return rowView;
	}

}
