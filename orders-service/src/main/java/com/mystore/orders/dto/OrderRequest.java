package com.mystore.orders.dto;

public class OrderRequest {

    private Integer productId;
    private Integer qty;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQty() {
        return qty;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "productId=" + productId +
                ", qty=" + qty +
                '}';
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}