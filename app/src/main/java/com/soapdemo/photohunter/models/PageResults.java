package com.soapdemo.photohunter.models;

import java.util.List;

public class PageResults<T> {
    public int total;
    public int total_pages;
    public List<T> results;
}
