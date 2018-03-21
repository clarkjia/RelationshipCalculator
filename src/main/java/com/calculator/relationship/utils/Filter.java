package com.calculator.relationship.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Filter {
    private final static Logger LOG = LoggerFactory.getLogger(Filter.class);
    private JSONObject[] _Filter;
    private int _FilterSize;
    private String filter;

    public Filter(String filter) {
        this.filter = filter;
        String[] _filter = filter.split("%");
        JSONObject[] _temp = new JSONObject[50];
        int j = 0;
        for (int i = 0; i < _filter.length - 1; i++) {
            _temp[j++] = JSONObject.parseObject(_filter[i]);
        }
        this._Filter = _temp;
        this._FilterSize = j - 1;

    }

    private void GetId(String selector, Map<String, Boolean> hash, ArrayList result) {
        String s = "";
        if (!hash.containsKey(selector)) {
            hash.put(selector, true);
            String status = "true";
            do {
                s = selector;

                for (int i = 0; i < _FilterSize + 1; i++) {

                    String patterm = _Filter[i].getString("exp").toString();

                    String str = _Filter[i].getString("str").toString();

                    selector = selector.replaceAll(patterm, str);

                    if (selector.indexOf('#') > -1) {
                        String[] arr = selector.split("#");

                        for (int j = 0; j < arr.length; j++) {
                            GetId(arr[j], hash, result);
                        }
                        status = "false";
                        break;
                    }
                }
            } while (s != selector);
            if (status == "true") {
                if (selector.matches(",[w0],w|,[h1],h")) {
                    return;
                }

                selector = selector.replaceAll(",[01]", "");

                if (selector.length() != 0) {
                    selector = selector.substring(1);
                }

                result.add(selector);
            }
        }
    }

    public List Execute(String selector, int sex) {
            /* Console.WriteLine("in:"+selector); */

        ArrayList result = new ArrayList();

        if (selector == "") {
            result.add("");
            return result;
        }

        Map<String, Boolean> hash = new HashMap<String, Boolean>();

        if (sex < 0) {
            if (selector.indexOf(",w") == 0) {
                sex = 1;
            } else if (selector.indexOf(",h") == 0) {
                sex = 0;
            }
        }
        if (sex > -1) {
            //selector = ',' + sex.ToString() + selector;
        }
        if (selector.matches(",[w0],w|,[h1],h")) {
            result.add("false");
            return result; // 同志关系
        }

        GetId(selector, hash, result);


        LOG.info("Filter out start:");
        for (Object s : result) {
            if (s == "") {
                LOG.info("_");
            } else {
                LOG.info(s + " ");
            }
        }
        LOG.info("Filter end\n");

        return result;
    }
}