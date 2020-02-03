package com.yss;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {

    private final List<Filter> filters = new ArrayList<>();

    public FilterChain(){
        filters.add(new Filter1());
        filters.add(new Filter2());
    }

    public void processData(Request request){
        for (Filter filter : filters){
            filter.doFilter(request);
        }
    }

}
