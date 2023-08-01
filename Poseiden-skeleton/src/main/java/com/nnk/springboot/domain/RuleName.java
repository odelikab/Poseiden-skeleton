package com.nnk.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rulename")
public class RuleName {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank(message = "must not be empty")
	private String name;
	@NotBlank(message = "must not be empty")
	private String description;
	@NotBlank(message = "must not be empty")
	private String json;
	@NotBlank(message = "must not be empty")
	private String template;
	@NotBlank(message = "must not be empty")
	private String sqlStr;
	@NotBlank(message = "must not be empty")
	private String sqlPart;

	public RuleName(@NotBlank(message = "must not be empty") String name,
			@NotBlank(message = "must not be empty") String description,
			@NotBlank(message = "must not be empty") String json,
			@NotBlank(message = "must not be empty") String template,
			@NotBlank(message = "must not be empty") String sqlStr,
			@NotBlank(message = "must not be empty") String sqlPart) {
		super();
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlStr = sqlStr;
		this.sqlPart = sqlPart;
	}
}
