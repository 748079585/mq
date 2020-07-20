package com.mast.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushPayload {

    //推送类型
    private String type;
    //推送对象
    private String mobile;
    //标题
    private String title;
    //内容
    private String content;
    //数量
    private Integer badge = 1;
    //铃声
    private String sound = "default";

}
