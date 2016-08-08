package com.plus.ext.handler;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionHandler extends Handler {

    /**
     * From 如梦技术 改良
     */
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        // L.cm update by 2014-08-06 更精准的判断
        boolean isFromURL = request.isRequestedSessionIdFromURL();
        if (isFromURL) {
            target = target.substring(0, target.indexOf(';'));
        }
        next.handle(target, request, response, isHandled);
    }
}
