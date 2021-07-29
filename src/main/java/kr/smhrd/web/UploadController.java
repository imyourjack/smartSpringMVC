package kr.smhrd.web;


import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.smhrd.domain.AttachFileVO;

@Controller // <-이걸 해야 컴포넌트 스캔이 돼서 UploadController가 메모리로 올라간다!
public class UploadController {
	
	@GetMapping("/uploadForm.do")
	public void uploadForm() {
		//void인 경우 uploadForm.jsp로 간다!
	}
	@PostMapping("/uploadFormAction.do")
	public void uploadFormAction(MultipartFile[] uploadFile, Model model) { //uploadFile 여기로 파일이 들어옴
		
		//AttachFileVO 생성 후 VO파일 정보(3개)를 담는 리스트 생성
		List<AttachFileVO> list = new ArrayList<AttachFileVO>();
		
		String uploadFolder="C:\\upload";
		String uploadFolderPath=getFolder(); //2021\07\27
		File uploadPath=new File(uploadFolder, uploadFolderPath);
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs(); //디렉토리 생성
		}
		
		
		for(MultipartFile multipartFile : uploadFile) { //MultipartFile에 파일 이름 / 파일 사이즈 저장
			System.out.print(multipartFile.getOriginalFilename()+":");
			System.out.println(multipartFile.getSize());
			
			//28행의 list 생성 이후
			AttachFileVO vo =new AttachFileVO();
			
			String uploadFileName = multipartFile.getOriginalFilename(); //업로드된 파일명의 중복제거를 위해 램덤수+파일명으로 저장하게 함
			vo.setFileName(uploadFileName); //0
			UUID uuid = UUID.randomUUID(); //random으로 수 제공
			uploadFileName = uuid.toString()+"_"+uploadFileName;
			
			File saveFile = new File(uploadPath, uploadFileName);
			try {
				multipartFile.transferTo(saveFile);
				vo.setUuid(uuid.toString()); //0
				vo.setUploadPath(uploadFolderPath); //0
				list.add(vo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//객체 바인딩
		model.addAttribute("list", list);
	}
	//년 월 일에 맞는 폴더 생성 함수
	private String getFolder() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		String str=sdf.format(date);
		return str.replace("-", "/");
	}
	@GetMapping(value="/download.do",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent")String userAgent , String fileName) {
		//System.out.println(fileName);
		Resource resource = new FileSystemResource("C:\\upload\\"+fileName);
		String resourceName=resource.getFilename();
		System.out.println(resourceName);
		
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);
		//다운로드 작업
		HttpHeaders headers = new HttpHeaders();
		try {
			String downloadName = null;
			if(userAgent.contains("Trident")) {
				System.out.println("InternetExplorer");
				downloadName=URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");
			}else if(userAgent.contains("Edge")) {
				System.out.println("Edge");
				downloadName=URLEncoder.encode(resourceOriginalName, "UTF-8");
			}else {
				System.out.println("Chrome");
				downloadName=new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			}
			
			headers.add("Content-Disposition", "attachment;filename=" + downloadName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
}
