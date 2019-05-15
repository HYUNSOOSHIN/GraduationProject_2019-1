package kr.ac.hansung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.dao.ParticipationDao;
import kr.ac.hansung.model.Participation;

@Service
public class ParticipationService {

	@Autowired
	private ParticipationDao participationDao;
	
	public void addParticipation(Participation participation) {
		participationDao.addParticipation(participation);
	}
	
	public List<Participation> getParticipationlist() {
		return participationDao.getParticipationlist();
	}
	
}
