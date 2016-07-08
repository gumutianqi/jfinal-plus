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

import com.google.common.base.Throwables;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * google 开源图形码工具Zxing使用
 */
@Slf4j
public class ZxingKit {
    /**
     * Zxing图形码生成工具
     *
     * @param contents        内容
     * @param barcodeFormat   BarcodeFormat对象
     * @param format          图片格式，可选[png,jpg,bmp]
     * @param width           宽
     * @param height          高
     * @param margin          边框间距px
     * @param saveImgFilePath 存储图片的完整位置，包含文件名
     * @return
     */
    public Boolean encode(String contents, BarcodeFormat barcodeFormat, Integer margin, ErrorCorrectionLevel errorLevel, String format, int width, int height, String saveImgFilePath) {
        Boolean bool;
        BufferedImage bufImg;
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, errorLevel);
        hints.put(EncodeHintType.MARGIN, margin);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
//            contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, barcodeFormat, width, height, hints);
            MatrixToImageConfig config = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
            bufImg = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
            bool = this.writeToFile(bufImg, format, saveImgFilePath);
        } catch (Throwable t) {
            log.error("encode-ex:{}", t);
            throw Throwables.propagate(t);
        }
        return bool;
    }

    /**
     * @param srcImgFilePath 要解码的图片地址
     * @return
     */
    public Result decode(String srcImgFilePath) {
        Result result = null;
        BufferedImage image;
        try {
            File srcFile = new File(srcImgFilePath);
            image = ImageIO.read(srcFile);
            if (null != image) {
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                Hashtable hints = new Hashtable();
                hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
                result = new MultiFormatReader().decode(bitmap, hints);
            } else {
                log.debug("Could not decode image.");
            }
        } catch (Throwable t) {
            log.error("decode-ex:{}", t);
            throw Throwables.propagate(t);
        } finally {
            return result;
        }
    }

    /**
     * 将BufferedImage对象写入文件
     *
     * @param bufImg          BufferedImage对象
     * @param format          图片格式，可选[png,jpg,bmp]
     * @param saveImgFilePath 存储图片的完整位置，包含文件名
     * @return
     */
    public Boolean writeToFile(BufferedImage bufImg, String format, String saveImgFilePath) {
        Boolean bool = false;
        try {
            bool = ImageIO.write(bufImg, format, new File(saveImgFilePath));
        } catch (Throwable t) {
            log.error("writeToFile-ex:{}", t);
            throw Throwables.propagate(t);
        } finally {
            return bool;
        }
    }
}
