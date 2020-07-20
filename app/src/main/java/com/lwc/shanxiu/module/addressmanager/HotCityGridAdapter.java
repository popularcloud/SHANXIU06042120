package com.lwc.shanxiu.module.addressmanager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.lwc.shanxiu.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * author zaaach on 2016/1/26.
 */
public class HotCityGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mCities;

    public HotCityGridAdapter(Context context) {
        this.mContext = context;
        mCities = new ArrayList<>();
        mCities.add("广州");
        mCities.add("东莞");
        mCities.add("惠州");
        mCities.add("肇庆");
        List<String> sharedCities = getAllCities(context);
        if (sharedCities.size() > 0) {
            mCities.clear();
            mCities.addAll(sharedCities);
        }
    }

    public List<String> getAllCities(Context context){
        List<String> cityList=new ArrayList<String>();
//        String cityString = MyShareUtil.getSharedString(context, "cityList");
        String cityString = "[\"广州\",\"东莞\",\"惠州\",\"肇庆\"]";
        try {
            JSONArray jsonArray=new JSONArray(cityString);
            for (int i = 0; i < jsonArray.length(); i++) {
                cityList.add(jsonArray.get(i).toString());
            }
            return cityList;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取app的版本号
     *
     * @return 返回当前的版本号
     */
     private int getVersionCode() {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public String getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        HotCityViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.cp_item_hot_city_gridview, parent, false);
            holder = new HotCityViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_hot_city_name);
            view.setTag(holder);
        }else{
            holder = (HotCityViewHolder) view.getTag();
        }
        holder.name.setText(mCities.get(position));
        return view;
    }

    public static class HotCityViewHolder{
        TextView name;
    }
}
