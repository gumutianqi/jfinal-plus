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
package com.plus.ext.plugin.quartz;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.druid.DruidPlugin;
import org.quartz.utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName: QuartzConnectionProvider
 * @Description：自定义的 QuartzConnectionProvider
 * @author koo@weteam.me
 * @date 2014年4月18日 下午6:17:23
 *
 */
public class QuartzConnectionProvider implements ConnectionProvider {

    private static final String DB_CONFIG = "quartz_conn.properties";

    private DruidPlugin druidPlugin;

    @Override
    public Connection getConnection() throws SQLException {
        return druidPlugin.getDataSource().getConnection();
    }

    @Override
    public void initialize() throws SQLException {
        Prop prop = PropKit.use(DB_CONFIG);
        druidPlugin = new DruidPlugin(prop.get("jdbcUrl"), prop.get("user"), prop.get("password"));

        // StatFilter提供JDBC层的统计信息
        druidPlugin.addFilter(new StatFilter());
        // WallFilter的功能是防御SQL注入攻击
        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType("mysql");
        druidPlugin.addFilter(wallFilter);
        druidPlugin.start();
    }

    @Override
    public void shutdown() throws SQLException {
        druidPlugin.stop();
    }

}
