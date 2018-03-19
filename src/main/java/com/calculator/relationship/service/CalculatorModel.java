package com.calculator.relationship.service;

import model.DataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CalculatorModel {
    private String result;
    private String inputText;

    private DataSource src;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        inputText = inputText.replaceFirst("我的爸爸", ",f");
        inputText = inputText.replaceFirst("我的父亲", ",f");
        inputText = inputText.replaceAll("的父亲", ",f");
        inputText = inputText.replaceAll("的爸爸", ",f");
        inputText = inputText.replaceFirst("我的妈妈", ",m");
        inputText = inputText.replaceAll("的妈妈", ",m");
        inputText = inputText.replaceFirst("我的母亲", ",m");
        inputText = inputText.replaceAll("的母亲", ",m");
        inputText = inputText.replaceFirst("我的哥哥", ",ob");
        inputText = inputText.replaceAll("的哥哥", ",ob");
        inputText = inputText.replaceFirst("我的兄长", ",ob");
        inputText = inputText.replaceAll("的兄长", ",ob");
        inputText = inputText.replaceFirst("我的弟弟", ",lb");
        inputText = inputText.replaceAll("的弟弟", ",lb");
        inputText = inputText.replaceFirst("我的姐姐", ",os");
        inputText = inputText.replaceAll("的姐姐", ",os");
        inputText = inputText.replaceFirst("我的妹妹", ",ls");
        inputText = inputText.replaceAll("的妹妹", ",ls");
        inputText = inputText.replaceFirst("我的老公", ",h");
        inputText = inputText.replaceAll("的老公", ",h");
        inputText = inputText.replaceFirst("我的丈夫", ",h");
        inputText = inputText.replaceAll("的丈夫", ",h");
        inputText = inputText.replaceFirst("我的老婆", ",w");
        inputText = inputText.replaceAll("的老婆", ",w");
        inputText = inputText.replaceFirst("我的妻子", ",w");
        inputText = inputText.replaceAll("的妻子", ",w");
        inputText = inputText.replaceFirst("我的儿子", ",s");
        inputText = inputText.replaceAll("的儿子", ",s");
        inputText = inputText.replaceFirst("我的女儿", ",d");
        inputText = inputText.replaceAll("的女儿", ",d");
        this.inputText = inputText;
        System.out.println("inputText=" + inputText);
    }

    public CalculatorModel() {
        result = "";
        inputText = "";
        try {
            this.src = new DataSource();
        } catch (Exception e) {
            e.printStackTrace();
            // Console.Write("ERROR: " + e.Message + "\n");
        }

    }

    public void GetResult() {
        result = "";

        Searcher searcher = new Searcher(src.GetJson());

        Filter simplifier = new Filter(src.GetFilterText());

        List<String> sim = simplifier.Execute(inputText, -1);

        Map<String, Boolean> record = new HashMap<String, Boolean>();

        if (sim.size() != 0) {
            for (String s : sim) {
                if (record.containsKey(s)) {
                    continue;
                } else {
                    record.put(s, true);
                    String res = searcher.Who(s);
                    if (res != "你们好像不是很熟哦~~ ") {
                        result += res;
                        result += "\n";
                    }
                }

            }
            if (result == "") {
                result = "你们好像不是很熟哦~~ ";
            }
        }
    }

    public static void main(String[] args) {
        CalculatorModel calculator = new CalculatorModel();
        calculator.setInputText("我的爸爸的妈妈的弟弟的哥哥的爸爸的弟弟的妹妹");
        calculator.GetResult();
        System.out.println("您应该称呼：" + calculator.getResult());
    }
}