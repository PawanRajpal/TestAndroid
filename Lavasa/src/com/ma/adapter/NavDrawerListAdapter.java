package com.ma.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ma.bean.NaviationParentItems;
import com.ma.lavasa.R;

public class NavDrawerListAdapter extends BaseExpandableListAdapter {

	private final Activity _context;
	private final List<NaviationParentItems> parentItems;
	private int selectedIndex;
	private int selectedChild;
	private int image[];

	public NavDrawerListAdapter(Activity context, List<NaviationParentItems> parentItems, int img[]) {
		super();
		this._context = context;
		this.parentItems = parentItems;
		selectedIndex = -1;
		image = img;
	}

	public void setSelectedIndex(int grp, int child)
	{
		selectedIndex = grp;
		selectedChild = child;
		notifyDataSetChanged();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return parentItems.get(groupPosition).getEntities().get(childPosition);

	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return parentItems.get(groupPosition).getEntities().get(childPosition).getId();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean arg2, View convertView, ViewGroup parentView) {

		if(convertView==null){
			LayoutInflater layoutInflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.drawer_child_list_item, null); 
		}

		TextView textView = (TextView) convertView.findViewById(R.id.tv_drawerchild);
		RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.lv_child_view);

		textView.setText(parentItems.get(groupPosition).getEntities().get(childPosition).getName());

		if(selectedIndex!= -1 && groupPosition == selectedIndex && childPosition == selectedChild)
		{
			relativeLayout.setBackgroundColor(_context.getResources().getColor(R.drawable.selected));
		}
		else
		{
			relativeLayout.setBackgroundColor(_context.getResources().getColor(R.drawable.notselected));
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return parentItems.get(groupPosition).getEntities().size();

	}

	@Override
	public Object getGroup(int groupPosition) {

		return parentItems.get(groupPosition);
	}

	@Override
	public int getGroupCount() {

		return parentItems.size();
	}

	@Override
	public long getGroupId(int groupPosition) {

		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,	View convertView, ViewGroup parent) {

		if(convertView==null){
			LayoutInflater layoutInflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.drawer_list_item, null);
		}

		TextView  textView = (TextView)convertView.findViewById(R.id.title);
		ImageView imageView = (ImageView)convertView.findViewById(R.id.icon);
		textView.setText(parentItems.get(groupPosition).getName());
		imageView.setBackgroundResource(image[groupPosition]);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
