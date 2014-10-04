package com.gmail.npnster.first_project;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FollowedUsersListAdapter extends ArrayAdapter<UserListItem> {
	private final Context context;
	private final ArrayList<UserListItem> values;

	public FollowedUsersListAdapter(Context context, ArrayList<UserListItem> values) {
		super(context, R.layout.row_layout, values);
		this.context = context;
		this.values = values;
	}

	static class ViewHolder {
		public TextView userNameView;
		public TextView lastPostView;
		public TextView postedTimeAgoView;
		public ImageView imageView;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View followedUserView = convertView;
		if (followedUserView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			followedUserView = inflater.inflate(R.layout.followed_user_item_layout, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.userNameView = (TextView) followedUserView.findViewById(R.id.user_name);
			viewHolder.lastPostView = (TextView) followedUserView.findViewById(R.id.last_post);
			viewHolder.postedTimeAgoView = (TextView) followedUserView.findViewById(R.id.posted_time_ago);
			viewHolder.imageView = (ImageView) followedUserView.findViewById(R.id.user_icon);
			followedUserView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) followedUserView.getTag();
		holder.userNameView.setText(values.get(position).getName());
		holder.lastPostView.setText(values.get(position).getLastMicropost());
		holder.postedTimeAgoView.setText(values.get(position).getPostedTimeAgo());
		ImageView imageView = holder.imageView;
		String gravatarURL = "http://www.gravatar.com/avatar/"
				+ values.get(position).getGravatarId();
		Picasso.with(context).load(gravatarURL).into(imageView);
			
		
		return followedUserView;
	}

}
