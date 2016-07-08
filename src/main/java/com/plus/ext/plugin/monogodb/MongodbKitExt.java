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
package com.plus.ext.plugin.monogodb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.MongoCursor;
import com.plus.kit.FileKit;
import jodd.datetime.JDateTime;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于Mongodb做的扩展工具
 */
@Slf4j
public enum MongodbKitExt {
    ice;

    /**
     * 根据集合名称导出Mongodb文档数据到Csv文件
     *
     * @param collection   集合
     * @param tempFilePath 临时文件路径
     * @param fileName     导出文件名
     * @return
     */
    public Boolean exportMongodbDocument(String collection, String tempFilePath, String fileName) {
        Boolean bool = false;
        try {
            List<String> listStr = new ArrayList<>();   //最终文件字符串
            String headerLine;  //文件头
            List<String> headerList = new ArrayList<>();
            MongoCursor<Document> cursor = MongodbKit.getCollection(collection).find().iterator();

            //获取文件头
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                JSONObject json = JSON.parseObject(doc.toJson());
                for (String key : json.keySet()) {
                    headerList.add(key);
                }
                break;
            }
            headerLine = headerList.toString().replace("[", "").replace("]", "");
            listStr.add(headerLine);
            //获取文件头end

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < headerList.size(); i++) {
                    Object value = doc.get(headerList.get(i));
                    if (headerList.get(i).contains("time")) {
                        value = new JDateTime(Long.parseLong(value.toString())).toString("YYYY-MM-DD hh:mm:ss");
                    }
                    if (i == headerList.size()) {
                        sb.append((null != value) ? value.toString() : "");
                    } else {
                        sb.append((null != value) ? value.toString() : "").append(",");
                    }
                }
                listStr.add(sb.toString());
            }
            if (listStr.size() > 0) {
                bool = FileKit.createFile(tempFilePath, fileName, listStr);
            }
        } catch (Exception e) {
            log.debug("exportMongodbDocument-fail", e);
        } finally {
            return bool;
        }
    }
}
