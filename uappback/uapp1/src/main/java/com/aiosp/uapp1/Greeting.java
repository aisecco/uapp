package com.aiosp.uapp1;

/**
 * 自定类加载器
 *
 * @author
 * @date 2023/06/07 16:23
 **/

public class Greeting {
    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;

    public Greeting(long incrementAndGet, String format) {
        id = incrementAndGet;
        content = format;
    }
}
