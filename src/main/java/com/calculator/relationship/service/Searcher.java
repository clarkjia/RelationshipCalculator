package com.calculator.relationship.service;

import com.alibaba.fastjson.JSONObject;

public class Searcher
    {
        private JSONObject obj;

        public Searcher(JSONObject obj)
        {
            this.obj = obj;
        }

        public String Who(String my)
        {
            String ret = "";

            if(obj.containsKey(my))
            {
                String one = obj.getString(my).toString().split(",")[0];
                one = one.replaceAll("\\|", "");
                ret = one;
            }
            else
            {
                String key = "l|o";
                my = my.replaceAll(key,"x");

                if (obj.containsKey(my))
                {
                    String one = obj.getString(my).toString().split(",")[0];
                    one = one.replaceAll("\\|", "");
                    ret = one;
                }
                else
                {
                    ret = "你们好像不是很熟哦~~ ";
                }
            }

            return ret;
        }
    }