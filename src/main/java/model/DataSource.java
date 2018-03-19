package model;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class DataSource {
    private String data;
    private String filter;
    private JSONObject obj;

    public DataSource() {
        data = DataStore.data;
        filter = DataStore.filter;
        JSONObject obj = JSON.parseObject(data);
        this.obj = obj;

    }

    public JSONObject GetJson() {
        return this.obj;
    }

    public String GetDataText() {
        return this.data;
    }

    public String GetFilterText() {
        return this.filter;
    }
}