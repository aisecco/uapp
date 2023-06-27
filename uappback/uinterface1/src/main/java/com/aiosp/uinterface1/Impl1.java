package com.aiosp.uinterface1;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Impl1  {

    public String showVersion() {
        return "Impl1 in uinterface1";
    }
}
