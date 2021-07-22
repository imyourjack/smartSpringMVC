package kr.smhrd.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.smhrd.domain.BoardVO;
import kr.smhrd.mapper.BoardMapper;

//POJO
@Controller
public class BoardController{
	
	//@Autowired
	@Inject
	//@Resource("boardMapper")
	private BoardMapper boardMapper;
	// 게시판 리스트를 가져오는 동작
	// HandlerMapping
	@RequestMapping("/boardList.do")
	public String boardList(Model model) {
		List<BoardVO> list= boardMapper.boardList();
		model.addAttribute("list", list); //객체바인딩 -> Model
		return "boardList"; // --> ViewResolver qq-->  /WEB-INK/views/boardList.jsp 에 만들기
	}
	@RequestMapping("/boardForm.do")
	public String boardForm() {
		return "boardForm";
	}
	@RequestMapping("/boardInsert.do")
	public String boardInsert(BoardVO vo) {
		boardMapper.boardInsert(vo);
		return "redirect:/boardList.do";
	}
	@RequestMapping("/boardContent.do")
	public String boardContent(int idx, Model model) {
		BoardVO vo = boardMapper.boardContent(idx);
		model.addAttribute("vo", vo);
		return "boardContent";
	}
	@RequestMapping("/boardDelete.do")
	public String boardDelete(int idx) {
		boardMapper.boardDelete(idx);
		return "redirect:/boardList.do";
	}
	
}
