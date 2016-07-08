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

import com.google.common.base.Throwables;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static sun.plugin.javascript.navig.JSType.Document;

/**
 * MongodbKit for mongodb 3.0
 */
public class MongodbKit {
    protected static Log logger = Log.getLog(MongoKit.class);

    private static MongoClient client;
    private static MongoDatabase defaultDb;

    public static void init(MongoClient client, String database) {
        MongodbKit.client = client;
        MongodbKit.defaultDb = client.getDatabase(database);
    }

    /**
     * 插入单个文档对象
     * <pre>
     *     MongodbKit.insertOne("name", record);
     * </pre>
     *
     * @param collectionName 集合
     * @param document       文档
     * @return
     */
    public static Boolean insertOne(String collectionName, Document document) {
        Boolean bool = false;
        try {
            getCollection(collectionName).insertOne(document);
            bool = true;
        } catch (MongoException e) {
            Throwables.propagate(e);
            logger.debug("insert a document fail.");
        } finally {
            return bool;
        }
    }

    /**
     * 插入多个文档对象
     * <pre>
     *     MongodbKit.insertMany("name", recordList);
     * </pre>
     *
     * @param collectionName 集合
     * @param docList        文档
     * @return
     */
    public static Boolean insertMany(String collectionName, List<Document> docList) {
        Boolean bool = false;
        try {
            getCollection(collectionName).insertMany(docList);
            bool = true;
        } catch (MongoException e) {
            Throwables.propagate(e);
            logger.debug("insert many document fail.");
        } finally {
            return bool;
        }
    }


    /**
     * 删除一个Mongo集合
     *
     * @param collectionName
     * @return
     */
    public static Boolean drop(String collectionName) {
        Boolean bool = false;
        try {
            getCollection(collectionName).drop();
            bool = true;
        } catch (MongoException e) {
            Throwables.propagate(e);
            logger.debug("drop collection fail.");
        } finally {
            return bool;
        }
    }

    /**
     * 删除一个Mongo集合和包含的索引信息
     *
     * @param collectionName
     * @return
     */
    public static Boolean dropAnddropIndex(String collectionName) {
        Boolean bool = false;
        try {
            getCollection(collectionName).drop();
            getCollection(collectionName).dropIndexes();
            bool = true;
        } catch (MongoException e) {
            Throwables.propagate(e);
            logger.debug("drop collection fail.");
        } finally {
            return bool;
        }
    }

    /**
     * 根据ObjectId查询文档对象
     *
     * @param collectionName 集合
     * @param objId          ObjectId
     * @return
     */
    public static Document findById(String collectionName, String objId) {
        MongoCursor<Document> cursor = getCollection(collectionName).find(new BasicDBObject("_id", new ObjectId(objId))).iterator();
        Document doc = null;
        while (cursor.hasNext()) {
            doc = cursor.next();
        }
        return doc;
    }

    /**
     * 查询单个Document对象
     *
     * @param collectionName 集合名称
     * @param filter         条件
     * @return
     */
    public static Document findOne(String collectionName, BasicDBObject filter) {
        MongoCursor<Document> cursor = getCollection(collectionName).find(filter).iterator();
        Document doc = null;
        while (cursor.hasNext()) {
            doc = cursor.next();
            break;
        }
        return doc;
    }


    /**
     * 分页查询
     *
     * @param collection
     * @param pageNumber
     * @param pageSize
     * @param filter
     * @param like
     * @param sort
     * @return
     */
    public static Page<Record> paginate(String collection, int pageNumber, int pageSize, BasicDBObject filter, BasicDBObject like, BasicDBObject sort) {
        BasicDBObject conditons = new BasicDBObject();
        buildFilter(filter, conditons);
        buildLike(like, conditons);
        MongoCursor<Document> cursor = getCollection(collection).find(conditons)
                .skip((pageNumber - 1) * pageSize).limit(pageSize).sort(sort(sort)).iterator();

        List<Record> records = new ArrayList<>();
        Long totalRow = getCollection(collection).count(conditons);
        while (cursor.hasNext()) {
            records.add(toRecord(cursor.next()));
        }
        if (totalRow <= 0) {
            return new Page<>(new ArrayList<Record>(0), pageNumber, pageSize, 0, 0);
        }
        Long totalPage = totalRow / pageSize;
        if (totalRow % pageSize != 0) {
            totalPage++;
        }
        Page<Record> page = new Page<>(records, pageNumber, pageSize, totalPage.intValue(), totalRow.intValue());
        return page;
    }

    private static BasicDBObject sort(BasicDBObject sort) {
        BasicDBObject bson = new BasicDBObject();
        if (sort != null) {
            Set<Map.Entry<String, Object>> entrySet = sort.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                Object val = entry.getValue();
                bson.put(key, "asc".equalsIgnoreCase(val + "") ? 1 : -1);
            }
        }
        return bson;
    }

    /**
     * ======================================================
     * ==================== Utils ====================
     * ======================================================
     */
    private static void buildLike(BasicDBObject like, BasicDBObject conditons) {
        if (like != null) {
            Set<Map.Entry<String, Object>> entrySet = like.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                Object val = entry.getValue();
                conditons.put(key, MongoKit.getLikeStr(val));
            }
        }
    }

    private static void buildFilter(BasicDBObject filter, BasicDBObject conditons) {
        if (filter != null) {
            Set<Map.Entry<String, Object>> entrySet = filter.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                Object val = entry.getValue();
                conditons.put(key, val);
            }
        }
    }

    public static Record toRecord(Document document) {
        Record record = new Record();
        record.setColumns(new BasicDBObject(document).toMap());
        return record;
    }

    public static BasicDBObject getLikeStr(Object findStr) {
        Pattern pattern = Pattern.compile("^.*" + findStr + ".*$", Pattern.CASE_INSENSITIVE);
        return new BasicDBObject("$regex", pattern);
    }

    public static MongoDatabase getDB() {
        return defaultDb;
    }

    public static MongoDatabase getDB(String dbName) {
        return client.getDatabase(dbName);
    }

    public static MongoCollection<Document> getCollection(String name) {
        return defaultDb.getCollection(name);
    }

    public static MongoCollection<Document> getDBCollection(String dbName, String collectionName) {
        return getDB(dbName).getCollection(collectionName);
    }

    public static MongoClient getClient() {
        return client;
    }

    public static void setMongoClient(MongoClient client) {
        MongodbKit.client = client;
    }
}
