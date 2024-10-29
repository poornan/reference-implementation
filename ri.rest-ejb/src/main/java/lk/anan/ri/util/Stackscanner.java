package lk.anan.ri.util;

import java.util.Arrays;

public class Stackscanner {
    public static void getCaller(){
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
    }
}
