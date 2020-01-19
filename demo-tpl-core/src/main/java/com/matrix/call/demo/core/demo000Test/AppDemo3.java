package com.matrix.call.demo.core.demo000Test;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Demo000
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class AppDemo3 {


    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        //String srcStr = "2-5-676164, 3-5-676164, 3-3-3952247, 2-3-3952247, 3-2-5504045, 2-2-5504045, 3-4-1910856, 2-4-99999, 3-1-11437, 2-1-11437";
        //String srcStr = "3-1-11437, 3-2-5504045, 3-3-3952247, 3-4-1910856, 3-5-676164, 2-1-11437, 2-2-5504045";
        //String srcStr = "3-1-11437, 3-2-5504045, 3-3-3952247, 3-4-1910856, 2-1-11437, 2-2-5504045";
        //String srcStr = "3-1-11437, 3-2-5504045, 2-1-11437, 2-2-5504045";
        //String srcStr = "3-1-11437, 3-2-5504045, 2-1-11437, 2-2-5504045, 2-3-3952247, 2-4-99999, 2-5-676164";
        //String srcStr = "2-3-10306395, 3-2-10306395, 3-3-216583240, 2-4-126221509, 3-5-71439352, 2-5-71439352, 2-2-31679580, 3-4-31679580, 2-1-19344409, 3-1-19344409";
        //String srcStr = "2-3-10306395, 3-2-10306395, 3-3-216583240, 2-4-126221509, 3-5-71439352, 2-5-71439352, 2-2-31679580, 3-4-31679580, 2-1-19344409, 3-1-19344409";
        String srcStr = "3-2-5504045, 3-1-11437, 3-3-3952247, 3-4-1910856, 3-5-676164, 2-1-676164, 2-2-5504045, 2-3-3952247, 2-4-99999, 2-5-11437";
        List<AchorRankInfo> achorRankInfos = calTop5AchorCard(srcStr, "71439352");

        for (AchorRankInfo achorRankInfo : achorRankInfos) {
            System.out.println(achorRankInfo.getUid());
        }
    }


    /**
     * 取观看时长最高的前3+打赏最多的前2，如两个维度有重复或数量不足，往后顺移
     3-1-11437
     3-2-5504045
     3-3-3952247
     3-4-1910856
     3-5-676164
     2-1-676164
     2-2-5504045
     2-3-3952247
     2-4-99999
     2-5-11437
     * @param srcStr
     * @return
     */
    private static List<AchorRankInfo> calTop5AchorCard(String srcStr, String userId) {
        // 转换为对象
        String[] srcArray = srcStr.split(", ");
        List<AchorRankInfo> srcList =  Arrays.stream(srcArray).map((src) -> {
            String[] dataSplit = src.split("-");
            return new AchorRankInfo(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Long.parseLong(dataSplit[2]));
        }).filter(anchorRankInfo->{
            String uidStr = String.valueOf(anchorRankInfo.getUid());
            return !uidStr.equals(userId);
        }).collect(Collectors.toList());
        // fix list
        List<AchorRankInfo> watchTimeOriginList = srcList.stream().filter(src -> (src.getDataType().intValue() == 3)).sorted(Comparator.comparing(AchorRankInfo::getRank)).collect(Collectors.toList());
        List<AchorRankInfo> yuchiOriginList = srcList.stream().filter(src -> (src.getDataType().intValue() == 2)).sorted(Comparator.comparing(AchorRankInfo::getRank)).collect(Collectors.toList());

        // 找到重复项 & 去重 (只去yuchi部分)
        List<Long> repeatUidList = Lists.newArrayList();
        List<Long> uniqueUidList = Lists.newArrayList();
        srcList.stream().forEach((src) -> {
            if (uniqueUidList.contains(src.getUid())) {
                repeatUidList.add(src.getUid());
            } else {
                uniqueUidList.add(src.getUid());
            }
        });
        List<AchorRankInfo> uniqueList = srcList.stream().filter((src) -> {
            if (src.getDataType() == 2 && repeatUidList.contains(src.getUid())) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());
        //System.out.println("去重复后大小: "+uniqueList.size());

        // 分组 & 合并
        List<AchorRankInfo> watchTimeList = uniqueList.stream().filter(src -> (src.getDataType().intValue() == 3)).sorted(Comparator.comparing(AchorRankInfo::getRank)).collect(Collectors.toList());
        List<AchorRankInfo> yuchiList = uniqueList.stream().filter(src -> (src.getDataType().intValue() == 2)).sorted(Comparator.comparing(AchorRankInfo::getRank)).collect(Collectors.toList());
        List<AchorRankInfo> mergeList = Lists.newArrayList();
        mergeList.addAll(watchTimeList);
        mergeList.addAll(yuchiList);

        // 取top6
        // 若 合并后 <6, 则 直接返回
        if (mergeList.size() < 6) { // fix
            return mergeList;
        }

        // 若 合并后 >=6,
        List<AchorRankInfo> resultList = Lists.newArrayList();
        resultList.addAll(mergeList.subList(0, 3));


        // 则替换index=3, 4, 5 ... 的值为鱼翅列表
        for (int i = 0; i < yuchiOriginList.size(); i++) { // fix 取去重前的
            AchorRankInfo yuchiBackup = yuchiOriginList.get(i);// fix 取去重前的
            if (resultList.contains(yuchiBackup)) {
                continue;
            }
            resultList.add(yuchiBackup);
        }
        if (resultList.size() < 6) { // 尼玛又要补观看的
            for (int i = 0; i < watchTimeOriginList.size(); i++) { // fix 取去重前的
                AchorRankInfo wtBackup = watchTimeOriginList.get(i);// fix 取去重前的
                if (resultList.contains(wtBackup)) {
                    continue;
                }
                resultList.add(wtBackup);
            }
            if (resultList.size() < 6) {
                return resultList;
            } else {
                return resultList.subList(0, 6);
            }

        } else { // 刚刚满足 3+3
            return resultList.subList(0, 6);
        }


    }


}

class AchorRankInfo {
    private Integer dataType;
    private Integer rank;
    private Long uid;


    public AchorRankInfo(Integer dataType, Integer rank, Long uid) {
        this.dataType = dataType;
        this.rank = rank;
        this.uid = uid;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AchorRankInfo that = (AchorRankInfo) o;

        return new EqualsBuilder()
                .append(uid, that.uid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(uid)
                .toHashCode();
    }
}
