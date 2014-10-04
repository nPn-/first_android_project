package com.gmail.npnster.first_project;

import java.util.ArrayList;
import java.util.List;

import com.gmail.npnster.first_project.api_params.GetMicropostsResponse.Micropost;
import com.gmail.npnster.first_project.api_params.UserListResponse.User;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class UserDetailMicropostAdapter extends ArrayAdapter<Micropost> {
	private final Context context;
	private final ArrayList<Micropost> values;

	public UserDetailMicropostAdapter(Context context, ArrayList<Micropost> values) {
		super(context, R.layout.user_detail_micropost_row_layout, values);
		this.context = context;
		this.values = values;
	}

	static class ViewHolder {
		public TextView mContent;
		public TextView mPostedAgo;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		System.out.println("creating a micropost row");
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.user_detail_micropost_row_layout, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.mContent = (TextView) rowView.findViewById(R.id.micropost_content);
			viewHolder.mPostedAgo = (TextView) rowView.findViewById(R.id.posted_time_ago);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.mContent.setText(values.get(position).getContent().trim());
		holder.mPostedAgo.setText(MyDateUtils.timeAgo(values.get(position).getCreated_at()));

		return rowView;
	}

}
