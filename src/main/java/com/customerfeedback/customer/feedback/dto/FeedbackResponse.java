package com.customerfeedback.customer.feedback.dto;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {
	
	private Long id;
	
	private String message;
	
	private String date;
	
	private String username;
	

}
