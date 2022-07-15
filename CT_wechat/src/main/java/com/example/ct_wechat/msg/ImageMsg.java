package com.example.ct_wechat.msg;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;

public class ImageMsg extends BaseMessage{
    @XStreamAlias("Image")
    private Image image;

    public ImageMsg(Map<String,String> requestMap, Image image) {
        super(requestMap);
        this.setMsgType("image");
        this.image = image;
    }
}
