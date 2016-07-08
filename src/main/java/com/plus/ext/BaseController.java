/**
 * Copyright (c) 2009-2016, LarryKoo 老古 (gumutianqi@gmail.com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plus.ext;

import com.alibaba.fastjson.JSON;
import com.jfinal.kit.StrKit;
import com.jfinal.render.Render;
import com.jfinal.render.RenderFactory;
import com.plus.ext.route.ControllerBind;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller 继承JFinal核心的Controller，作为扩展
 */
@Slf4j
public abstract class BaseController extends com.jfinal.core.Controller {

    ControllerBind controller;

//    public BaseController() {
//        Field[] fields = this.getClass().getDeclaredFields();
//        for (int i = 0; i < fields.length; i++) {
//            Field field = fields[i];
//            Class clazz = field.getType();
//            if (Service.class.isAssignableFrom(clazz) && clazz != Service.class) {
//                try {
//                    if (!field.isAccessible()) {
//                        field.setAccessible(true);
//                    }
//
//                    field.set(this,Service.getInstance(clazz, this));
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException();
//                }
//            }
//        }
//    }

    public BaseController() {
        controller = this.getClass().getAnnotation(ControllerBind.class);
    }

    protected static RenderFactory renderFactory = RenderFactory.me();

    public Render getRender() {
        if (ReturnKit.isJson(getRequest()) && !(ReturnKit.isJson(super.getRender()))) {
            return renderFactory.getJsonRender();
        }
        return super.getRender();
    }

    public void forwardAction(String msg, String url) {
        setAttr("errmsg", msg);
        forwardAction(url);
    }

    public void render(String msg, String url) {
        setAttr("errmsg", msg);
        render(url);
    }

    public void renderTop(String url) {
        renderHtml("<html><script>window.open('" + url + "','_top')</script></html>");
    }

    public void renderFastJson(Object obj) {
        renderJson(JSON.toJSONString(obj));
    }

    public void renderWechatXML(String resXml) {
        renderText(resXml, "text/xml");
    }

    /**
     * 获取前端传来的数组对象Model
     *
     * @param modelClass
     * @param <T>
     * @return
     */
    public <T> List<T> getModels(Class<T> modelClass) {
        return getModels(modelClass, StrKit.firstCharToLowerCase(modelClass.getSimpleName()));
    }

    /**
     * 获取前端传来的数组对象并响应成Model列表
     */
    public <T> List<T> getModels(Class<T> modelClass, String modelName) {
        List<String> indexes = getIndexes(modelName);
        List<T> list = new ArrayList<T>();
        for (String index : indexes) {
            T m = getModel(modelClass, modelName + "[" + index + "]");
            if (m != null) {
                list.add(m);
            }
        }
        return list;
    }

    /**
     * 提取model对象数组的标号
     */
    private List<String> getIndexes(String modelName) {
        // 提取标号
        List<String> list = new ArrayList<>();
        String modelNameAndLeft = modelName + "[";
        Map<String, String[]> parasMap = getRequest().getParameterMap();
        for (Map.Entry<String, String[]> e : parasMap.entrySet()) {
            String paraKey = e.getKey();
            if (paraKey.startsWith(modelNameAndLeft)) {
                String no = paraKey.substring(paraKey.indexOf("[") + 1,
                        paraKey.indexOf("]"));
                if (!list.contains(no)) {
                    list.add(no);
                }
            }
        }
        return list;
    }

    // --------

    /**
     * Returns the value of a request parameter and convert to BigInteger.
     * @param name a String specifying the name of the parameter
     * @return a BigInteger representing the single value of the parameter
     */
    public BigInteger getParaToBigInteger(String name){
        return this.toBigInteger(getPara(name), null);
    }

    /**
     * Returns the value of a request parameter and convert to BigInteger with a default value if it is null.
     * @param name a String specifying the name of the parameter
     * @return a BigInteger representing the single value of the parameter
     */
    public BigInteger getParaToBigInteger(String name,BigInteger defaultValue){
        return this.toBigInteger(getPara(name), defaultValue);
    }

    private BigInteger toBigInteger(String value, BigInteger defaultValue) {
        if (value == null || "".equals(value.trim()))
            return defaultValue;
        return (new BigInteger(value));
    }

    /**
     * Reflect Exception
     * @param e
     */
    public abstract void onExceptionError(Exception e);
}
