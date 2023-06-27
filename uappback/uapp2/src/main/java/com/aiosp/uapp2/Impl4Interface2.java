package com.aiosp.uapp2;

import com.aiosp.uinterface1.UappInterface1;
import com.aiosp.uinterface1.UappResponseModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("Impl4Interface2")
public class Impl4Interface2 implements UappInterface1<String> {

    @Override
    public String showVersion() {
        return "implements in uapp2";
    }

    @Override
    public List<String> getList(String vm) {
        return null;
    }

    @Override
    public UappResponseModel getResponse(String vm) {
        return null;
    }
}
