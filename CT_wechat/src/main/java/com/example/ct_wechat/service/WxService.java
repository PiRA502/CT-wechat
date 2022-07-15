package com.example.ct_wechat.service;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WxService {

    //以Map形式读取公众号的所有消息
    public static Map<String, String> parseRequest(InputStream is) throws IOException {
        //将输入流解析成Map
        Map<String, String> map = new HashMap<>();
        try {
            //读取输入流获取文档对象
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);

            //根据文档对象获取根节点
            Element root = document.getRootElement();
            //获取所有的子节点
            List<Element> elements = root.elements();
            for (Element e : elements) {
                map.put(e.getName(), e.getStringValue());
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }
}