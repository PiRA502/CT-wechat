package com.example.ct_wechat.service;

import com.example.ct_wechat.msg.BaseMessage;
import com.example.ct_wechat.msg.ImageMsg;
import com.example.ct_wechat.msg.TextMsg;
import com.thoughtworks.xstream.XStream;
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

    /*
     *封装公众号返回信息，用于处理所有的事件和消息的回复，返回的是xml数据包
     * */
    public static String getResponse(Map<String, String> requestMap) {
        BaseMessage msg = null;
        //获取用户发送消息的类型
        String msgType = (String) requestMap.get("MsgType");
        switch (msgType) {
            case "text":
                //处理文本类型
                msg = dealTextMessage(requestMap);
                //将图文信息转成xml数据包
                //msg=dealNewsMessage(requestMap);
                //System.out.println("---返回文字类型数据---");
                break;
            case "image":
                //处理图片信息
                msg = dealImageMessage(requestMap);
                break;
            case "event":
                //处理各类事件
                msg = dealEvent(requestMap);
                break;
            default:
                break;
        }
        if (msg != null) {
            //将信息转成xml数据包
            return beanToXml(msg);
        }
        return null;
    }

    /*
     * 把消息对象转成xml数据
     * */
    private static String beanToXml(BaseMessage msg) {
        XStream stream = new XStream();
        /*
         * 使对应类上的注解生效
         * */
        stream.processAnnotations(BaseMessage.class);
        stream.processAnnotations(TextMsg.class);
        stream.processAnnotations(ImageMsg.class);
        String xml = stream.toXML(msg);
        return xml;
    }

    /*
     * 专门处理文本消息
     * */
    private static BaseMessage dealTextMessage(Map<String, String> requestMap) {
        TextMsg textMessage=new TextMsg(requestMap,"Got your text.");
        return textMessage;
    }

    /*
     *
     *专门处理回复图片消息 requestMap是从用户从公众号发过来的数据
     * */
    private static BaseMessage dealImageMessage(Map<String, String> requestMap) {
        TextMsg textMessage=new TextMsg(requestMap,"Got the picture.");
        return textMessage;
    }

    private static BaseMessage dealEvent(Map<String, String> requestMap) {
        //判断什么事件
        String event = requestMap.get("Event");
        switch (event) {
            case "CLICK":
                //详细处理点击事件
                return dealClick(requestMap);
            default:
                //其它事件
                break;
        }
        return null;
    }

    /**
     * 专门处理点击事件
     * @param requestMap
     * @return
     */
    private static BaseMessage dealClick(Map<String, String> requestMap) {
        //获取创建菜单时填的key值
        String key = requestMap.get("EventKey");
        switch (key){
            case "1":
                //点击一级菜单
                return new TextMsg(requestMap,"点击了一级菜单");
            case "21":
                //点击二级菜单的1
                return new TextMsg(requestMap,"点击二级菜单的1");
            case "22":
                //点击二级菜单的2
                return new TextMsg(requestMap,"点击二级菜单的2");
            default:
                break;
        }
        return null;
    }


}