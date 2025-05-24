package com.example.eas.service; 

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.eas.entity.ManagerSignup;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service 
public class ManageManagerService { 
	
	// private final ManagerSignup managerSignup; 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	/* public ManageManagerService(ManagerSignup managerSignup) { 
		this.managerSignup = managerSignup; 
	} */ 
	
	public List<ManagerSignup> getAllManagers() { 
		
		String getManagersJpql = "SELECT m FROM ManagerSignup m"; 
		TypedQuery<ManagerSignup> executeQuery = entityManager.createQuery(getManagersJpql, ManagerSignup.class);  
		
		return executeQuery.getResultList();  
	} 
	
	public void updateManagerInformation(String email, BigDecimal ctc, int previousExperience) { 
		
		ManagerSignup manager = entityManager.createQuery("SELECT m FROM ManagerSignup m WHERE m.email =:email", ManagerSignup.class)
				.setParameter("email", email)
				.getSingleResult(); 
		
		manager.setManagerCTC(ctc); 
		manager.setManagerPreviousExperience(previousExperience); 
		entityManager.merge(manager); 
	}  
	
	public ManagerSignup getManagerByEmail(String email) { 
		return entityManager.createQuery("SELECT m FROM ManagerSignup m WHERE m.managerMailId = :email", ManagerSignup.class)
				.setParameter("email", email) 
				.getSingleResult(); 
	} 
	
	@Transactional 
	public void updateCtcAndExperienceByEmail(String email, long ctc, double experience) { 
		entityManager.createQuery("UPDATE ManagerSignup m SET m.managerCTC = :managerCTC, m.managerPreviousExperience = :managerPreviousExperience WHERE m.managerMailId = :managerMailId")
		.setParameter("managerCTC", ctc)
		.setParameter("managerPreviousExperience", experience)
		.setParameter("managerMailId", email) 
		.executeUpdate(); 
	} 
	
	public String extractDesignationFromEmail(String email) {

		if (email == null || !email.contains("@")) {
			return "unknown";
		}

		String localPart = email.substring(0, email.indexOf('@'));

		String[] parts = localPart.split("\\.");

		if (parts.length > 0) {
			return parts[parts.length - 1].substring(0, 1).toUpperCase() + parts[parts.length - 1].substring(1);
		}

		return "Unknown";
	}
	
	@Transactional
    public void deleteByManagerMailId(String email) {
        List<ManagerSignup> managers = entityManager
            .createQuery("SELECT m FROM ManagerSignup m WHERE m.managerMailId = :email", ManagerSignup.class)
            .setParameter("email", email)
            .getResultList();

        for (ManagerSignup m : managers) {
            entityManager.remove(m);
        }
    }
} 


