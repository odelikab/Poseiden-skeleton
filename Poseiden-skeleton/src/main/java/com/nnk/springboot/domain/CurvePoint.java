package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
@Table(name = "curvepoint")
public class CurvePoint {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	@Positive(message = "must not be null")
	private Integer curveId;
	private Timestamp asOfDate;

	@Positive(message = "must not be null")
	private Double term;

	@Positive(message = "must not be null")
	private Double value;
	private Timestamp creationDate;

	public CurvePoint(@Positive(message = "must not be null") Integer curveId,
			@Positive(message = "must not be null") Double term, @Positive(message = "must not be null") Double value) {
		super();
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}

}
