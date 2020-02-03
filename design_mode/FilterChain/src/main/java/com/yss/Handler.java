package com.yss;

public class Handler {

    public void handlerRequest(Request request){
        FilterChain chain = new FilterChain();
        chain.processData(request);
    }

}
