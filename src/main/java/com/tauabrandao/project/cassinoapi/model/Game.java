package com.tauabrandao.project.cassinoapi.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "game")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

	@Id
	private Long id;
	
	@ManyToOne
	private Player player;
	
	private boolean active;
	
	@ManyToOne
	private Cassino cassino;
	
	private LocalDate startedAt;
	
	private LocalDate finalizedAt;
	
	
}
