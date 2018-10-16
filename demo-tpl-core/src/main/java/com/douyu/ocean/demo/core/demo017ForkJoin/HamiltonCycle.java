package com.douyu.ocean.demo.core.demo016ForkJoin;

import java.util.List;
import java.util.WeakHashMap;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 深度优先 + 穷举 + 微信 + 一笔画完 + java
 *
 * @author weiqi
 * @create 2018-04-18 8:56:00
 */
public class HamiltonCycle {


    private static final Logger LOGGER = LoggerFactory.getLogger(HamiltonCycle.class);

    private int[][] matrix;
    private int[] startPos;
    private int[] endPos;
    public int fullPathCount=0;
    public int mis_path_count=0;
    public int correct_path_count=0;
    public List<int[]>  resultPath;


    public HamiltonCycle(int[][] matrix, int[] startPos, int[] endPos) {
        this.matrix = matrix;
        this.startPos = startPos;
        this.endPos = endPos;
        this.fullPathCount = findFullPathCount();
        //System.out.println(fullPathCount);
    }


    public static void main(String[] args) throws InterruptedException {
        HamiltonCycle cycle = null;
        int[] startPos;
        int[] endPos;
        // matrix[2][2] --> matrix[0][1]=  2,2 + 1,2 + 1,3 + 2,3 + 3,3 + 3,2 + 3,1 + 2,1 + 2,0 + 1,0 + 1,1 + 0,1
        int [][] matrix = {
          //0 , 1, 2, 3
            {0, 1, 0, 0}, //0
            {1, 1, 1, 1}, //1
            {1, 1, 1, 1}, //2
            {0, 1, 1, 1}, //3

        };

        startPos = new int[]{2, 2};
        endPos = new int[]{0, 1};
        cycle = new HamiltonCycle(matrix, startPos, endPos);
        cycle.findPath(startPos, Lists.newArrayList(startPos));
        cycle.showResult();

        // matrix[2][0] --> matrix[0][0]=  2,0 + 3,0 + 3,1 + 3,2 + 3,3 + 2,3 + 2,2+ 2,1 + 1,1 + 1,2 + 0,2 + 0,1 + 0,0
        int [][] matrix2 = {
          //0 , 1, 2, 3
            {1, 1, 1, 0}, //0
            {0, 1, 1, 0}, //1
            {1, 1, 1, 1}, //2
            {1, 1, 1, 1}, //3

        };
        startPos = new int[]{2, 0};
        endPos = new int[]{0, 0};
        cycle = new HamiltonCycle(matrix2, startPos, endPos);
        cycle.findPath(startPos, Lists.newArrayList(startPos));
        cycle.showResult();
        // matrix55
        int [][] matrix55 = {
            {1, 1, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 0},


        };
        startPos = new int[]{3, 3};
        endPos = new int[]{0, 5};
        cycle = new HamiltonCycle(matrix55, startPos, endPos);
        cycle.findPath(startPos, Lists.newArrayList(startPos));
        cycle.showResult();
    }

    private void showResult() {
        System.out.print("Cycle: ");
        resultPath.forEach(path -> System.out.print((path[0])  +","+ (path[1])  +" + "));
        System.out.println("");
        System.out.println("mis_path_count: "+ mis_path_count + " && correct_path_count: " +correct_path_count);
        System.out.println("-----------------------------");
    }

    private void findPath(int[] current, List<int[]> pathList) {
        if(current[0] == this.endPos[0] && current[1]== this.endPos[1]){
            if (fullPathCount != pathList.size()) {
                mis_path_count++;
                return;
            }else{
                correct_path_count++;
                this.resultPath = pathList;
                return;
            }



        }
        int up = current[0] -1;
        int down = current[0] +1;
        int left = current[1] -1;
        int right = current[1] +1;
        int[] pre_pos = pathList.get(pathList.size()-1);

        int[] next_up_pos = {up, current[1]};
        if(up >=0 && matrix[up][current[1]] != 0 && !checkIsOldPos(next_up_pos, pathList)){ //上
            List<int[]> branchList = copyList(pathList);
            branchList.add(next_up_pos);
            findPath(next_up_pos, branchList);
            //return;
        }

        int[] next_down_pos = {down, current[1]};
        if(down < matrix.length && matrix[down][current[1]] != 0 && !checkIsOldPos(next_down_pos, pathList)){
            List<int[]> branchList = copyList(pathList);
            branchList.add(next_down_pos);
            findPath(next_down_pos, branchList);
            //return;
        }
        int[] next_left_pos = {current[0], left};
        if (left >= 0 && matrix[current[0]][left] != 0 && !checkIsOldPos(next_left_pos, pathList)){
            List<int[]> branchList = copyList(pathList);
            branchList.add(next_left_pos);
            findPath(next_left_pos, branchList);
            //return;
        }

        int[] next_right_pos = {current[0], right};
        if (right < matrix[current[0]].length && matrix[current[0]][right] != 0 && !checkIsOldPos(next_right_pos, pathList)){
            List<int[]> branchList = copyList(pathList);
            branchList.add(next_right_pos);
            findPath(next_right_pos, branchList);
            //return;
        }
    }

    private boolean checkIsOldPos(int[] pos, List<int[]> pathList) {
        for (int i = 0; i < pathList.size(); i++) {
            if (pathList.get(i)[0] == pos[0] && pathList.get(i)[1] == pos[1]) {
                return true;
            }
        }
        return false;
    }
    private List<int[]> copyList( List<int[]> pathList) {
        List<int[]> copyList = Lists.newArrayList();
        for (int i = 0; i < pathList.size(); i++) {
            copyList.add(pathList.get(i));
        }
        return copyList;
    }
    private int findFullPathCount() {
        int count=0;
        for (int i = 0; i < matrix.length; i++) {
            int[] matrix_lie = matrix[i];
            for (int j = 0; j < matrix_lie.length; j++) {
                if(matrix_lie[j] == 1){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 休眠辅助方法
     * @param milliSecond
     */
    public static void sleep(long milliSecond) {
        try {
            if (milliSecond <= 0L) {
                return;
            }
            Thread.sleep(milliSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
