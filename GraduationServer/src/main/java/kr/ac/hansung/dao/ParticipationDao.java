package kr.ac.hansung.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.model.Participation;

@Repository
@Transactional
public class ParticipationDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addParticipation(Participation participation) {
		
		Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(participation);
        session.flush();
        
	}
	
	@SuppressWarnings("unchecked")
	public List<Participation> getParticipationlist() {
		
		Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Participation");
        List<Participation> participationList =  query.list();

        return participationList;
        
	}
	
}
