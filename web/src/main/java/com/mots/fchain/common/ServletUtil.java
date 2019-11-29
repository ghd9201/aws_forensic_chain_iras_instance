package com.mots.fchain.common;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;


public class ServletUtil {
    /**
     * 사용하지 않음!!
     */
    public static String getPayload(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

    /**
     *
     * HttpServletRequest 객체를 받아 getParameterNames 깂을 HashMap 타입으로 리턴
     *
     * @param request
     * @return
     */
    public static HashMap<String, String> paramToHashMap(HttpServletRequest request)
    {
        HashMap<String, String> param = new HashMap<String, String>();

        Enumeration penum = request.getParameterNames();

        String key	= null;
        String value= null;

        System.out.println("================ paramToHashMap [Start] ================");

        while(penum.hasMoreElements())
        {
            key = (String)penum.nextElement();

//	        if( key.equals("dataList") ) {
//	        	continue;
//	        }
            value = (new String(request.getParameter(key)) == null) ? "" : new String(request.getParameter(key).trim().replaceAll("<", "&lt;"));

            param.put(key, value);
        }

        System.out.println("================ paramToHashMap [End] ================");

        return param;
    }

    public static HashMap<String, String> paramToHashMapUtf8(HttpServletRequest request)
    {
        HashMap<String, String> param = new HashMap<String, String>();

        Enumeration penum = request.getParameterNames();

        String key	= null;
        String value= null;

        System.out.println("================ paramToHashMap [Start] ================");

        while(penum.hasMoreElements())
        {
            key = (String)penum.nextElement();

//	        if( key.equals("dataList") ) {
//	        	continue;
//	        }
            value = (new String(request.getParameter(key)) == null) ? "" : new String(request.getParameter(key).trim().replaceAll("<", "&lt;"));

            // Decode 시 깨지는 문자 처리
            value = replacer(value);

            System.out.println("2[" + key + "] : [" + value + "]");

            param.put(key, value);
        }

        System.out.println("================ paramToHashMap [End] ================");

        return param;
    }

    // from : https://stackoverflow.com/questions/6067673/urldecoder-illegal-hex-characters-in-escape-pattern-for-input-string
    public static String replacer(String data) {
        try {
            data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            data = data.replaceAll("\\+", "%2B");
            data = URLDecoder.decode(data, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}