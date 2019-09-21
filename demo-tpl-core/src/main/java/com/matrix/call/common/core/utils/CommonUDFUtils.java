package com.matrix.call.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by weiqi on 2017/7/26.
 */
public class CommonUDFUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUDFUtils.class);

    private static final Pattern BRANCH_GREP_PATTERN = Pattern.compile("^([a-z0-9]+)", Pattern.MULTILINE);


    public static void sleep(long milliSecond) {
        try {
            if (milliSecond <= 0L) {
                return;
            }
            Thread.sleep(milliSecond);
        } catch (InterruptedException e) {
            LOGGER.error("", e);
        }
    }



    public static String grepBranchName(String branchName) {
        String result = "";
        if (StringUtils.isEmpty(branchName)) {
            return result;
        }

        Matcher matcher = BRANCH_GREP_PATTERN.matcher(branchName);
        if (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }


    public static Set<String> findVarName(String url, Pattern pattern) {
        // url like : "curl -s -o ${111} devops-git.tar"+
        // "--header \"Authorization: Bearer ${p.gitUserToken}\"+
        // "\"${p.gitProjectPrefixUrl}/api/v4/projects/${p.gitProjectId}/repository/archive.tar?sha=${p.gitCommitId}\""
        Set<String> result = Sets.newHashSet();
        if (StringUtils.isEmpty(url) || pattern == null) {
            return result;
        }
        //String regexStr = "\\$\\{[^\\}]*\\.([^\\}]+)\\}";
        //Pattern pattern = Pattern.compile(regexStr);

        Matcher matcher = pattern.matcher(url);
        while (matcher.find()) {
            String contextName = matcher.group(1);
            if (StringUtils.isEmpty(contextName) || contextName.trim().length() == 0) {
                continue;
            }
            result.add(contextName);
        }
        return result;

    }



    public static List<String> convertCommaStrToList(String src, boolean toLowerCase) {
        List<String> retVal = Lists.newArrayList();
        try {
            if (StringUtils.isEmpty(src)) {
                return retVal;
            }
            String[] items = src.split(",");
            for (String item : items) {
                if (StringUtils.isEmpty(item)) {
                    continue;
                }
                if (toLowerCase) {
                    retVal.add(item.toLowerCase());
                } else {
                    retVal.add(item);
                }
            }
        } catch (Exception e) {
            LOGGER.error("原始str:"+src, e.toString());
        }
        return retVal;
    }

    public static String hiddenTokenStr(String src) {
        if (StringUtils.isEmpty(src)) {
            return "";
        }
        String result = StringUtils.replaceAll(src, "oauth2:.+@", "oauth2:******@");
        result = StringUtils.replaceAll(result, "Authorization: Bearer [a-z0-9]+", "Authorization: Bearer ******");
        return result;
    }


    public static Integer parseIntNoException(String intStr, Integer errorResult) {
        int result = 0;
        try {
            result = Integer.parseInt(intStr);
        } catch (Exception e) {
            LOGGER.error("转换Int出错, srcVal:{}", intStr);
            return errorResult;
        }
        return result;
    }


    public static List<String> parseEmailField(String srcJson, String objKey) {
        List<String> result = Lists.newArrayList();
        try {
            JSONObject notifyObj = JSON.parseObject(srcJson);
            if (notifyObj == null) {
                return result;
            }
            JSONArray emailsArrayObj = notifyObj.getJSONArray(objKey);
            if (emailsArrayObj == null) {
                return result;
            }
            result = emailsArrayObj.toJavaList(String.class);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return result;
    }

    public static String joinEmailByChar(List<String> emailList, String splitChar) {
        StringBuffer resultSb = new StringBuffer("");
        if(emailList == null || emailList.isEmpty()){
            return resultSb.toString();
        }
        emailList.forEach((item) ->{
            resultSb.append(item).append(splitChar);
        });
        String result = resultSb.toString();
        if(result.endsWith(splitChar)){
            return result.substring(0, result.length() - 1);
        }
        return result;
    }




    public static List<String> convertCmdToList(String cmd) {
        List<String> retVal = Lists.newArrayList();
        if (StringUtils.isEmpty(cmd)) {
            return retVal;
        }
        cmd = cmd.trim().replaceAll("(\\\\r\\\\n|\\\\r|\\\\n|\\\\n\\\\r)", "\n");
        String[] targetArray = StringUtils.split(cmd, "\n");
        Arrays.stream(targetArray).forEach(item -> {
            if(!StringUtils.isEmpty(item)){
                retVal.add(item.trim());
            }
        });
        return retVal;
    }


    public static List<String> splitString(String src, String splitBy) {
        if (StringUtils.isEmpty(src)) {
            return Lists.newArrayList();
        }
        String[] targetArray = src.split(splitBy);
        List<String> targetList = Lists.newArrayList();
        Arrays.stream(targetArray).forEach(item -> targetList.add(item.trim()));
        return targetList;
    }

    public static String getFileNameByUrl(String url, String defaultVal) {
        if (StringUtils.isEmpty(url)) {
            return defaultVal;
        }
        if (url.startsWith("$")) {
            return defaultVal;
        }
        return FilenameUtils.getName(url);

    }


    public static String convertToGitProtocol(String src) {
        try {
            if (StringUtils.isEmpty(src)) {
                return null;
            }
            // 本来是git 开头的
            if (StringUtils.startsWith(src, "git@")) {
                if(!StringUtils.endsWith(src, ".git")){
                    src = src + ".git";
                }
                return src;
            } else if (StringUtils.startsWith(src, "http")) {
                src = StringUtils.replaceIgnoreCase(src, "https://", "git@");
                src = StringUtils.replaceIgnoreCase(src, "http://", "git@");
                src = StringUtils.replaceFirst(src, "/", ":");
                if(!StringUtils.endsWith(src, ".git")){
                    src = src + ".git";
                }
                return src;
            } else {
                return null;
            }


        } catch (Exception e) {
            LOGGER.error("convertToGitProtocol ERROR, ", e.toString());
            return null;
        }
    }


    public static String convertToHttpGitProtocol(String src, String httpOrHttps) {
        // gitBaseUrl like "https://git.dz11.com" or "http://gitlab.ocean.douyu.com"
        try {
            if (StringUtils.isEmpty(src)) {
                return null;
            }
            // 本来是git 开头的
            if (StringUtils.startsWith(src, "http")) {
                if(!StringUtils.endsWith(src, ".git")){
                    src = src + ".git";
                }
                return src;
            } else if (StringUtils.startsWith(src, "git@")) {
                src = StringUtils.replaceFirst(src, ":", "/");
                src = StringUtils.replaceIgnoreCase(src, "git@", (httpOrHttps+"://"));

                if(!StringUtils.endsWith(src, ".git")){
                    src = src + ".git";
                }
                return src;
            } else {
                return null;
            }


        } catch (Exception e) {
            LOGGER.error("convertToGitProtocol ERROR, ", e.toString());
            return null;
        }
    }

    public static String convertToHttpGitProtocolWithToken(String src, String httpOrHttps, String token) {

        try {
            if (StringUtils.isEmpty(src)) {
                return null;
            }
            String result = convertToHttpGitProtocol(src, httpOrHttps);
            if (StringUtils.isEmpty(result)) {
                return null;
            }
            result = StringUtils.replaceFirst(result, httpOrHttps+"://", httpOrHttps+"://oauth2:"+token+"@");
            return result;
        } catch (Exception e) {
            LOGGER.error("convertToGitProtocol ERROR, ", e.toString());
            return null;
        }
    }

    public static void main(String[] args) {
        String src = "https://git.dz11.com/bigdata/inf/cradle.git";
        String src2 = "git@git.dz11.com:bigdata/inf/cradle.git";
        String src3 = "https://git.dz11.com/wsd/dy-projects/wechat-push-srv-php.git";
        String result = convertToHttpGitProtocolWithToken(src3, "https", "123d4aa833807213ee1188bf057c69e0b91944a90d9a4d59f455f7f65957353f");
        System.out.println(result);

        String test = " GIT_REPO_SSH = \"https://oauth2:c2dfd89adfc033273741b774463827702adfd709230fac5d5d4aad5fff3e64bb@git.dz11.com/wsd/dy-projects/wechat-push-srv-php.git\"";
        String result2 = hiddenTokenStr(test);
        System.out.println(result2);

    }



    public static String expandJvmMemConfig(String jvmVal) { //jvmVal = 512m 或6g
        int jvmValLength = jvmVal.length();

        int jvmContent = Integer.parseInt(jvmVal.substring(0, jvmValLength-1));
        String jvmUnit = jvmVal.substring(jvmValLength - 1, jvmValLength);
        if ("g".equalsIgnoreCase(jvmUnit)) {
            jvmContent = jvmContent * 1024;
            jvmUnit = "m";
        }
        int expandVal = (int)(jvmContent * 1.5);
        expandVal = (expandVal < 1024) ? 1024 : expandVal;
        return expandVal + "Mi";

    }

    public static boolean isWindowsEnv() {
        if (System.getProperties().getProperty("os.name").matches("^(?i)Windows.*$")) {
            return true;
        }
        return false;
    }

    public static String[] findIpAndPortByRegex(String srcStr) {
        if (srcStr == null) {
            return null;
        }
        String regexStr = "((\\d+\\.){3}\\d+)\\:(\\d+)";
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(srcStr);
        if (matcher.find()) {
            String ip = matcher.group(1);
            String port = matcher.group(3);
            return new String[]{ip, port};
        } else {
            return null;
        }

    }

    public static String getkeywordsByRegex(String regex, String str) {
        Pattern p = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }

    public static String getFriendlytime(Date d) {
        long delta = (System.currentTimeMillis() - d.getTime()) / 1000;
        if (delta <= 0) {
            return "0sec";
        }
        if (delta / (60 * 60 * 24 * 365) > 0) {
            return delta / (60 * 60 * 24 * 365) + "year";
        }
        if (delta / (60 * 60 * 24 * 30) > 0) {
            return delta / (60 * 60 * 24 * 30) + "month";
        }

        if (delta / (60 * 60 * 24) > 0) {
            return delta / (60 * 60 * 24) + "day";
        }
        if (delta / (60 * 60) > 0) {
            return delta / (60 * 60) + "hour";
        }
        if (delta / (60) > 0) {
            return delta / (60) + "min";
        }
        return delta + "sec";
    }


    public static String convertKubeTimeToAge(String ageStr) {
        String retVal = "";
        try {
            if (ageStr == null) {
                return retVal;
            }
            ageStr = ageStr.replaceAll("Z", "");
            //System.out.println(ageStr);
            Date ageCreated = DateUtils.parseDate(ageStr, "yyyy-MM-dd'T'HH:mm:ss");
            retVal = CommonUDFUtils.getFriendlytime(DateUtils.addHours(ageCreated, 8));
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return retVal;
    }

    public static String convertKubeTimeToAgeDate(String ageStr) {
        String retVal = "";
        try {
            if (ageStr == null) {
                return retVal;
            }
            ageStr = ageStr.replaceAll("Z", "");
            //System.out.println(ageStr);
            Date ageCreated = DateUtils.parseDate(ageStr, "yyyy-MM-dd'T'HH:mm:ss");
            ageCreated = DateUtils.addHours(ageCreated, 8);
            retVal = DateFormatUtils.format(ageCreated, "yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return retVal;
    }


    /**
     * NO-USE
     * 根据"-"前面的数值倒叙
     *
     * @param tagList
     */
    public static void sortStringList(List<String> tagList) {
        //- sort List
        Collections.sort(tagList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (StringUtils.isEmpty(s1)) {
                    return 1;
                }
                if (StringUtils.isEmpty(s2)) {
                    return 1;
                }
                if ("latest".equalsIgnoreCase(s1)) {
                    return -1;
                }
                if ("latest".equalsIgnoreCase(s2)) {
                    return 1;
                }
                long s1Value = 0L;
                long s2Value = 0L;
                try {
                    String s1DateStr = s1.split("-")[0];
                    s1Value = Long.parseLong(s1DateStr);
                } catch (Exception e) {
                    return 1;
                }
                try {
                    String s2DateStr = s2.split("-")[0];
                    s2Value = Long.parseLong(s2DateStr);
                } catch (Exception e) {
                    return -1;
                }

                if (s1Value > s2Value) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }




    public static String filterStringEvalValue(Object obj) {
        String retVal = null;
        if(obj == null){
            return retVal;
        }

        try {
            retVal = (String) obj;
            return retVal;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return retVal;
    }

    public static Integer filterIntEvalValue(Object obj) {
        Integer retVal = 0;
        if(obj == null){
            return retVal;
        }

        try {
            retVal = (Integer) obj;
            return retVal;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return retVal;
    }
    public static Long filterLongEvalValue(Object obj) {
        Long retVal = 0L;
        if(obj == null){
            return retVal;
        }

        try {
            retVal = (Long) obj;
            return retVal;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return retVal;
    }

    public static boolean filterBooleanEvalValue(Object obj, boolean defaultVal) {
        boolean retVal = defaultVal;
        if(obj == null){
            return retVal;
        }

        try {
            retVal = (boolean) obj;
            return retVal;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return retVal;
    }

    public static Integer filterIntEvalValue(Object obj, Integer defaultVal) {
        Integer retVal = defaultVal;
        if(obj == null){
            return retVal;
        }

        try {
            retVal = (Integer) obj;
            return retVal;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return retVal;
    }

    public static JSONObject filterJSONObjValue(Object obj) {
        JSONObject retVal = new JSONObject();
        if(obj == null){
            return retVal;
        }

        try {
            if(obj instanceof JSONObject){
                retVal = (JSONObject) obj;
            }else if (obj instanceof LinkedHashMap){
                retVal = new JSONObject((LinkedHashMap) obj);
            }
            return retVal;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return retVal;
    }
    public static JSONArray filterJSONArrayValue(Object obj) {
        JSONArray retVal = new JSONArray();
        if(obj == null){
            return retVal;
        }
        try {
            if(obj instanceof JSONArray){
                retVal = (JSONArray) obj;
            }else if (obj instanceof ArrayList){
                retVal = new JSONArray((ArrayList) obj);
            }
            return retVal;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return retVal;
    }

    public static String upperfirstLetter(String word) {
        if (StringUtils.isEmpty(word)) {
            return "";
        }
        String firstLetter = word.toUpperCase().substring(0, 1);
        return firstLetter + word.substring(1);
    }



}
