package com.example.ct_wechat.msg;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.Map;

@Data
@XStreamAlias("xml")
public class TextMsg extends BaseMessage{
    @XStreamAlias("Content")
    private String content;

    public TextMsg(Map<String, String> requestMap, String content){
        //调用父类构造方法
        super(requestMap);
        //设置类型为文本类型
        this.setMsgType("text");
        this.content=content;
    }
}
