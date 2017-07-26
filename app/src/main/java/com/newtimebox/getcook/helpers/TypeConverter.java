package com.newtimebox.getcook.helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 17.07.2017.
 */

public class TypeConverter {

    public static HashMap<String,String> StringToMapString(String[] ourString){
       //String ler {"key:value","key2:value"}
        Map<String,String> mapString = new HashMap<String,String>();
        for (String string : ourString) {
            String splitFirstValue = string.substring(0,string.indexOf(":"));
            String splitSeconSValue = string.substring(string.indexOf(":") + 1 ,string.length());

            mapString.put(splitFirstValue, splitSeconSValue);
        }

        return (HashMap<String, String>) mapString;

    }


}
