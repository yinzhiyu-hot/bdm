package cn.wangoon.ms.bdm.core.common.utils;

import cn.hutool.core.lang.Console;
import cn.hutool.core.text.StrSplitter;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Description 字符串工具类
 * @Author YINZHIYU
 * @Date 2019-10-09 10:15:00
 * @Version 1.0.0.0
 **/
@Slf4j
public class StringUtils {

    /**
     * @Description 首字母变小写
     * @Params ==>
     * @Param str
     * @Return java.lang.String
     * @Date 2020/4/21 10:05
     * @Auther YINZHIYU
     */
    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * @Description 首字母变大写
     * @Params ==>
     * @Param str
     * @Return java.lang.String
     * @Date 2020/4/21 10:05
     * @Auther YINZHIYU
     */
    public static String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * @Description 字符串为 null 或者为  "" 时返回 true
     * @Params ==>
     * @Param str
     * @Return boolean
     * @Date 2020/4/21 10:06
     * @Auther YINZHIYU
     */
    public static boolean isBlank(String str) {
        return ObjectUtil.isEmpty(str) || ObjectUtil.isEmpty(str.trim()) || StrUtil.equals("null", str.trim(), true);
    }

    /**
     * @Description 字符串不为 null 而且不为  ""/"null" 时返回 true
     * @Params ==>
     * @Param str
     * @Return boolean
     * @Date 2020/4/21 10:06
     * @Auther YINZHIYU
     */
    public static boolean notBlank(String str) {
        return ObjectUtil.isNotEmpty(str) && ObjectUtil.isNotEmpty(str.trim()) && !StrUtil.equals("null", str.trim(), true);
    }

    /**
     * @Description 字符串不为 null
     * @Remark
     * @Params ==>
     * @Param strings
     * @Return boolean
     * @Date 2020/11/17 13:58
     * @Auther YINZHIYU
     */
    public static boolean notBlank(String... strings) {
        if (ObjectUtil.isEmpty(strings)) {
            return false;
        }
        for (String str : strings) {
            if (ObjectUtil.isEmpty(str) || ObjectUtil.isEmpty(str.trim()) || StrUtil.equals("null", str.trim(), true)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @Description 对象不为 null
     * @Remark
     * @Params ==>
     * @Param paras
     * @Return boolean
     * @Date 2020/11/17 13:58
     * @Auther YINZHIYU
     */
    public static boolean notNull(Object... paras) {
        if (ObjectUtil.isEmpty(paras)) {
            return false;
        }
        for (Object obj : paras) {
            if (ObjectUtil.isEmpty(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将数字转化成设定位数的字符串，不足位数则在数字前补0 <br/>
     * 例如获取6位的字符串：2018 ——> "002018"
     *
     * @param intValue 传入的正整数
     * @param n        几位的字符串
     * @return
     */
    public static String genNumberStr(int intValue, int n) {
        StringBuilder rs = new StringBuilder();
        if (intValue > 0 && n > 0) {
            rs.append(intValue);
            if (n > rs.length()) {
                int addn = n - rs.length();
                for (int i = 0; i < addn; i++) {
                    rs.append("0").append(rs);
                }
            }
        }
        return rs.toString();
    }

    /**
     * 将数字转化成设定位数的字符串，不足位数则在数字前补0 <br/>
     * 例如获取6位的字符串：2018 ——> "002018"
     *
     * @param intValue 传入的正整数
     * @param n        几位的字符串
     * @return
     */
    public static String genNumberStrByL(Long intValue, int n) {
        StringBuilder rs = new StringBuilder();
        if (intValue > 0 && n > 0) {
            rs.append(intValue);
            if (n > rs.length()) {
                int addn = n - rs.length();
                for (int i = 0; i < addn; i++) {
                    rs.append("0").append(rs);
                }
            }
        }
        return rs.toString();
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥…（）—【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 清洗特殊字符
     *
     * @Remark 移除特殊字符串
     * @Params ==>
     * @Param str
     * @Return java.lang.String
     * @Date 2021-11-24 09:23:31
     * @Auther YINZHIYU
     */
    public static String removeSpecialChar(String str) throws PatternSyntaxException {
        try {
            if (ObjectUtil.isEmpty(str)) {
                return StrUtil.EMPTY;
            }
            // 只允许字母和数字
            // String   regEx  =  "[^a-zA-Z0-9]";
            // 清除掉所有特殊字符
            String regEx = "[_`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥…（）—【】‘；：”“。，、？]|\n|\r|\t";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.replaceAll(StrUtil.EMPTY).trim();//特殊字符用空格替代
        } catch (Exception ex) {
            log.error("清除特殊字符异常", ex);
            return str;
        }
    }

    /**
     * 清洗特殊字符
     *
     * @Remark
     * @Params ==>
     * @Param str
     * @Param replaceStr 特殊字符替代字符
     * @Return java.lang.String
     * @Date 2021-11-24 09:23:21
     * @Auther YINZHIYU
     */
    public static String removeSpecialChar(String str, String replaceStr) throws PatternSyntaxException {
        try {
            if (ObjectUtil.isEmpty(str)) {
                return StrUtil.EMPTY;
            }
            // 只允许字母和数字
            // String   regEx  =  "[^a-zA-Z0-9]";
            // 清除掉所有特殊字符
            String regEx = "[_`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥…（）—【】‘；：”“。，、？]|\n|\r|\t";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.replaceAll(replaceStr).trim();
        } catch (Exception ex) {
            log.error("清除特殊字符异常", ex);
            return str;
        }
    }

    /**
     * @Description 判断是否纯数字
     * @Remark
     * @Params ==>
     * @Param str
     * @Return boolean
     * @Date 2020/11/17 13:59
     * @Auther YINZHIYU
     */
    public static boolean isOnlyNumber(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        String regEx = "^[0-9]*$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * @Description 判断是否浮点数
     * @Remark
     * @Params ==>
     * @Param str
     * @Return boolean
     * @Date 2020/11/17 13:59
     * @Auther YINZHIYU
     */
    public static boolean isNumber(String str) {
        //采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全
        //可以判断正负、整数小数
        String regexRuleInt = "^-?[1-9]\\d*$";
        Pattern patternInt = Pattern.compile(regexRuleInt);
        boolean isInt = patternInt.matcher(str).find();
        String regexRuleDouble = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
        Pattern patternDouble = Pattern.compile(regexRuleDouble);
        boolean isDouble = patternDouble.matcher(str).find();

        return isInt || isDouble;
    }

    /**
     * 获取当前时间字符串：1855555735109
     *
     * @return
     */
    public static String autoGenItemCode() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        int milliSecond = now.get(Calendar.MILLISECOND);
        long timeMills = now.getTimeInMillis();
        Console.log("年: " + year);
        Console.log("月: " + month);
        Console.log("日: " + day);
        Console.log("时: " + hour);
        Console.log("分: " + minute);
        Console.log("秒: " + second);
        Console.log("毫秒: " + milliSecond);
        Console.log("当前时间毫秒数：" + timeMills);
        String dateStr = year + "";
        dateStr = dateStr.substring(2);

        if (month + 1 < 10) {
            dateStr = dateStr + ("0" + month);
        } else {
            dateStr = dateStr + (month + 1);
        }
        if (day < 10) {
            dateStr = dateStr + ("0" + day);
        } else {
            dateStr = dateStr + day;
        }
        if (hour < 10) {
            dateStr = dateStr + ("0" + hour);
        } else {
            dateStr = dateStr + hour;
        }
        if (minute < 10) {
            dateStr = dateStr + ("0" + minute);
        } else {
            dateStr = dateStr + minute;
        }
        if (milliSecond < 10) {
            dateStr = dateStr + ("00" + milliSecond);
        } else if (milliSecond < 100) {
            dateStr = dateStr + ("0" + milliSecond);
        } else {
            dateStr = dateStr + milliSecond;
        }
        return dateStr;
    }

    /**
     * 拼接字符串
     *
     * @param sb
     * @param prefixMsg
     * @param suffixMsg
     * @return prefixMsg：suffixMsg  例如   姓名:啊啊啊; 年龄:18
     */
    public static StringBuffer splicingMsg(StringBuffer sb, String prefixMsg, String suffixMsg) {
        if (ObjectUtil.isEmpty(prefixMsg) || ObjectUtil.isEmpty(suffixMsg)) {
            return sb;
        }
        if (sb.length() > 0) {
            sb.append("; ");
        }
        sb.append(prefixMsg).append("：").append(suffixMsg);
        return sb;
    }

    /**
     * 去除字符串中间的多余的空格，只保留一个空格  例子：  'aaa	 bbb  c '  =>  'aaa bbb c '
     *
     * @param string
     * @return
     */
    public static String removeMiddleExtraSpace(String string) {
        try {
            if (ObjectUtil.isEmpty(string)) {
                return StrUtil.EMPTY;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < string.length(); i++) {
                String str = "" + string.charAt(i);
                str = str.replaceAll("\\s*", "");
                if (ObjectUtil.isNotEmpty(str)) {
                    sb.append(str);
                }
                try {
                    String str_next = "" + string.charAt(i + 1);
                    str_next = str_next.replaceAll("\\s*", "");
                    if (ObjectUtil.isEmpty(str) && ObjectUtil.isNotEmpty(str_next)) {
                        sb.append(' ');
                    }
                } catch (Exception e) {
                    if (ObjectUtil.isEmpty(str)) {
                        sb.append(' ');
                    }
                }
            }
            return sb.toString().trim();
        } catch (Exception ex) {
            log.error("移除多余空格异常", ex);
            return string;
        }
    }

    public static StringBuffer appendStrMsg2Sb(StringBuffer sb, String firstMsg, boolean addFirstMsg, String msg, String lastMsg) {
        if (ObjectUtil.isEmpty(sb)) {
            return null;
        }
        if (addFirstMsg) {
            sb.append(firstMsg);
        }
        sb.append(msg);
        sb.append(lastMsg);
        return sb;
    }

    /**
     * 字符串按长度拆分成数组
     *
     * @param string
     * @param len
     * @return
     */
    public static String[] splitString(String string, int len) {

        String[] stringArrays = new String[0];

        if (isBlank(string)) {
            return stringArrays;
        }

        int strLen = string.length();

        int splitCount = strLen / len;

        if (splitCount > 0) {
            if ((strLen % len) > 0) {
                stringArrays = new String[splitCount + 1];
            } else {
                stringArrays = new String[splitCount];
            }

            int cache = len;

            int start = 0;
            for (int i = 0; i < stringArrays.length; i++) {
                if (Objects.equals(i, 0)) {
                    stringArrays[0] = string.substring(start, len);
                    start = len;
                } else if (i > 0 && i < stringArrays.length - 1) {
                    len = len + cache;
                    stringArrays[i] = string.substring(start, len);
                    start = len;
                } else {
                    stringArrays[i] = string.substring(len);
                }
            }
        } else {
            stringArrays = new String[]{string};
        }
        return stringArrays;
    }

    /**
     * 编码
     *
     * @param str
     * @return
     */
    public static String base64Encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解码
     *
     * @param str
     * @return
     */
    public static String base64Decode(String str) {
        return new String(Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * @Description 拼接
     * @Remark
     * @Params ==>
     * @Param conjunction
     * @Param objs
     * @Return java.lang.String
     * @Date 2021/3/11 15:50
     * @Auther Thomas
     */
    public static String join(CharSequence conjunction, String... objs) {
        if (ObjectUtil.isEmpty(objs)) {
            return StrUtil.EMPTY;
        }
        List<String> obs = Arrays.stream(objs).filter(o -> ObjectUtil.isNotEmpty(StrUtil.trim(o))).collect(Collectors.toList());
        return StrUtil.join(conjunction, obs);
    }

    /**
     * @Description 拼接
     * @Remark
     * @Params ==>
     * @Param conjunction
     * @Param objs
     * @Return java.lang.String
     * @Date 2021/3/11 15:50
     * @Auther Thomas
     */
    public static String join(CharSequence conjunction, List<String> objs) {
        if (ObjectUtil.isEmpty(objs)) {
            return StrUtil.EMPTY;
        }
        List<String> obs = objs.stream().filter(o -> ObjectUtil.isNotEmpty(StrUtil.trim(o))).collect(Collectors.toList());
        return StrUtil.join(conjunction, obs);
    }

    /**
     * @Description Object拼接
     * @Remark
     * @Params ==>
     * @Param conjunction
     * @Param objs
     * @Return java.lang.String
     * @Date 2021/3/23 14:30
     * @Auther YINZHIYU
     */
    public static String joinObj(CharSequence conjunction, Object... objs) {
        if (ObjectUtil.isEmpty(objs)) {
            return StrUtil.EMPTY;
        }
        List<Object> obs = Arrays.stream(objs).filter(ObjectUtil::isNotEmpty).filter(o -> ObjectUtil.isNotEmpty(StrUtil.trim(o.toString())))
                .collect(Collectors.toList());
        return StrUtil.join(conjunction, obs);
    }

    /**
     * @Description Object拼接
     * @Remark
     * @Params ==>
     * @Param conjunction
     * @Param objs
     * @Return java.lang.String
     * @Date 2021/3/23 14:30
     * @Auther YINZHIYU
     */
    public static String joinObj(CharSequence conjunction, List<Object> objs) {
        if (ObjectUtil.isEmpty(objs)) {
            return StrUtil.EMPTY;
        }
        List<Object> obs = objs.stream().filter(ObjectUtil::isNotEmpty).filter(o -> ObjectUtil.isNotEmpty(StrUtil.trim(o.toString())))
                .collect(Collectors.toList());
        return StrUtil.join(conjunction, obs);
    }

    /**
     * @Description 字符串转map
     * @Remark 如下规则的进行map转换，按\n拆分，再按:进行key-value拆分
     * "Max Memory:3.52 GB\nTotal Memory:243.5 MB\nFree Memory:207.5 MB\nUsable Memory:3.49 GB\n"
     * @Params ==>
     * @Param str
     * @Param fieldSeparator  字段拆分
     * @Param keyValueSeparator 键值拆分
     * @Return java.util.Map<java.lang.String, java.lang.String>
     * @Date 2021/3/24 10:22
     * @Auther YINZHIYU
     */
    public static Map<String, String> strToMap(String str, String fieldSeparator, String keyValueSeparator) {
        if (ObjectUtil.isEmpty(str) || ObjectUtil.isEmpty(StrUtil.trim(str))) {
            return null;
        }
        List<String> strs = StrSplitter.split(str, fieldSeparator, true, true);
        if (ObjectUtil.isEmpty(strs)) {
            return null;
        }
        Map<String, String> map = Maps.newHashMap();
        for (String kvStr : strs) {
            List<String> kv = StrSplitter.split(kvStr, keyValueSeparator, true, true);
            map.put(kv.get(0), kv.get(1));
        }
        return map;
    }

    /**
     * @Description 字符串转map
     * @Remark 如下规则的进行map转换，按:进行key-value拆分
     * "Max Memory:3.52 GB"
     * @Params ==>
     * @Param str
     * @Param keyValueSeparator 键值拆分
     * @Return java.util.Map<java.lang.String, java.lang.String>
     * @Date 2021/3/24 10:22
     * @Auther YINZHIYU
     */
    public static Map<String, String> strToMap(String str, String keyValueSeparator) {
        if (ObjectUtil.isEmpty(str) || ObjectUtil.isEmpty(StrUtil.trim(str))) {
            return null;
        }
        Map<String, String> map = Maps.newHashMap();
        List<String> kv = StrSplitter.split(str, keyValueSeparator, true, true);
        map.put(kv.get(0), kv.get(1));
        return map;
    }

    /**
     * @Description 压缩
     * @Remark StandardCharsets.ISO_8859_1.name() 别动
     * @Params ==>
     * @Param str
     * @Param inEncoding
     * @Return java.lang.String
     * @Date 2021-06-22 10:14:27
     * @Auther YINZHIYU
     */
    public static String compress(String str, String charsetName) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes(charsetName));
        gzip.close();
        return out.toString(StandardCharsets.ISO_8859_1.name());

    }

    /**
     * @Description 解压缩
     * @Remark StandardCharsets.ISO_8859_1.name() 别动
     * @Params ==>
     * @Param str
     * @Param outEncoding
     * @Return java.lang.String
     * @Date 2021-06-22 10:14:42
     * @Auther YINZHIYU
     */
    public static String uncompress(String str, String charsetName) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes(StandardCharsets.ISO_8859_1));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toString(charsetName);
    }

    /**
     * @Description MD5加密
     * @Remark
     * @Params ==>
     * @Param data
     * @Return java.lang.String
     * @Date 2022-04-11 09:51:56
     * @Auther YINZHIYU
     */
    public static String strMd5(String data) {
        return MD5.create().digestHex(data);
    }

    public static void main(String[] args) {
        String taskData = base64Encode("我是一只小小小小鸟");
        Console.log("编码后 " + taskData);
        Console.log("字符串长度 " + taskData.length());
        String[] taskDataArray = StringUtils.splitString(taskData, 2);
        Console.log("数组长度 " + taskDataArray.length);
        StringBuilder sb = new StringBuilder();
        for (String s : taskDataArray) {
            sb.append(s);
        }
        Console.log("解码后 " + base64Decode(sb.toString()));
        Console.log(base64Decode(sb.toString()).substring(1, 8));
        String ex = "1234567";
        String str = ex.length() > 8 ? ex.substring(0, 8) : ex;
        Console.log(str);
    }
}




