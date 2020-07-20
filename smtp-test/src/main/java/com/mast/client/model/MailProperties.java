package com.mast.client.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailProperties {
 
    private String from;
 
    private String to;
 
}
