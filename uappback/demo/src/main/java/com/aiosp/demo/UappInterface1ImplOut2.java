package com.aiosp.demo;

import com.aiosp.uinterface1.UappInterface1;
import com.aiosp.uinterface1.UappModel;
import com.aiosp.uinterface1.UappResponseModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("UappInterface1ImplOut2")
public class UappInterface1ImplOut2 implements UappInterface1<UappModel> {

    @Override
    public String showVersion() {
        return "UappInterface1ImplOut2 in demo application";
    }

    @Override
    public List<UappModel> getList(UappModel vm) {
        return null;
    }

    @Override
    public UappResponseModel getResponse(UappModel vm) {

        UappResponseModel responseModel = new UappResponseModel();
        responseModel.setCode(200);
        responseModel.setMessage("UappInterface1ImplOut2");

        List<UappModel> list = new ArrayList<>();

        //Here is different!
        UappModelOut2 uappModelOut2 = new UappModelOut2();
        list.add( uappModelOut2 );

        responseModel.setResult(list);

        return responseModel;
    }
}
