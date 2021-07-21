package kr.smhrd.mapper;

import java.util.List;

import kr.smhrd.domain.BoardVO;

public interface BoardMapper {
	// SQL --> X / Mapper File(XML) --> O
	public List<BoardVO> boardList();
	public void boardInsert(BoardVO vo); // insert SQL
}
