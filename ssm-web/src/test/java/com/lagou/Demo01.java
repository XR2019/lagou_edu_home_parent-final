package com.lagou;

import java.util.Arrays;

public class Demo01 {

    /*
        快速排序的实现：双边循环法
     */
    public static void main(String[] args) {
        int[] arr = new int[]{4,7,3,5,6,2,8,1};
        quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
    public static void quickSort(int[] arr,int startIndex,int endIndex) {
        //递归结束的条件
        if (startIndex >= endIndex) {
            return;
        }
        //得到基准值
        int provit = partition(arr,startIndex,endIndex);
        quickSort(arr,startIndex,provit-1);
        quickSort(arr,provit+1,endIndex);
    }
    public static int partition(int[] arr,int startIndex,int endIndex) {
        //我们可以取数组中第一个值为基准值
        int provit = arr[startIndex];
        int left = startIndex;
        int right = endIndex;
        while (left != right) {
            //控制right指针向左移
            while (left < right && arr[right] > provit) {
                right--;
            }
            //控制left指针右移
            while (left < right && arr[left] <= provit) {
                left++;
            }
            //否则交换left和right指针所对应的元素
            if (left <= right) {
                int p = arr[left];
                arr[left] = arr[right];
                arr[right] = p;
            }
        }
        //当right和left指针所指向的元素下标一样时候，就和provit基准元素交换值
        arr[startIndex] = arr[left];
        arr[left] = provit;
        return left;
    }
}
