package com.customerfeedback.customer.feedback.service;

import java.util.List;

import com.customerfeedback.customer.feedback.dto.FeedbackRequest;
import com.customerfeedback.customer.feedback.dto.FeedbackResponse;

public interface FeedbackService {

	FeedbackResponse addFeedback(FeedbackRequest request, String username);

	FeedbackResponse getFeedbackByUsername(String username);

	FeedbackResponse updateFeedback(FeedbackRequest request, String username);

	// For admin
	List<FeedbackResponse> getAllFeedbacks();
	FeedbackResponse getFeedbackById(Long id);
	FeedbackResponse updateFeedbackById(Long id, FeedbackRequest request);
	void deleteFeedback(Long id);

}