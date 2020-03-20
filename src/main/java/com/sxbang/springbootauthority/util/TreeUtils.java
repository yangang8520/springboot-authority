package com.sxbang.springbootauthority.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxbang.springbootauthority.model.SysPermission;

import java.util.List;

public class TreeUtils {

    /**
     * 菜单树
     *
     * @param parentId
     * @param permissionAll
     * @param array
     */
    public static void setPermissionsTree(Integer parentId, List<SysPermission> permissionAll, JSONArray array){
        for (SysPermission per : permissionAll) {
            Integer id = per.getParentId();
            if (per.getParentId().equals(parentId)) {
                String string = JSONObject.toJSONString(per);
                JSONObject parent = (JSONObject) JSONObject.parse(string);
                array.add(parent);
                if (permissionAll.stream().filter(p -> p.getParentId().equals(per.getId())).findAny() != null) {
                    JSONArray child = new JSONArray();
                    parent.put("child",child);
                    setPermissionsTree(per.getId(), permissionAll, child);
                }
            }
        }
    }
}
