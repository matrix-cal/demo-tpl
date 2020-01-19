package com.matrix.call.demo.core.demo000Test;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo6 {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppDemo6.class);

    private static final String DATE_REGEX = "\\{(YYYY[-/.]?MM[-/.]?dd)\\}";

    private static String replaceDatePath(String path) {
        String dateFormat = getValueByRegex(path, DATE_REGEX);
        if (StringUtils.isEmpty(dateFormat)) {
            return path;
        }
        String date = getDateByDiffDay(0, dateFormat);
        String baseFormat = "{" + dateFormat + "}";
        return path.replace(baseFormat, date);
    }
    public static String getDateByDiffDay(int daydiff, String format) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, daydiff);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }
    private static final String EXEC_CMD = " xxxx";

    public static String getValueByRegex(String result, String regex) {
        return getValueByRegex(result, regex, 0);
    }

    public static String getValueByRegex(String result, String regex, Integer group) {
        if (StringUtils.isBlank(result) || StringUtils.isBlank(regex)) {
            return StringUtils.EMPTY;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(result);
        while (matcher.find()) {
            return matcher.group(group);
        }
        return StringUtils.EMPTY;
    }

    public static void main(String[] args) {
        /*String path = "/tmp/{YYYYMMdd}*//*";
        StringBuilder cmdSb = new StringBuilder();
        cmdSb.append("stat ").append(replaceDatePath(path)).append(EXEC_CMD);
        System.out.println(cmdSb);
        */
        String format = "{YYYYMMdd}";
        Date date = new Date(1572502596000L);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        System.out.println(dateString);

    }



}
