package com.aiosp.demo;

import com.aiosp.uapp1.UappModelExt1;
import com.aiosp.uinterface1.UappInterface1;
import com.aiosp.uinterface1.UappModel;
import com.aiosp.uinterface1.UappResponseModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("UappInterface1ImplOut1")
public class UappInterface1ImplOut1 implements UappInterface1<UappModel> {

    @Override
    public String showVersion() {
        return "UappInterface1ImplOut1 in demo application";
    }


    @Override
    public List<UappModel> getList(UappModel vm) {

        List<UappModel> list = new ArrayList<UappModel>();
        UappModelOut1 uappModelOut1 = new UappModelOut1();
        list.add( uappModelOut1 );
        return list;
    }

    @Override
    public UappResponseModel getResponse(UappModel vm) {

        UappResponseModel responseModel = new UappResponseModel();
        responseModel.setCode(200);
        responseModel.setMessage("UappInterface1ImplOut1");

        List<UappModel> list = new ArrayList<>();

        //
        UappModelOut1 uappModelOut1 = new UappModelOut1();
        list.add( uappModelOut1 );
        responseModel.setResult(list);

        return responseModel;
    }


}
