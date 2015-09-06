package com.example.rk.ga;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by RK on 2015/8/4.
 */
public class zhongqun {

    public static void getZhongQun(){
        List<String> zhongqun=new ArrayList<String>();
        for (int j=0;j<5;j++) {
            StringBuilder geti = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < 22; i++) {
                geti.append(Math.abs(random.nextInt() % 2));
            }
            zhongqun.add(geti.toString());
        }
        Log.e("TAG",zhongqun.toString());
        List<Float> shiyindu=getshiyingdu(zhongqun);
        getnewzhongqun(zhongqun,shiyindu);

        //根据适应度 选出新中裙


    }

    public static List<String> getnewzhongqun(List<String> list,List<Float> shiyindu){
        List<String> newzgongqun=new ArrayList<String>();
        Random random=new Random();
        for (int i=0;i<list.size();i++){
           float a=random.nextFloat();
            if (a<shiyindu.get(0)){
                newzgongqun.add(list.get(0));
            }else if (a<(shiyindu.get(0)+shiyindu.get(1))){
                newzgongqun.add(list.get(1));
            }else if (a<(shiyindu.get(0)+shiyindu.get(1)+shiyindu.get(2))){
                newzgongqun.add(list.get(2));
            }else if (a<(shiyindu.get(0)+shiyindu.get(1)+shiyindu.get(2)+shiyindu.get(3))){
                newzgongqun.add(list.get(3));
            }else {
                newzgongqun.add(list.get(4));
            }

        }



        Log.e("TAG", ""+newzgongqun.toString());
        return newzgongqun;
    }


    public static List<Float> getshiyingdu(List<String> list){
        List<Float> shiyindu=new ArrayList<Float>();
        int sum=0;
        for (String s:list){
            sum+=Integer.valueOf(s,2);
            Log.e("TAG", ""+sum);
        }

        for (String s:list){
            shiyindu.add(Float.valueOf(Integer.valueOf(s, 2)) / sum);
            Log.e("TAG", "" + Float.valueOf(Integer.valueOf(s,2))/sum);
        }
        return shiyindu;


    }
}
