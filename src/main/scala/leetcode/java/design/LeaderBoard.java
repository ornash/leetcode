package leetcode.java.design;

import java.util.*;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class LeaderBoard {
    public static void main(String[] args) {
        int[] scores = new int[90000000];
        for(int i = 0 ; i < scores.length; i++){
            scores[i] = (int)(Math.random() * 1000000);
        }

        long start = System.currentTimeMillis();
        Arrays.sort(scores);
        System.out.println(System.currentTimeMillis() - start);

        for(int i = scores.length -1 ; i > scores.length - 100; i--){
            System.out.println(scores[i]);
        }
    }
}
