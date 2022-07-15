package com.example.ct_wechat.msg;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;

@XStreamAlias("Image")
@AllArgsConstructor
@Data
public class Image {
    @XStreamAlias("MediaId")
    private String mediaId;
}