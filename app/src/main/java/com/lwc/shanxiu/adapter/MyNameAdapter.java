package com.lwc.shanxiu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;

import java.util.ArrayList;
import java.util.List;

public class MyNameAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<String> privinces=new ArrayList<>();
	private String value="";
	private Context context;
	public MyNameAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	public void setData(ArrayList<String> list, String name) {
		privinces = list;
		value = name;
	}
	@Override
	public View getView(final int position, View convertView,
			ViewGroup parent) {
		View rowView = convertView;
		ViewHolder holder;
		if (rowView == null) {
			rowView = inflater.inflate(R.layout.item_list_province, null);
			holder = new ViewHolder();
			holder.name = (TextView) rowView.findViewById(R.id.name);
			holder.iv_select = (ImageView)rowView.findViewById(R.id.iv_select);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		String name = privinces.get(position);
		holder.name.setText(name);
		if (value != null && name.equals(value)) {
			holder.name.setTextColor(this.context.getResources().getColor(R.color.btn_blue_nomal));
			holder.iv_select.setVisibility(View.VISIBLE);
		} else {
			holder.iv_select.setVisibility(View.GONE);
			holder.name.setTextColor(this.context.getResources().getColor(R.color.black_66));
		}
		return rowView;
	}

	public class ViewHolder {
		public ImageView iv_select;
		public TextView name;
	}

	@Override
	public int getCount() {
		return privinces.size();
	}

	@Override
	public String getItem(int arg0) {
		return privinces.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
}