package com.lwc.shanxiu.module.partsLib.ui.view;


import com.lwc.shanxiu.module.partsLib.ui.bean.PartsBean;

import java.util.List;

/**
 * @author younge
 * @date 2018/12/27 0026
 * @email 2276559259@qq.com
 */
public interface PartsListView {

    /**
     * 加载配件列表
     * @param partsBeenList
     */
    void onLoadDataList(List<PartsBean> partsBeenList);

    /**
     * 请求或处理异常
     * @param msg
     */
    void onLoadError(String msg);
}
