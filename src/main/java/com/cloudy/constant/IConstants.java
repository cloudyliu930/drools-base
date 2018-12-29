package com.cloudy.constant;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author cloudy
 * @createTime 2018/12/26
 * @description 常量类
 */
public interface IConstants {

    interface Order {
        /**
         * 初始化容器顺序
         */
        Integer INIT_CONTEXT_ORDER = 50;

        /**
         * 初始化ETCD监听器顺序
         */
        Integer ETCD_WATCH_ORDER = 49;
    }

    interface ValueType {
        String STRING = String.class.getSimpleName();
        String INTEGER = Integer.class.getSimpleName();
        String DOUBLE = Double.class.getSimpleName();
        String LONG = Long.class.getSimpleName();
        String FLOAT = Float.class.getSimpleName();
        String BIGDECIMAL = BigDecimal.class.getSimpleName();
        String LIST = List.class.getSimpleName();
    }
}
