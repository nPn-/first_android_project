package com.gmail.npnster.first_project;

import java.util.ArrayList;
import java.util.List;

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

public class UserDetailOptionsAdapter extends ArrayAdapter<UserDetailOption> {
	private final Context context;
	private final ArrayList<UserDetailOption> values;
	private final UserDetailView mView;

	public UserDetailOptionsAdapter(Context context, ArrayList<UserDetailOption> values, UserDetailView view) {
		super(context, R.layout.user_detail_option_row_layout, values);
		this.context = context;
		this.values = values;
		mView = view;
	}

	static class ViewHolder {
		public Switch mSwitch;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		System.out.println("creating a user detail option row");
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.user_detail_option_row_layout, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.mSwitch = (Switch) rowView.findViewById(R.id.enable_switch);
			viewHolder.mSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					mView.onOptionChanged(position, isChecked);
					
				}
				
			});

			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.mSwitch.setChecked(values.get(position).isEnabled());
		holder.mSwitch.setText(values.get(position).getDisplayName());

		return rowView;
	}

}
