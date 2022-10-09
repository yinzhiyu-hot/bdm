package cn.wangoon.ms.bdm.core.common.cache.impl;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.wangoon.ms.bdm.core.common.cache.BaseCache;
import cn.wangoon.ms.bdm.core.common.constants.SysBaseConfigConstants;
import cn.wangoon.ms.bdm.core.common.enums.DelFlagEnum;
import cn.wangoon.ms.bdm.core.domain.entity.base.SysBaseConfig;
import cn.wangoon.ms.bdm.core.service.base.SysBaseConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @Description 基础配置缓存
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-04-06 22:45:14
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Component
@Slf4j
public class SysBaseConfigCache implements BaseCache {

    /**
     * sys_base_config 配置map
     * KEY -> biz_type  Value Map for bizType
     */
    public final static ConcurrentMap<String, Map<String, List<SysBaseConfig>>> SYS_BASE_CONFIG_MAP = Maps.newConcurrentMap();

    @Resource
    private SysBaseConfigService sysBaseConfigService;

    @Override
    public void init() {
        log.info("加载sysBaseConfigMap");
        QueryWrapper<SysBaseConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SysBaseConfig.COL_DEL_FLAG, DelFlagEnum.NO.getFlag());
        List<SysBaseConfig> sysBaseConfigs = sysBaseConfigService.list(queryWrapper);
        convertMap(sysBaseConfigs);
    }

    /**
     * @Description 转换map
     * @Params ==>
     * @Param sysBaseConfigs
     * @Return void
     * @Date 2020/4/26 11:01
     * @Auther YINZHIYU
     */
    private void convertMap(List<SysBaseConfig> sysBaseConfigs) {

        if (ObjectUtil.isNotEmpty(sysBaseConfigs)) {

            SYS_BASE_CONFIG_MAP.clear();

            //biz_type 分组
            Map<String, List<SysBaseConfig>> listBizTypeMap = sysBaseConfigs.stream()
                    .collect(Collectors.groupingBy(SysBaseConfig::getBizType));

            for (Map.Entry entry : listBizTypeMap.entrySet()) {
                //biz_key分组
                Map<String, List<SysBaseConfig>> listBizKeyMap = listBizTypeMap.get(entry.getKey().toString()).stream()
                        .collect(Collectors.groupingBy(SysBaseConfig::getBizKey));
                SYS_BASE_CONFIG_MAP.put(entry.getKey().toString(), listBizKeyMap);
            }
        }
    }

    /**
     * @Description 获取单个配置值
     * @Params ==>
     * @Param bizType
     * @Param bizKey
     * @Param defaultValue
     * @Return T
     * @Date 2020/6/15 15:16
     * @Auther YINZHIYU
     */
    public static <T> T getSysBaseConfigFromGlobalMap(String bizType, String bizKey, T defaultValue) {
        Map<String, List<SysBaseConfig>> listMap = null;
        if (ObjectUtil.isNotEmpty(bizType)) {
            listMap = SYS_BASE_CONFIG_MAP.get(bizType);
        }
        if (ObjectUtil.isEmpty(listMap)) {
            return defaultValue;
        }
        List<SysBaseConfig> sysBaseConfigs = null;
        if (ObjectUtil.isNotEmpty(bizKey)) {
            sysBaseConfigs = listMap.get(bizKey);
        }
        if (ObjectUtil.isEmpty(sysBaseConfigs)) {
            return defaultValue;
        }
        SysBaseConfig sysBaseConfig = sysBaseConfigs.get(0);
        if (ObjectUtil.isEmpty(sysBaseConfig.getBizValue())) {
            return defaultValue;
        }
        if (!ClassUtil.getClass(defaultValue).isPrimitive()) {
            return ReflectUtil.invoke(defaultValue, "valueOf", sysBaseConfig.getBizValue());
        }
        return (T) sysBaseConfig.getBizValue();
    }

    /**
     * @Description 获取单个配置值
     * @Params ==>
     * @Param bizType
     * @Param bizKey
     * @Param defaultValue
     * @Return T
     * @Date 2020/6/15 15:16
     * @Auther YINZHIYU
     */
    public static <T> T getSysBaseConfigFromGlobalMapByValue(String bizType, String bizValue) {
        Map<String, List<SysBaseConfig>> listMap = null;
        if (ObjectUtil.isNotEmpty(bizType)) {
            listMap = SYS_BASE_CONFIG_MAP.get(bizType);
        }
        if (ObjectUtil.isEmpty(listMap)) {
            return null;
        }
        List<SysBaseConfig> list = Lists.newArrayList();
        listMap.values().forEach(list::addAll);

        List<SysBaseConfig> r = list.stream().filter(item -> bizValue.equals(item.getBizValue())).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(r)) {
            return (T) r.get(0).getBizKey();
        }

        return null;
    }

    /**
     * 根据key和value获取type
     *
     * @param bizKey
     * @param bizValue
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T getSysBaseConfigFromGlobalMapTypeByKeyAndValue(String bizKey, String bizValue, T defaultValue) {
        List<SysBaseConfig> configList = Lists.newArrayList();
        SYS_BASE_CONFIG_MAP.forEach((key, value) -> value.values().forEach(keyItemList -> keyItemList.forEach(keyItem -> {
            if (StrUtil.equalsIgnoreCase(bizKey, keyItem.getBizKey()) && StrUtil.equalsIgnoreCase(bizValue, keyItem.getBizValue())) {
                configList.add(keyItem);
            }
        })));
        if (ObjectUtil.isEmpty(configList)) {
            return defaultValue;
        }
        return (T) configList.get(0).getBizType();
    }

    /**
     * @Description 获取单个配置值（忽略bizType,bizKey大小写）
     * @Params ==>
     * @Param bizType
     * @Param bizKey
     * @Param defaultValue
     * @Return T
     * @Date 2020/6/15 15:16
     * @Auther YINZHIYU
     */
    public static <T> T getSysBaseConfigFromGlobalMapIgnoreCase(String bizType, String bizKey, T defaultValue) {
        Map<String, List<SysBaseConfig>> listMap = Maps.newHashMap();
        SYS_BASE_CONFIG_MAP.forEach((key, value) -> {
            if (ObjectUtil.isNotEmpty(bizType) && Objects.equals(key.toUpperCase(), bizType.toUpperCase()) && ObjectUtil.isNotEmpty(value)) {
                listMap.putAll(value);
            }
        });
        if (ObjectUtil.isEmpty(listMap)) {
            return defaultValue;
        }
        List<SysBaseConfig> sysBaseConfigs = Lists.newArrayList();
        listMap.forEach((key, value) -> {
            if (ObjectUtil.isNotEmpty(bizKey) && Objects.equals(key.toUpperCase(), bizKey.toUpperCase()) && ObjectUtil.isNotEmpty(value)) {
                sysBaseConfigs.addAll(value);
            }
        });
        if (ObjectUtil.isEmpty(sysBaseConfigs)) {
            return defaultValue;
        }
        SysBaseConfig sysBaseConfig = sysBaseConfigs.get(0);
        if (ObjectUtil.isEmpty(sysBaseConfig.getBizValue())) {
            return defaultValue;
        }
        if (!ClassUtil.getClass(defaultValue).isPrimitive()) {
            return ReflectUtil.invoke(defaultValue, "valueOf", sysBaseConfig.getBizValue());
        }
        return (T) sysBaseConfig.getBizValue();
    }

    /**
     * @Description 获取批量配置值
     * @Params ==>
     * @Param bizType
     * @Param bizKey
     * @Return java.util.List<cn.wangoon.domain.entity.SysBaseConfig>
     * @Date 2020/6/15 15:21
     * @Auther YINZHIYU
     */
    public static List<SysBaseConfig> getSysBaseConfigListFromGlobalMap(String bizType) {
        Map<String, List<SysBaseConfig>> listMap = null;
        if (ObjectUtil.isNotEmpty(bizType)) {
            listMap = SYS_BASE_CONFIG_MAP.get(bizType);
        }
        List<SysBaseConfig> sysBaseConfigs = Lists.newArrayList();
        if (ObjectUtil.isNotEmpty(listMap) && ObjectUtil.isNotEmpty(listMap.values())) {
            listMap.values().forEach(sysBaseConfigs::addAll);
        }

        return sysBaseConfigs;
    }

    /**
     * @Description 获取批量配置值
     * @Params ==>
     * @Param bizType
     * @Param bizKey
     * @Return java.util.List<cn.wangoon.domain.entity.SysBaseConfig>
     * @Date 2020/6/15 15:21
     * @Auther YINZHIYU
     */
    public static List<SysBaseConfig> getSysBaseConfigListFromGlobalMap(String bizType, String bizKey) {
        Map<String, List<SysBaseConfig>> listMap = null;
        if (ObjectUtil.isNotEmpty(bizType)) {
            listMap = SYS_BASE_CONFIG_MAP.get(bizType);
        }
        List<SysBaseConfig> sysBaseConfigs = Lists.newArrayList();
        if (ObjectUtil.isNotEmpty(listMap) && ObjectUtil.isNotEmpty(bizKey) && ObjectUtil.isNotEmpty(listMap.get(bizKey))) {
            sysBaseConfigs.addAll(listMap.get(bizKey));
        }
        return sysBaseConfigs;
    }

    /**
     * @Description 获取批量配置值（忽略bizType,bizKey大小写）
     * @Params ==>
     * @Param bizType
     * @Param bizKey
     * @Return java.util.List<cn.wangoon.domain.entity.SysBaseConfig>
     * @Date 2020/6/15 15:21
     * @Auther YINZHIYU
     */
    public static List<SysBaseConfig> getSysBaseConfigListFromGlobalMapIgnoreCase(String bizType, String bizKey) {
        Map<String, List<SysBaseConfig>> listMap = Maps.newHashMap();
        SYS_BASE_CONFIG_MAP.forEach((key, value) -> {
            if (ObjectUtil.isNotEmpty(bizType) && Objects.equals(key.toUpperCase(), bizType.toUpperCase()) && ObjectUtil.isNotEmpty(value)) {
                listMap.putAll(value);
            }
        });
        List<SysBaseConfig> sysBaseConfigs = Lists.newArrayList();
        if (ObjectUtil.isEmpty(listMap)) {
            return sysBaseConfigs;
        }
        listMap.forEach((key, value) -> {
            if (ObjectUtil.isNotEmpty(bizKey) && Objects.equals(key.toUpperCase(), bizKey.toUpperCase()) && ObjectUtil.isNotEmpty(value)) {
                sysBaseConfigs.addAll(value);
            }
        });
        return sysBaseConfigs;
    }

    /**
     * @param bizType 获取所有type的集合
     * @return
     */
    public static List<SysBaseConfig> getAllBizType(String bizType) {
        Map<String, List<SysBaseConfig>> listMap = null;
        if (ObjectUtil.isNotEmpty(bizType)) {
            listMap = SYS_BASE_CONFIG_MAP.get(bizType);
        }
        List<SysBaseConfig> result = Lists.newArrayList();
        if (ObjectUtil.isNotEmpty(listMap) && ObjectUtil.isNotEmpty(listMap.values())) {
            listMap.values().forEach(result::addAll);
        }
        return result;
    }

    /**
     * @Description 清洗前缀
     * @Remark
     * @Params ==>
     * @Param str
     * @Return java.lang.String
     * @Date 2022-09-27 14:32:26
     * @Auther YINZHIYU
     */
    public static String cleaningPrefix(String str) {
        if (ObjectUtil.isEmpty(str)) {
            return str;
        }

        //获取基础配置中配置的需要移除前缀的后缀列表
        List<String> prefixRemoveList = new ArrayList<String>() {{
            List<SysBaseConfig> values = getSysBaseConfigListFromGlobalMap(SysBaseConfigConstants.PREFIX, SysBaseConfigConstants.PREFIX_REMOVE);
            if (ObjectUtil.isNotEmpty(values)) {
                values.forEach(sysBaseConfig -> this.add(sysBaseConfig.getBizValue()));
            }
        }};

        if (ObjectUtil.isNotEmpty(prefixRemoveList)) {
            for (String prefixRemove : prefixRemoveList) {
                if (str.toUpperCase().startsWith(prefixRemove.toUpperCase())) {
                    str = StrUtil.removePrefixIgnoreCase(str, prefixRemove);
                    break;
                }
            }
        }
        if (ObjectUtil.isNotEmpty(str)) {
            str = StrUtil.trimStart(str);
        }
        return str;
    }

    /**
     * @Description 清洗后缀
     * @Remark
     * @Params ==>
     * @Param str
     * @Return java.lang.String
     * @Date 2022-09-27 14:32:40
     * @Auther YINZHIYU
     */
    public static String cleaningSuffix(String str) {
        if (ObjectUtil.isEmpty(str)) {
            return str;
        }

        //获取基础配置中配置的需要移除后缀的后缀列表
        List<String> suffixRemoveList = new ArrayList<String>() {{
            List<SysBaseConfig> values = getSysBaseConfigListFromGlobalMap(SysBaseConfigConstants.SUFFIX, SysBaseConfigConstants.SUFFIX_REMOVE);
            if (ObjectUtil.isNotEmpty(values)) {
                values.forEach(sysBaseConfig -> this.add(sysBaseConfig.getBizValue()));
            }
        }};

        if (ObjectUtil.isNotEmpty(suffixRemoveList)) {
            for (String suffixRemove : suffixRemoveList) {
                if (str.toUpperCase().endsWith(suffixRemove.toUpperCase())) {
                    str = StrUtil.removeSuffixIgnoreCase(str, suffixRemove);
                    break;
                }
            }
        }
        if (ObjectUtil.isNotEmpty(str)) {
            str = StrUtil.trimEnd(str);
        }
        return str;
    }
}
