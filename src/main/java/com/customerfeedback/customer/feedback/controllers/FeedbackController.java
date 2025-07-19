package com.customerfeedback.customer.feedback.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerfeedback.customer.feedback.dto.FeedbackRequest;
import com.customerfeedback.customer.feedback.dto.FeedbackResponse;
import com.customerfeedback.customer.feedback.service.FeedbackService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user/feedback")
@RequiredArgsConstructor
public class FeedbackController {
	
	private final FeedbackService feedbackService;
	
    @PostMapping("/add")
    public ResponseEntity<FeedbackResponse> addFeedback(@RequestBody FeedbackRequest request, Authentication auth) {
        String username = auth.getName(); 
        FeedbackResponse response = feedbackService.addFeedback(request, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-feedback")
    public ResponseEntity<FeedbackResponse> getOwnFeedback(Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(feedbackService.getFeedbackByUsername(username));
    }

    @PutMapping("/update")
    public ResponseEntity<FeedbackResponse> updateFeedback(@RequestBody FeedbackRequest request, Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(feedbackService.updateFeedback(request, username));
    }

}

