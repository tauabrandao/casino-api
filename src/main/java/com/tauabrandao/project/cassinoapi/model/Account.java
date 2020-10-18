package com.tauabrandao.project.cassinoapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	private Long id;
	private boolean active;
	private Double balance;
	private String currency;
	@OneToOne
	private Player player;
	
}
