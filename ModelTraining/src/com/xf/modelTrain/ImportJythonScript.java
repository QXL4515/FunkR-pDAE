package com.xf.modelTrain;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class ImportJythonScript {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            Process proc=Runtime.getRuntime().exec("python ../model/model.py"); //执行py文件
            InputStreamReader stdin=new InputStreamReader(proc.getInputStream());
            LineNumberReader input=new LineNumberReader(stdin);
            String line;
            while((line=input.readLine())!=null ){
                System.out.println(line);//得到输出
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}