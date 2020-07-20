package com.activemq.demo.value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Search{
	private String fileName;
	private Long pid;
	// 0 女性，1 男性
	private int gender;
	// 0 白种人，1 亚洲人，2 黑种
	private int race;
	// 年龄（0-100）
	private int age;
}
