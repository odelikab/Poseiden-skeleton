package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trade")
public class Trade {
	// TODO: Map columns in data table TRADE with corresponding java fields
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer tradeId;
	@NotBlank(message = "must not be empty")
	private String account;
	@NotBlank(message = "must not be empty")
	private String type;
	@Positive
	private Double buyQuantity;
	@Positive
	private Double sellQuantity;
	@Positive
	private Double buyPrice;
	@Positive
	private Double sellPrice;
	private String benchmark;
	private Timestamp tradeDate;
	private String security;
	private String status;
	private String trader;
	private String book;
	private String creationName;
	private Timestamp creationDate;
	private String revisionName;
	private Timestamp revisionDate;
	private String dealName;
	private String dealType;
	private String sourceListId;
	private String side;

	public Trade(@NotBlank(message = "must not be empty") String account,
			@NotBlank(message = "must not be empty") String type) {
		super();
		this.account = account;
		this.type = type;
	}
}
