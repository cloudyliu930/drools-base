package com.ygts.security.riskcontrol.ruleengine.server.fact;

/**
 * @author cloudy
 * @createTime 2018/12/26
 * @description 变量字段
 */
public class VariableField<T> {

    /**
     * 对象类型
     */
    private String valueType;

    /**
     * 对象名称
     */
    private String name;

    /**
     * 对象显示名称
     */
    private String displayName;

    /**
     * 对象值
     */
    private T value;

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "VariableField{" +
                "valueType='" + valueType + '\'' +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", value=" + value +
                '}';
    }
}
