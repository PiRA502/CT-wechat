package com.example.ct_wechat.controller;

import com.example.ct_wechat.service.WxService;
import com.example.ct_wechat.utils.Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@RestController
public class WxController {

    @RequestMapping(value="/testWeixin",method = {RequestMethod.POST,RequestMethod.GET})
    public void testWeixin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        if(Utils.check(timestamp,nonce,signature)){
            //校验正确并返回echostr，才能正式成为一名公众号开发者，即填写的URL和TOKEN才能生效
            //out.print(echostr);

            //查看用户从公众号发送过来的信息
            Map<String, String> requestMap=WxService.parseRequest(request.getInputStream());
            System.out.println(requestMap);

            //返回 收到了！！！给用户
            String respXml="<xml>\n" +
                    "<ToUserName><![CDATA["+requestMap.get("FromUserName")+"]]></ToUserName>\n" +
                    "<FromUserName><![CDATA["+requestMap.get("ToUserName")+"]]></FromUserName>\n" +
                    "<CreateTime>12345678</CreateTime>\n" +
                    "<MsgType><![CDATA[text]]></MsgType>\n" +
                    "<Content><![CDATA["+"收到了！！！"+"]]></Content>\n" +
                    "</xml>\n";

            out.print(respXml);
        }else{
            //校验失败
            out.print("---请到公众号执行相应操作---");
        }
        out.flush();
        out.close();
    }
}