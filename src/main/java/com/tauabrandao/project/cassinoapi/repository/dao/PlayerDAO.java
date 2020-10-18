package com.tauabrandao.project.cassinoapi.repository.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.tauabrandao.project.cassinoapi.dto.FullPlayerDataDTO;

@Repository
public class PlayerDAO {

	@PersistenceContext
	private EntityManager em;
	
	public FullPlayerDataDTO getFullPlayerDataByPlayerID(Long id) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select new com.tauabrandao.project.cassinoapi.dto.FullPlayerDataDTO( p.name, p.dateOfBirth, p.email,"
				+ " p.gender, p.id, a.balance, a.currency) from Account a join a.player p where p.id = :pId");
		
		FullPlayerDataDTO result = (FullPlayerDataDTO) em.createQuery(sb.toString()).setParameter("pId", id).getSingleResult();

		return result;
		
	}
	
}
