package com.bless.java8;

import com.bless.enums.Gender;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;


/**
 * Created by wangxi on 2019/7/2.
 */
@Slf4j
public class SortTest {

   static Integer[] numbers = {32,45,35,74,12,23,43,63,57,87,25,12,16,62,68,99,76};
    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {

//        insertSort();
//        selectSort();
//        bubbleSort();
//        quickSort(numbers);
//        for(int i=0;i< numbers.length;i++){
//            System.out.println(numbers[i]);
//        }

    }



    //直接插入排序
    public static void insertSort(){
        for (int i = 1; i < numbers.length; i++) {
            int tmp = numbers[i];
            int j = i - 1;
            for (; j >= 0 ; j--) {
                //跟前面的所有值相比较
                if (tmp < numbers[j]){
                    numbers[j+1] = numbers[j];
                    numbers[j] = tmp;
                }
            }
        }
    }

    //简单选择排序
    public static void selectSort(){
        for (int i = 0; i < numbers.length; i++) {
            int position = i;
            int j = i + 1;
            int tmp = numbers[i];
            for (; j < numbers.length; j++) {
                if (tmp > numbers[j]){
                    position = j;
                    tmp = numbers[j];
                }
            }
            //每次取出最小值和i换位置
            numbers[position] = numbers[i];
            numbers[i] = tmp;
        }
    }

    //冒泡排序
    public static void bubbleSort(){
        for (int i = 0; i < numbers.length-1; i++) {
            for (int j = 0; j < numbers.length -i - 1; j++) {
                if (numbers[j+1] < numbers[j]){
                    int tmp = numbers[j+1];
                    numbers[j+1] = numbers[j];
                    numbers[j] = tmp;
                }
            }
        }
    }

    //快排
    public static void quickSort(Integer[] list){
        if (list.length > 0 ){
            quick(list,0,list.length - 1);
        }
    }

    public static void quick(Integer[] list,int low,int high){
        if (low < high){
            int middle = getMiddle(list,low,high);
            quick(list,low,middle - 1);
            quick(list,middle + 1,high);
        }
    }


    public static int getMiddle(Integer[] list,int low,int high){
        int base = list[low];
        while (low < high){
            while ( low < high &&  list[high] >= base){
                high--;
            }

            list[low] = list[high];

            while (low < high && list[low] <= base){
                low++;
            }

            list[high] = list[low];
        }
        list[low] = base;
        return low;
    }


}
