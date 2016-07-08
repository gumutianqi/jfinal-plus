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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageKit {
    /**
     * 转换分页数据信息
     *
     * @param pageNumber      当前页码
     * @param limit           每页显示多少条
     * @param _paginationSize 最多显示的页码
     * @param totalRow        总数据量
     * @param totalPage       总页数
     * @return
     */
    public static Map<String, Object> convertPage(Integer pageNumber, Integer limit, Integer _paginationSize, Integer totalRow, Integer totalPage, Boolean hasInfo) {
        Map<String, Object> map = new HashMap<>();

        boolean _hasPrev = true, _hasNext = true;

        if (pageNumber == 1) _hasPrev = false;

        if (pageNumber == totalPage) _hasNext = false;
        Integer _begin = Math.max(1, pageNumber - _paginationSize / 2);

        Integer _end = Math.min(_begin + (_paginationSize - 1), totalPage);
        List<Integer> _list = new ArrayList<>();

        for (int i = 0; i <= (_end - _begin); i++) {
            _list.add(_begin + i);
        }

        map.put("limit", limit);
        map.put("pageNumber", pageNumber);
        map.put("totalRow", totalRow);
        map.put("totalPage", totalPage);
        map.put("hasPreviousPage", _hasPrev);
        map.put("hasNextPage", _hasNext);
        map.put("numList", _list);
        map.put("hasInfo", hasInfo);

        return map;
    }
}
