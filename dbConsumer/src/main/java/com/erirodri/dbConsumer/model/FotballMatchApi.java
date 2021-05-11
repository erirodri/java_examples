package com.erirodri.dbConsumer.model;

import java.util.List;
import java.util.Map;


public class FotballMatchApi {
    int page;
    int per_page;
    int total;
    int total_pages;
    List<Map<String,Object>> data;

    public FotballMatchApi() {
    }

    public FotballMatchApi(int page, int per_page, int total, int total_pages, List<Map<String, Object>> data) {
        this.page = page;
        this.per_page = per_page;
        this.total = total;
        this.total_pages = total_pages;
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = this.total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

}
