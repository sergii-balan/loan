package com.balan.sergii.loan.dao.listener;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.hibernate.Session;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.Query;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.balan.sergii.loan.dao.Loan;

@SuppressWarnings("serial")
@Component
public class LoanPreInsertListener implements PreInsertEventListener{

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		return checkIp(event);
	}
	
    @PostConstruct
    protected void init() {
        SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(this);
    }
	
	private boolean checkIp(PreInsertEvent event) {
		if (event.getEntity() instanceof Loan) {
			System.out.println(">>> - event.");
			Loan loan = (Loan) event.getEntity();
			
			
			Session session = event.getSession().getSessionFactory().openTemporarySession();			
			Query<?> query = session.createQuery(
					"SELECT count(*) FROM Loan "
					+ "WHERE user_id = :userId "
					+ "AND start_date >= :startDate "
					+ "AND ip = :ip ");
			
			query.setParameter("userId", loan.getUserId());
			query.setParameter("startDate", new DateTime().minusDays(1).toDate());
			query.setParameter("ip", loan.getIp());
			Object result = query.uniqueResult();
			session.close(); 
			
			if (result == null) {
				return false;
			}
			
			Long count = (Long) result;
			
			return count >= 3;
		}
		
		return false;
	}
}
