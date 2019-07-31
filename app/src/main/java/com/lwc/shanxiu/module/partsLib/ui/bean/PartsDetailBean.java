package com.lwc.shanxiu.module.partsLib.ui.bean;

import java.io.Serializable;

/**
 * @author younge
 * @date 2019/1/14 0014
 * @email 2276559259@qq.com
 */
public class PartsDetailBean implements Serializable {

    /**
     * accessoriesName : 配件名称
     * accessoriesPrice : 配件价格
     * accessoriesNum : 配件数量
     */

    private String accessoriesName;
    private String accessoriesPrice;
    private String accessoriesNum;

    public String getAccessoriesName() {
        return accessoriesName;
    }

    public void setAccessoriesName(String accessoriesName) {
        this.accessoriesName = accessoriesName;
    }

    public String getAccessoriesPrice() {
        return accessoriesPrice;
    }

    public void setAccessoriesPrice(String accessoriesPrice) {
        this.accessoriesPrice = accessoriesPrice;
    }

    public String getAccessoriesNum() {
        return accessoriesNum;
    }

    public void setAccessoriesNum(String accessoriesNum) {
        this.accessoriesNum = accessoriesNum;
    }
}
