package com.hcu.polishnotationreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    TextView tvResult;
    EditText etInput;
    Button btnIslem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tvResult);
        etInput = (EditText) findViewById(R.id.editText);
        btnIslem = (Button) findViewById(R.id.btnIslem);

        btnIslem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input;
                String[] inputArray;
                input=etInput.getText().toString();
                inputArray = input.split(" ");
                Stack stack = new Stack();

                for(int i = 0;i<inputArray.length;i++){
                    if(isOperator(inputArray[i])){
                        stack.push(inputArray[i]);
                    }
                    else
                        break;
                }

                int syc=0;
                int stackCount= stack.size();
                int stringOpCount=0;

                while(inputArray.length!=1){
                    if(!isOperator(inputArray[syc])){
                        if(!isOperator(inputArray[syc+1])){
                            inputArray[syc] = String.valueOf(hesapla(Integer.valueOf(inputArray[syc]),Integer.valueOf(inputArray[syc+1]),(String)stack.pop()));
                            stackCount = stack.size();
                            String[] temp;
                            temp = removeElements(inputArray, syc-1, syc+1);
                            inputArray=null;
                            inputArray = temp;
                            syc=0;
                            stringOpCount=0;
                            continue;
                        }else{
                            syc++;
                            continue;
                        }
                    }else{
                        stringOpCount++;
                        if(stringOpCount>stackCount){
                            stack.push(inputArray[syc]);
                            stackCount = stack.size();
                            stringOpCount=0;
                        }
                        syc++;
                        continue;
                    }
                }
                tvResult.setText(inputArray[0]);
            }
        });
    }

    public static int hesapla(int value1,int value2,String operator){
        int result=0;
        switch(operator){
            case "+": result = value1 + value2; break;
            case "-": result = value1 - value2; break;
            case "*": result = value1 * value2; break;
            case "/": result = value1 / value2; break;
        }
        return result;
    }

    private static boolean isOperator(String inputchar) {
        boolean isOp;
        switch(inputchar){
            case "+": isOp = true; break;
            case "-": isOp = true; break;
            case "*": isOp = true; break;
            case "/": isOp = true; break;
            default:isOp = false;
        }
        return isOp;
    }

    public static String[] removeElements(String[] input, int first,int second) {
        List result = new ArrayList();
        for(int i=0;i<input.length;i++){
            result.add(input[i]);
        }
        result.remove(first);
        result.remove(second-1);
        String[] str = new String[result.size()];
        for(int i=0;i<result.size();i++){
            str[i] = (String) result.get(i);
        }
        return str;
    }
}
