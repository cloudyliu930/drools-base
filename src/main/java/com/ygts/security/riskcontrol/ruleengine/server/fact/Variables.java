package com.ygts.security.riskcontrol.ruleengine.server.fact;

import com.cloudy.constant.IConstants;
import com.cloudy.util.ArithUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author cloudy
 * @createTime 2018/12/26
 * @description 变量Fact对象
 */
public class Variables extends HashMap<String, VariableField> {

    /**
     * 添加新的数据
     * @param key 属性名
     * @param value 数据
     * @param valueType 数据类型
     * @param displayName 显示名称
     */
    public void put(String key, Object value, String valueType, String displayName) {
        VariableField variableField = new VariableField();
        variableField.setName(key);
        variableField.setValue(value);
        variableField.setValueType(valueType);
        variableField.setDisplayName(displayName);

        super.put(key, variableField);
    }

    /**
     * 添加数据，便于规则引擎添加
     * @param key 属性名
     * @param value 数据
     * @param valueType 数据类型
     * @param displayName 显示名称
     */
    public void put(String key, String value, String valueType, String displayName) {
        VariableField variableField = new VariableField();
        variableField.setName(key);
        variableField.setValue(value);
        variableField.setValueType(valueType);
        variableField.setDisplayName(displayName);

        super.put(key, variableField);
    }

    /**
     * 存在数据追加数据，不存在新增数据
     * @param key 属性名
     * @param value 数据
     * @param valueType 数据类型
     * @param displayName 显示名称
     */
    public void append(String key, String value, String valueType, String displayName) {
        VariableField variableField = null;
        if (super.containsKey(key) && Objects.nonNull(super.get(key).getValue())) {
            variableField = super.get(key);
            // 获取旧的数据
            Object oldValue = this.get(variableField.getName(), variableField.getValueType(), variableField.getDisplayName());

            if (isString(variableField.getValueType())) {
                variableField.setValue(((String) oldValue) + value);
            }

            if (isInteger(variableField.getValueType())) {
                variableField.setValue(((Integer) oldValue) + value);
            }

            if (isDouble(variableField.getValueType())) {
                variableField.setValue(ArithUtils.add(oldValue.toString(), value).doubleValue());
            }

            if (isFloat(variableField.getValueType())) {
                variableField.setValue(ArithUtils.add(oldValue.toString(), value).floatValue());
            }

            if (isLong(variableField.getValueType())) {
                variableField.setValue(((Long) oldValue) + value);
            }

            if (isList(variableField.getValueType())) {
                variableField.setValue(((List)oldValue).addAll(Arrays.asList(value.split(","))));
            }

            if (isBigDecimal(variableField.getValueType())) {
                variableField.setValue(ArithUtils.add(oldValue.toString(), value));
            }

        } else {
            variableField = new VariableField();
            variableField.setName(key);
            variableField.setValue(value);
            variableField.setValueType(valueType);
            variableField.setDisplayName(displayName);
        }

        super.put(key, variableField);
    }

    /**
     * 根据字段名称、类型获取值
     * @param key 字段名
     * @param type 数据类型
     * @param displayName 显示名称
     * @return Object
     */
    @Deprecated
    public Object get(String key, String type, String displayName) {
        VariableField value = super.get(key);

        if (Objects.isNull(value) || Objects.isNull(value.getValue()) || StringUtils.isEmpty(value.getValue().toString())) {
            return null;
        }

        String tmpValue = Objects.toString(value.getValue());

        if (isString(type)) {
            return tmpValue;
        }

        if (isInteger(type)) {
            return Integer.parseInt(tmpValue);
        }

        if (isDouble(type)) {
            return Double.parseDouble(tmpValue);
        }

        if (isFloat(type)) {
            return Float.parseFloat(tmpValue);
        }

        if (isLong(type)) {
            return Long.parseLong(tmpValue);
        }

        if (isList(type)) {
            return Arrays.asList(tmpValue.split(","));
        }

        if (isBigDecimal(type)) {
            return new BigDecimal(tmpValue);
        }

        return tmpValue;
    }


    /******************** 泛型 ************************/

    /**
     * 添加数据，便于规则引擎添加
     * @param key 属性名
     * @param value 数据
     * @param valueType 数据类型
     * @param displayName 显示名称
     */
    public <T> void put(String key, String value, Class<T> valueType, String displayName) {
        VariableField variableField = new VariableField();
        variableField.setName(key);
        variableField.setValue(value);
        variableField.setValueType(valueType.getSimpleName());
        variableField.setDisplayName(displayName);

        super.put(key, variableField);
    }

    /**
     * 添加新的数据
     * @param key 属性名
     * @param value 数据
     * @param valueType 数据类型
     * @param displayName 显示名称
     */
    public <T> void put(String key, Object value, Class<T> valueType, String displayName) {
        VariableField variableField = new VariableField();
        variableField.setName(key);
        variableField.setValue(value);
        variableField.setValueType(valueType.getSimpleName());
        variableField.setDisplayName(displayName);

        super.put(key, variableField);
    }

    /**
     * 根据字段名称、类型获取值
     * @param key 字段名
     * @param tClass 数据类型
     * @param displayName 显示名称
     * @return Object
     */
    public <T> T get(String key, Class<T> tClass, String displayName) {
        VariableField value = super.get(key);

        if (Objects.isNull(value) || Objects.isNull(value.getValue()) || StringUtils.isEmpty(value.getValue().toString())) {
            return (T) null;
        }

        Object tmpValue = value.getValue();

        return (T) tmpValue;
    }

    /**
     * 存在数据追加数据，不存在新增数据
     * @param key 属性名
     * @param value 数据
     * @param tClass 数据类型
     * @param displayName 显示名称
     */
    public <T> void append(String key, String value, Class<T> tClass, String displayName) {
        VariableField variableField = null;
        if (super.containsKey(key) && Objects.nonNull(super.get(key).getValue())) {
            variableField = super.get(key);
            // 获取旧的数据
            Object oldValue = this.get(variableField.getName(), tClass, variableField.getDisplayName());

            if (isString(variableField.getValueType())) {
                variableField.setValue(((T) oldValue) + value);
            }

            if (isInteger(variableField.getValueType())) {
                variableField.setValue(ArithUtils.add(oldValue.toString(), value).intValue());
            }

            if (isDouble(variableField.getValueType())) {
                variableField.setValue(ArithUtils.add(oldValue.toString(), value).doubleValue());
            }

            if (isFloat(variableField.getValueType())) {
                variableField.setValue(ArithUtils.add(oldValue.toString(), value).floatValue());
            }

            if (isLong(variableField.getValueType())) {
                variableField.setValue(ArithUtils.add(oldValue.toString(), value).longValue());
            }

            if (isList(variableField.getValueType())) {
                variableField.setValue(((List)oldValue).addAll(Arrays.asList(value.split(","))));
            }

            if (isBigDecimal(variableField.getValueType())) {
                variableField.setValue(ArithUtils.add(oldValue.toString(), value));
            }

        } else {
            variableField = new VariableField();
            variableField.setName(key);
            variableField.setValue(value);
            variableField.setValueType(tClass.getSimpleName());
            variableField.setDisplayName(displayName);
        }

        super.put(key, variableField);
    }

    private boolean isString(String type) {
        return IConstants.ValueType.STRING.equals(type);
    }

    private boolean isInteger(String type) {
        return IConstants.ValueType.INTEGER.equals(type);
    }

    private boolean isDouble(String type) {
        return IConstants.ValueType.DOUBLE.equals(type);
    }

    private boolean isFloat(String type) {
        return IConstants.ValueType.FLOAT.equals(type);
    }

    private boolean isLong(String type) {
        return IConstants.ValueType.LONG.equals(type);
    }

    private boolean isList(String type) {
        return IConstants.ValueType.LIST.equals(type);
    }

    private boolean isBigDecimal(String type) {
        return IConstants.ValueType.BIGDECIMAL.equals(type);
    }

}
