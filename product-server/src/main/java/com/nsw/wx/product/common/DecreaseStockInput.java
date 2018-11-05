package com.nsw.wx.product.common;

import lombok.Data;

/**
 * 购物车
 *
 * @author 张维维
 * description:
 * path: nsw-wx-product-com.nsw.wx.product.common-DecreaseStockInput
 * date: 2018/10/18/018 17:40
 * version: 02.06
 * To change this template use File | Settings | File Templates.
 */
    @Data
    public class DecreaseStockInput {

        private String productId;

        private Integer productQuantity;

        public DecreaseStockInput() {
        }
        public DecreaseStockInput(String productId, Integer productQuantity) {
            this.productId = productId;
            this.productQuantity = productQuantity;
        }

}
