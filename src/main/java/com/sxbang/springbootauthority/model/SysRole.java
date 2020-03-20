package com.sxbang.springbootauthority.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity<Integer> {

    private static final long seriaVersionUID = -6525908145032868837L;

    private String name;
    private String description;
    private List<Long> permissionIds;

    @Override
    public String toString() {
        return "SysRole{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
