package com.plzEnterCompanyName.HDQS.util;

import java.util.List;
import java.util.Objects;

public class Configurations {
    /* No Configurations instance for you. */
    private Configurations() {
    }

    /**
     * 注释参见Configurations#orderName(List<Configuration> src, String moduleType,
     * String name)
     *
     * @param src        从此集合中进行点名
     * @param moduleType 按该值匹配name属性
     * @param name       按该值匹配key为name的property
     * @return 如果有符合name条件的子集，第一个将会被返回
     */
    public static Configuration strictOrderName(List<Configuration> src, String moduleType, String name) {
        Configuration c = orderName(src, moduleType, name);
        if (c == null) {
            throw new ConfigurationNotFoundException("Cannot found " + name);
        }
        return c;
    }

    /**
     * 用于处理多个同类型的{@code Configuration}。
     *
     * <p>通常情况下，Configuration实例内部可以放入若干个子集，从而形成嵌套结构。子
     * 集们有自己的{@code name}属性，用于区分不同的子集类型。但当遇到该属性重复时，即单
     * 个{@code Configuration}中拥有多个同名子集，这时传统的{@code getSubs()}的使用较为
     * 不便。此方法便是解决此问题的。
     *
     * <p>一般而言，调用{@link Configuration#getName()}方法的返回值称为这个Configuration
     * 实例的名字。
     *
     * <p>该方法要求传入多个{@code Configuration}，传入值{@code moduleType}代表同名子集的名
     * 字，还要求子集中必须有一个key为{@code name}的property，满足上述条件的子集会缓存在一个
     * 集合中以待查询。随后，该方法遍历这个集合，逐个检测子集中key为{@code name}的property
     * 的value是否为传入值{@code name}。当遇到符合条件的子集时，将它返回。
     *
     * @param src        从此集合中进行点名
     * @param moduleType 按该值匹配name属性
     * @param name       按该值匹配key为name的property
     * @return 如果有符合name条件的子集，第一个将会被返回
     */
    public static Configuration orderName(List<Configuration> src, String moduleType, String name) {
        Objects.requireNonNull(src);
        FormatCheck.specialString(moduleType,
                FormatCheck.ZERO_LENGTH + FormatCheck.NULL + FormatCheck.NEW_LINE);
        FormatCheck.specialString(name,
                FormatCheck.ZERO_LENGTH + FormatCheck.NULL + FormatCheck.NEW_LINE);
        for (Configuration c : src) {
            if (name.equals(c.get("name"))) {
                return c;
            }
        }
        return null;
    }
}
