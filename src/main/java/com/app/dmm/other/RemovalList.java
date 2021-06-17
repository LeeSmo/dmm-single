package com.app.dmm.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
/**
 *使用LinkedHashSet删除arraylist中的重复项
 */

public class RemovalList {

    public static void main(String[] args)

    {

        ArrayList<Integer> numbersList = new ArrayList<>(Arrays.asList(1, 1, 2, 3, 3, 3, 4, 5, 6, 6, 6, 7, 8));

        System.out.println(numbersList);

        LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(numbersList);

        ArrayList<Integer> listWithoutDuplicates = new ArrayList<>(hashSet);

        System.out.println(listWithoutDuplicates);

    }
}
