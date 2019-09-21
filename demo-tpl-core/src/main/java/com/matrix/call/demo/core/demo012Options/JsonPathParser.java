package com.matrix.call.demo.core.demo012Options;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @description:
 * @author: weiqi
 * @create: 2019/9/16
 **/
public class JsonPathParser {
    public static void main(String[] args) throws IOException {
        String fullPath = "$.spec.containers[0].livenessProbe.exec.command[0]";
        // 方法一
        //String commandVal = CommonUDFUtils.filterStringEvalValue(JSONPath.eval(itemJsonObj, fullPath));
        // 方法二
        String fileStr = FileUtils.readFileToString(new File("D:/test.json"));
        JSONObject itemJsonObj = JSON.parseObject(fileStr);
        String commandVal2 = Optional.ofNullable(itemJsonObj)
                .map(jsonObj -> jsonObj.getJSONObject("spec"))
                .map(jsonObj -> jsonObj.getJSONArray("containers"))
                .map(jsonObj -> CollectionUtils.isEmpty(jsonObj) || jsonObj.size() < 2 ? null : jsonObj.getJSONObject(1))
                .map(jsonObj -> jsonObj.getJSONObject("livenessProbe"))
                .map(jsonObj -> jsonObj.getJSONObject("exec"))
                .map(jsonObj -> jsonObj.getJSONArray("command"))
                .map(jsonObj -> CollectionUtils.isEmpty(jsonObj) ? null : jsonObj.getString(0))
                .orElse("1111");
        System.out.println(commandVal2);
    }
}

