package com.example.rk.ga.ga;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by RK on 2015/8/5.
 */
public class Population {
    /*种群属于第几代*/
    public int generations;
    /*种群个体容器*/
    public List<String> individuals;
    //个体表现型
    public List<Float> individual;
    /*种群个体适应度*/
    private List<Float> fitness;
    /*个体基因链长度*/
    private int gene=22;
    /*遗传算子组合交叉概率*/
    private double crossover=0.25;
    /*遗传算子变异概率*/
    private double mutation=0.01;

    public Population(int generation){
        this.generations=generation;
        if (generation==0)coding(20,gene);//如果是初代，进行基因编码，产生初代个体
    }
    public void heredity(){
        setFitness();//评估
        selection();//选择
        setCrossover();//交叉
        setMutation();//变异
        setPhenotype();//表现型
        generations+=1;
    }
    /**
     * 基因编码，产生初代个体
     * @number个体数量
     * @length基因编码长度
     */
    public void coding(int number,int length){
        individuals=new ArrayList<String>();
        for (int j=0;j<number;j++) {
            StringBuilder geti = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                geti.append(Math.abs(random.nextInt() % 2));
            }
            individuals.add(geti.toString());
        }
        //Log.e("TAG", "" + individuals.toString());
    }
    /**
     * 解码
     * 返回2进制字符串所对应的实际x坐标
     */
    public double decoding(String s){
        double x=-1.0 + Math.abs(Integer.valueOf(s, 2)) * (2+1)/(Math.pow(2,gene)-1);

        return x;
    }
    /**
     * 适应度评估
     * @individuals 种群个体容器
     * @fitness 种群个体适应度
     */
    public void setFitness(){
        individual=new ArrayList<Float>();//基因所对应的具体值
        fitness=new ArrayList<Float>();
        float sum=0;
        for (String s:individuals){
            double x=decoding(s);
            sum+=x*Math.sin(10 * Math.PI * x)+2.0;
        }
        //Log.e("TAG", "" +sum);
        float temp;
        float fit=0;
        for (String s:individuals){
            float x=(float)decoding(s);
            float y= (float) (x*Math.sin(10 * Math.PI * x)+2.0);
            temp=(y)/sum;
            fit+=temp;
            individual.add(x);
            fitness.add(temp);
            Log.e("q","x = "+x+ "y = " + y);
        }
        Log.e("TAG", "总适应度" + fit);
    }
    /**
     * 选择 采用轮盘赌博法
     * 根据适应度选出新个体     *
     */
    public void selection(){
        /*构建轮盘*/
        List<Float> lunpan=new ArrayList<Float>();
        float temp=0;
        for (int i=0;i<individuals.size();i++){
            temp+=fitness.get(i);
            lunpan.add(temp);
        }
        /*选出具有新的个体*/
        ArrayList<Integer> list=new ArrayList<>();
        Random random=new Random();
        for (int j=0;j<individuals.size();j++){
            float pointer=random.nextFloat();//转动轮盘，指针位置为pointer
            int i=0;
            do {
            }while (pointer>lunpan.get(i++));
            list.add(i-1);
        }
        List<String> individual=new ArrayList<>();
        for (Integer i:list){
            individual.add(individuals.get(i));
        }
        individuals=individual;
        Log.e("TAG", "轮盘选择个体 ： " + list.toString());
    }
    /**
     * 交叉变异
     */
    public void setCrossover(){
       /*对选出的个体俩俩配对*/
        ArrayList<Integer> sort=new ArrayList<>();//排序结果
        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<individuals.size();i++){
            list.add(i);
        }
        Random random=new Random();
        do {
            int a = Math.abs(random.nextInt() % list.size());
            sort.add(list.get(a));
            list.remove(a);
            if (list.size()==1){ sort.add(list.get(0));break;}
            if (list.size()==0){break;}
        }while (true);
        //配对交叉
        for (int i=0;i<sort.size();i+=2){
            if (random.nextFloat()<crossover)break;
            Log.i("TAG", "交叉");
            if (i==sort.size()-1){break;}
            int i1=sort.get(i);
            int i2=sort.get(i+1);
            String s1=individuals.get(i1);
            String s2=individuals.get(i2);
            int a=Math.abs(random.nextInt()%gene);//交叉的位置
            /*交叉*/
            individuals.set(i1,s1.substring(0,a)+s2.substring(a));
            individuals.set(i2,s2.substring(0,a)+s1.substring(a));
            if (i==sort.size()-1){break;}
        }
        Log.i("TAG", "交叉后的个体基因  ：" + individuals.toString());
    }
    /**
     * 变异
     */
    public void setMutation(){
        Random random=new Random();
        for (int j=0;j<individuals.size();j++){
            char[] c=individuals.get(j).toCharArray();
            for (int i=0;i<c.length;i++){
                if (mutation>random.nextFloat()){
                    c[i]=c[i]=='1'?'0':'1';
                    Log.i("TAG", "" + "字符" + i );
                }
            }
            individuals.set(j, String.valueOf(c));
        }
    }
    public void setPhenotype(){

    }

}
