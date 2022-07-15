package com.example.ct_wechat.msg;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

/*
 * 所有消息的父类（根据不同的消息种类写延伸类）
 * */
@Data
@NoArgsConstructor
@XStreamAlias("xml")
public class BaseMessage {
    @XStreamAlias("ToUserName")
    private String toUserName;

    @XStreamAlias("FromUserName")
    private String fromUserName;

    @XStreamAlias("CreateTime")
    private String createTime;

    @XStreamAlias("MsgType")
    private String msgType;

    public BaseMessage(Map<String,String> requestMap){
        this.toUserName= (String) requestMap.get("FromUserName");
        this.fromUserName= (String) requestMap.get("ToUserName");
        this.createTime=System.currentTimeMillis()/1000+"";
    }
}
