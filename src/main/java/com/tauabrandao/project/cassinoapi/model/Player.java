package com.tauabrandao.project.cassinoapi.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.tauabrandao.project.cassinoapi.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String name;

	@NotNull
	private String email;

	@NotNull
	@JsonIgnore
	private String password;

	private LocalDate dateOfBirth;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

}
