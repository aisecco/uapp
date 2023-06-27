package com.aiosp.uinterface1;


import java.util.List;

public interface UappInterface1<T> {
     String showVersion();
     List<T> getList(T vm);
     UappResponseModel getResponse (T vm);
}
