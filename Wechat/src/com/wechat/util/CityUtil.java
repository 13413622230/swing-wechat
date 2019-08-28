package com.wechat.util;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class CityUtil {
	public static String[] getCity() throws DocumentException{
		SAXReader reader = new SAXReader();  
        // ͨ��read������ȡһ���ļ� ת����Document����  
        Document document = reader.read(new File("xml/city.xml"));  
        //��ȡ���ڵ�Ԫ�ض���  
        Element node = document.getRootElement();
        List<Element> carnames = node.elements("province");
        String[] s = new String[carnames.size()];
        int i=0;
        for(Element ee:carnames){
//        	System.out.println(ee.attributeValue("name"));
        	s[i] = ee.attributeValue("name");
        	i++;
        }
        return s;
	}
	public static String[] getItme(String name) throws DocumentException{
		SAXReader reader = new SAXReader();  
        // ͨ��read������ȡһ���ļ� ת����Document����  
        Document document = reader.read(new File("xml/city.xml"));  
        //��ȡ���ڵ�Ԫ�ض���  
        Element node = document.getRootElement();
        List<Element> carnames = node.elements("province");
        List<Element> items = null;
        String[] s = null;
        int i=0;
        for(Element ee:carnames){
        	if(ee.attributeValue("name").equals(name)){
        		items = ee.elements("item");
        		break;
        	}
        }
        if(items==null){
        	return null;
        }
        else{
        	s = new String[items.size()];
        }
        for(Element ee:items){
        	s[i] = ee.getText();
        	i++;
        }
        return s;
	}
}
