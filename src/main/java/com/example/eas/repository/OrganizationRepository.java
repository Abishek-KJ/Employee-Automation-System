package com.example.eas.repository; 

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eas.entity.OrganizationDetails;

public interface OrganizationRepository extends JpaRepository<OrganizationDetails, Long> {
	

}

