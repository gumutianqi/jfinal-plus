package com.plus.ext.handler;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionHandler extends Handler {

    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {

        Integer index = target.indexOf(";jsessionid".toUpperCase());

        if (index != -1)
            target = target.substring(0, index);

        next.handle(target, request, response, isHandled);
    }
}
