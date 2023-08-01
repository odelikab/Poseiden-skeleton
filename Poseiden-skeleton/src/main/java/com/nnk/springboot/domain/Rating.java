package com.nnk.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rating")
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank(message = "field is mandatory")
	private String moodysRating;
	@NotBlank(message = "field is mandatory")
	private String sandPRating;
	@NotBlank(message = "field is mandatory")
	private String fitchRating;
	@Positive
	private Integer orderNumber;

	public Rating(@NotBlank(message = "field is mandatory") String moodysRating,
			@NotBlank(message = "field is mandatory") String sandPRating,
			@NotBlank(message = "field is mandatory") String fitchRating, @Positive Integer orderNumber) {
		super();
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}
}
