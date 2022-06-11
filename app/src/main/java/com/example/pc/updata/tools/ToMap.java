package com.example.pc.updata.tools;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by pc on 2018/3/30.
 */

public class ToMap {
    public static ArrayList ConvertObjToMap(Object obj){
        ArrayList<String> list=new ArrayList<String>();
        if (obj == null)
            return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for(int i=0;i<fields.length;i++){
                try {
                    Field f = obj.getClass().getDeclaredField(fields[i].getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    list.add(o.toString());
                } catch (NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }
    public  static   Field[]  getBean(){
        try {
            Class clazz = Class.forName("com.text.accounting.bean.Format");
            Field[] field0 = clazz.getDeclaredFields();
            return field0;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
