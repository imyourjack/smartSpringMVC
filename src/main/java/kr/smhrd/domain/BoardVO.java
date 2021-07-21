package kr.smhrd.domain;

import lombok.Data;
@Data
public class BoardVO {
	private int idx;
	private String title;
	private String contents;
	private int count;
	private String writer;
	private String indate;
	//getter, setter, toString() Spring에선 만들필요없음
}
