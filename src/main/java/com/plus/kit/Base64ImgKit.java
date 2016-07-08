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
package com.plus.kit;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.*;

/**
 * 实现Base64字符串与图片文件之间的互转
 */
@Slf4j
public class Base64ImgKit {
    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @return
     */
    public static String GetImageStr(String imgFilePath) {
        byte[] data = null;
        //读取图片字节数组
        try (InputStream in = new FileInputStream(imgFilePath)) {
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
        //对字节数组Base64编码
        return Base64.encodeBase64String(data);//返回Base64编码过的字节数组字符串
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     */
    public Boolean GenerateImage(String imgStr, String imgFilePath) {
        if (imgStr == null) {//图像数据为空
            return false;
        }

        //生成jpeg图片
        try (OutputStream out = new FileOutputStream(imgFilePath)) {
            //Base64解码
            byte[] b = Base64.decodeBase64(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }

            out.write(b);
            out.flush();
            return true;
        } catch (Exception e) {
//            throw Exceptions.unchecked(e);
            log.error("generate-image fail: {}", e);
            return false;
        }
    }
}
