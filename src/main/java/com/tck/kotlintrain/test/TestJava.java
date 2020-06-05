package com.tck.kotlintrain.test;

import java.util.Locale;

public class TestJava {

    public static void main(String[] args) {

        String formatText = getFormatText("http://m.sos.test.trial.link/use_medicine/expatiation/", new String[]{"0", "1", "2", "3"});
        System.out.println(formatText);
    }


    private static String getFormatText(String str, String[] paramAry) {
        //格式化模版内容
        for (int i = 0; i < paramAry.length; i++) {
            try {
                System.out.println(i);
                str = str.replaceAll(String.format(Locale.CHINA, "\\{%d\\}", i), paramAry[i]);
            } catch (Exception e) {
                return "ggffgffg";
            }
        }
        return str;
    }
}
