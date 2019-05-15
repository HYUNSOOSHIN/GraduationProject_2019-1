package kr.ac.hansung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ac.hansung.model.Participation;
import kr.ac.hansung.service.ParticipationService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private ParticipationService participationService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String createParticipation(Participation participation) { //업로드 함수
		
		participationService.addParticipation(participation);
		
		return "home";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteParticipation(Participation participation) { //삭제 함수
		
		return "home";
	}
	
	@RequestMapping(value = "/get_list", method = RequestMethod.GET)
	public @ResponseBody List<Participation> getParticipation() { //리스트 받기 함수
		
		List<Participation> participation = participationService.getParticipationlist();
		
		return participation;
	}
	
}
