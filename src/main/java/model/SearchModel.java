package model;

import com.alibaba.fastjson.JSONObject;
import com.calculator.relationship.service.DataSource;

import java.util.Iterator;

class SearchModel
    {
        private DataSource src;
        private JSONObject obj;
        private String keyword;
        private String result;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public SearchModel()
        {
            this.src = new DataSource();
            try
            {
                this.obj = src.GetJson();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }

        public void GetChain()
        {
            Boolean found = false;
            String temp = keyword.replaceAll("^[一|二|三|四|五|六|七|八|九|十]+","x");
            String k1 = "|" + temp + ",";
            String k2 = "," + temp + ",";
            String k3 = "," + temp + "|";
            String k4 = "|" + temp + "|";
            /*Iterator iterator = obj.keySet();
            while(iterator.hasNext()){
                key = (String) iterator.next();
                value = jsonObject.getString(key);
            }
            {
                if(i.Value.ToString().Contains(k1) || i.Value.ToString().Contains(k2) || i.Value.ToString().Contains(k3) || i.Value.ToString().Contains(k4))
                {
                    result = Transfer(i.Key);
                    found = true;
                    break;
                }
            }*/
            if(found==false)
            {
                result = "尚未收录[" + keyword + "] ...";
            }
            
            return;
        }

        private String Transfer(String str)
        {
            String s = str;

            s = s.replaceAll( "f", "爸爸");
            s = s.replaceAll( "m", "妈妈");
            s = s.replaceAll( "h", "老公");
            s = s.replaceAll( "w", "老婆");
            s = s.replaceAll( "lb", "弟弟");
            s = s.replaceAll( "ls", "妹妹");
            s = s.replaceAll( "ob", "哥哥");
            s = s.replaceAll( "os", "姐姐");
            s = s.replaceAll( "xb", "兄弟");
            s = s.replaceAll( "xs", "姐妹");
            s = s.replaceAll( "s", "儿子");
            s = s.replaceAll( "d", "女儿");
            s = s.replaceAll( ",", "的");

            return s;
        }
    }