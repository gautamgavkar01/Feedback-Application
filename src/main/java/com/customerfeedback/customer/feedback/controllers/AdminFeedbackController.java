package com.customerfeedback.customer.feedback.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerfeedback.customer.feedback.dto.FeedbackRequest;
import com.customerfeedback.customer.feedback.dto.FeedbackResponse;
import com.customerfeedback.customer.feedback.service.FeedbackService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/feedback")
@RequiredArgsConstructor
public class AdminFeedbackController {
	
	private final FeedbackService feedbackService;
	
    @GetMapping("/all")
    public ResponseEntity<List<FeedbackResponse>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponse> getFeedbackById(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FeedbackResponse> updateFeedbackById(
            @PathVariable Long id,
            @RequestBody FeedbackRequest request
    ) {
        return ResponseEntity.ok(feedbackService.updateFeedbackById(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok("Feedback deleted successfully!");
    }

}
