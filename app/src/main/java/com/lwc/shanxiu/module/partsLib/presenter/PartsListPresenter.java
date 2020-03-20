package com.lwc.shanxiu.module.partsLib.presenter;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.partsLib.ui.bean.PartsBean;
import com.lwc.shanxiu.module.partsLib.ui.view.PartsListView;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author younge
 * @date 2018/12/26 0026
 * @email 2276559259@qq.com
 */
public class PartsListPresenter {

    private Activity activity;
    private PartsListView partsListView;
    private int pagesAll = 1;
    public PartsListPresenter(Activity activity, PartsListView partsListView){
        this.activity = activity;
        this.partsListView =  partsListView;
    }

    /**
     * 查找配件信息
     * @param typeId 配件分类
     * @param searchText 搜索内容
     * @param otherSearch 其它条件
     * @param sortType 排序(价格) 1(顺序)/ 2(倒序)  0不排序
     */
    public void searchPartsData(String typeId,String searchText,String otherSearch,String sortType,int currentPages){
     /*   if(currentPages > pagesAll){
            return;
        }*/
        Map<String,String> params = new HashMap<>();
        params.put("type_id",typeId);
        params.put("vague_accessories_name",searchText); //配件名称
        params.put("regexp_attribute_name",otherSearch);  //其它筛选条件
        params.put("curPage",String.valueOf(currentPages));

        StringBuilder sb = new StringBuilder("?sortord="+sortType);

        HttpRequestUtils.httpRequest(activity, "searchPartsData", RequestValue.GET_ACCESSORIES_ALL+sb.toString(),params, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        try {
                            JSONObject jsonObject = new JSONObject(JsonUtil.getGsonValueByKey(response, "data"));
                            pagesAll = Integer.parseInt(jsonObject.optString("pages"));
                            List<PartsBean> partsBeanList = JsonUtil.parserGsonToArray(jsonObject.optString("data"), new TypeToken<ArrayList<PartsBean>>() {});
                               partsListView.onLoadDataList(partsBeanList);
                        } catch (Exception e) {
                            e.printStackTrace();
                            partsListView.onLoadError(e.getMessage());
                        }
                        break;
                    default:
                        partsListView.onLoadError(common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                partsListView.onLoadError(msg);
            }
        });
    }
}
