package com.aiosp.uinterface1;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
//@Qualifier("UappInterface1ImplDefault")
public class UappInterface1ImplDefault implements UappInterface1<String> {

    @Override
    public String showVersion() {
        return "UappInterface1ImplDefault in uapp1";
    }


    @Override
    public List<String> getList(String vm) {

        List<String> list = new ArrayList<>();
        return list;
    }

    @Override
    public UappResponseModel getResponse(String vm) {
        return null;
    }
}
