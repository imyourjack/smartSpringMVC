package kr.smhrd.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public void boardList(Model model) {
		List<BoardVO> list= boardMapper.boardList();
		model.addAttribute("list", list); //객체바인딩 -> Model
		//return "boardList"; // --> ViewResolver qq-->  /WEB-INK/views/boardList.jsp 에 만들기
	}
	@RequestMapping("/boardListAjax.do")
	public @ResponseBody List<BoardVO> boardListAjax() {
		List<BoardVO> list = boardMapper.boardListAjax(); //게시판 전체리스트 가져오기
		return list; // 객체를 리턴 --->{JSON API}--->String 변환 -->응답
	}
	@RequestMapping("/boardForm.do")
	public void boardForm() { //요청 이름과 jsp 이름이 같을 경우 void로 가능
		//return "boardForm"; //jsp로 갈 때는 그냥 boardForm
	}
	@RequestMapping("/boardInsert.do")
	public String boardInsert(BoardVO vo) {
		boardMapper.boardInsert(vo);
		return "redirect:/boardList.do"; //.do로 보낼 때는 redirect:/ 쓰기
	}
	@RequestMapping("/boardContent.do")
	public void boardContent(int idx, Model model) {
		BoardVO vo = boardMapper.boardContent(idx);
		model.addAttribute("vo", vo);
		//return "boardContent";
	}
	@RequestMapping("/boardDelete.do")
	public String boardDelete(int idx) {
		boardMapper.boardDelete(idx);
		return "redirect:/boardList.do";
	}
	//GET, POST
	@RequestMapping(value="/boardUpdate.do", method=RequestMethod.POST)
	public String boardUpdate(BoardVO vo) {
		boardMapper.boardUpdate(vo);
		return "redirect:/boardList.do";
	}
}
