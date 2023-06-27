package com.aiosp.uapp1;

import com.aiosp.uinterface1.UappInterface1;
import com.aiosp.uinterface1.UappModel;
import com.aiosp.uinterface1.UappResponseModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("Impl4Interface1")
public class Impl4Interface1 implements UappInterface1<UappModel> {

    @Override
    public String showVersion() {
        return "implements in uapp1";
    }


    @Override
    public List<UappModel> getList(UappModel vm) {

        List<UappModel> list = new ArrayList<UappModel>();
        return list;
    }

    @Override
    public UappResponseModel getResponse(UappModel vm) {
        UappResponseModel responseModel = new UappResponseModel();
        responseModel.setCode(200);
        responseModel.setMessage("Impl4Interface1 in uapp1");

        List<UappModel> list = new ArrayList<>();

        //Here is different!
        UappModelExt1 uappModelExt1 = new UappModelExt1();
        list.add(uappModelExt1);

        responseModel.setResult(list);

        return responseModel;
    }
}
