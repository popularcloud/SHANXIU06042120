package com.lwc.shanxiu.module.addressmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;

import java.util.List;

/**
 * Author Bro0cL on 2016/12/16.
 * 城市选择
 */
public class CityPickerActivity extends BaseActivity implements View.OnClickListener {
    public static final String KEY_PICKED_CITY = "picked_city";

    private LinearLayout mSearchLayout;
    private LinearLayout mLeftLayout;
    private ListView mListView;
    private ListView mResultListView;
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
   // private TextView cancelBtn;
    private ViewGroup emptyView;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<City> mAllCities;
    private List<City> mResultCities;
    private DBManager dbManager;

    public static final Boolean HIDE_ALL_CITY = false;

    private AMapLocationClient mLocationClient;
    private String city_str;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.cp_activity_city_list;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void init() {
        initData();
        initView();
        initLocation();
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }

    private void initLocation() {
      /*  mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String province = aMapLocation.getProvince();
                        String city = aMapLocation.getCity();
                        String district = aMapLocation.getDistrict();
                        String location = StringUtils.extractLocation(city, district);
                        mCityAdapter.updateLocateState(LocateState.SUCCESS, location);
                    } else {
                        //定位失败
                        mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    }
                }
            }
        });
        mLocationClient.startLocation();*/
      city_str = getIntent().getStringExtra("city_str");
      setTitle("选择城市");
      mCityAdapter.updateLocateState(LocateState.SUCCESS, city_str);
    }

    private void initData() {
        dbManager = new DBManager(this);
        dbManager.copyDBFile();
        mAllCities = dbManager.getAllCities();
//        mAllCities.clear();
        mCityAdapter = new CityListAdapter(this, mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                backWithData(name);
            }

            @Override
            public void onLocateClick() {
              //  mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                //requestPermissions(CityPickerActivity.this, neededPermissions, CityPickerActivity.this);
            }
        });

        mResultAdapter = new ResultListAdapter(this, null);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview_all_city);
        mListView.setAdapter(mCityAdapter);
        mSearchLayout = (LinearLayout) findViewById(R.id.search_layout);
        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        searchBox = (EditText) findViewById(R.id.et_search);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    mResultCities = dbManager.searchCity(keyword);
                    if (mResultCities == null || mResultCities.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(mResultCities);
                    }
                }
            }
        });

        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                backWithData(mResultAdapter.getItem(position).getName());
            }
        });

        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
       // cancelBtn = (TextView) findViewById(R.id.tv_search_cancel);

        clearBtn.setOnClickListener(this);
        //cancelBtn.setOnClickListener(this);

        if (HIDE_ALL_CITY) {
            mSearchLayout.setVisibility(View.GONE);
            mLetterBar.setVisibility(View.GONE);
        }
    }

    private void backWithData(String city){
        Intent data = new Intent();
        data.putExtra(KEY_PICKED_CITY, city);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_search_clear) {
            searchBox.setText("");
            clearBtn.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
//            mResultListView.setVisibility(View.GONE);
            mResultCities = null;
        }/* else if (i == R.id.tv_search_cancel) {
            finish();
        }*/
    }
}