package com.example.punchcardsystem.utils;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Result {

    public static String generateResponse(int code, String message, Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);

        if (data != null && !data.isEmpty()) {
            response.put("data", data);
        }

        return JSON.toJSONString(response);
    }

    public static String okGetString() {
        return generateResponse(200, "ok", null);
    }

    public static String okGetStringWithData(Map<String, Object> data) {
        return generateResponse(200, "ok", data);
    }

    public static String errorGetString(int code, String message) {
        return generateResponse(code, message, null);
    }

    public static String warningGetString(String message) {
        return generateResponse(300, message, null);  // 假设 300 是警告状态码
    }
}

