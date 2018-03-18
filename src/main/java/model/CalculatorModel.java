package model;

import com.calculator.relationship.service.DataSource;
import com.calculator.relationship.service.Filter;
import com.calculator.relationship.service.Searcher;

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
        this.inputText = inputText;
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

    public static void main(String[] args){
        CalculatorModel calculator = new CalculatorModel();
        calculator.setInputText("我的爸爸的爸爸");
        calculator.GetResult();
        System.out.println("result="+calculator.getResult());
    }
}