package kr.smhrd.web;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller // <-이걸 해야 컴포넌트 스캔이 돼서 UploadController가 메모리로 올라간다!
public class UploadController {
	
	@GetMapping("/uploadForm.do")
	public void uploadForm() {
		//void인 경우 uploadForm.jsp로 간다!
	}
	@PostMapping("/uploadFormAction.do")
	public void uploadFormAction(MultipartFile[] uploadFile) { //uploadFile 여기로 파일이 들어옴
		
		String uploadFolder="C:\\upload";
		String uploadFolderPath=getFolder(); //2021\07\27
		File uploadPath=new File(uploadFolder, uploadFolderPath);
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs(); //디렉토리 생성
		}
		
		
		for(MultipartFile multipartFile : uploadFile) { //MultipartFile에 파일 이름 / 파일 사이즈 저장
			System.out.print(multipartFile.getOriginalFilename()+":");
			System.out.println(multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename(); //업로드된 파일명의 중복제거를 위해 램덤수+파일명으로 저장하게 함
			UUID uuid = UUID.randomUUID(); //random으로 수 제공
			uploadFileName = uuid.toString()+"_"+uploadFileName;
			
			File saveFile = new File(uploadPath, uploadFileName);
			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private String getFolder() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		String str=sdf.format(date);
		return str.replace("-", File.separator);
	}
	
}
