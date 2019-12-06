package com.benz.core.common.utils;

import java.util.HashMap;
import java.util.UUID;

public class TraceUtil {
    private static final ThreadLocal<HashMap<String, Object>> threadLocal = ThreadLocal.withInitial(HashMap::new);


    public static void setCorrelationID(String correlationID) {
        if (null == threadLocal.get()) {
            threadLocal.set(new HashMap<>());
        }

        threadLocal.get().put("correlationID", correlationID);
    }

    public static String getCorrelationID() {
        if (null == threadLocal.get()) {
            threadLocal.set(new HashMap<>());
        }

        Object object = threadLocal.get().get("correlationID");
        if (null == object) {
            object = UUID.randomUUID();
            threadLocal.get().put("correlationID", object.toString());
        }

        return object.toString();
    }

    public static void setClientIP(String correlationID) {
        if (null == threadLocal.get()) {
            threadLocal.set(new HashMap<>());
        }

        threadLocal.get().put("clientIP", correlationID);
    }

    public static String getClientIP() {
        if (null != threadLocal.get()) {
            Object object = threadLocal.get().get("clientIP");
            if (null != object) {
                return object.toString();
            }
        }

        return null;
    }

    public static void clear() {
        if (null != threadLocal.get()) {
            threadLocal.get().clear();
        }

    }
}
