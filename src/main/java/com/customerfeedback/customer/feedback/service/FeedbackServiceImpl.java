package com.customerfeedback.customer.feedback.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.customerfeedback.customer.feedback.dto.FeedbackRequest;
import com.customerfeedback.customer.feedback.dto.FeedbackResponse;
import com.customerfeedback.customer.feedback.entity.FeedbackEntity;
import com.customerfeedback.customer.feedback.entity.UserEntity;
import com.customerfeedback.customer.feedback.exceptions.ResourceNotFoundException;
import com.customerfeedback.customer.feedback.repository.FeedbackRepository;
import com.customerfeedback.customer.feedback.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Override
    public FeedbackResponse addFeedback(FeedbackRequest request, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (feedbackRepository.existsByUser(user)) {
            throw new RuntimeException("Feedback already submitted. Use update instead.");
        }

        FeedbackEntity feedback = FeedbackEntity.builder()
                .user(user)
                .message(request.getMessage())
                .date(LocalDate.now())
                .build();

        feedback = feedbackRepository.save(feedback);
        return mapToResponse(feedback);
    }

    @Override
    public FeedbackResponse getFeedbackByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        FeedbackEntity feedback = feedbackRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));

        return mapToResponse(feedback);
    }

    @Override
    public FeedbackResponse updateFeedback(FeedbackRequest request, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        FeedbackEntity feedback = feedbackRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));

        feedback.setMessage(request.getMessage());
        feedback.setDate(LocalDate.now());

        feedback = feedbackRepository.save(feedback);
        return mapToResponse(feedback);
    }

//Now this is for admin
    
    
    @Override
    public List<FeedbackResponse> getAllFeedbacks() {
        return feedbackRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackResponse getFeedbackById(Long id) {
        FeedbackEntity feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with ID: " + id));
        return mapToResponse(feedback);
    }

    @Override
    public FeedbackResponse updateFeedbackById(Long id, FeedbackRequest request) {
        FeedbackEntity feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with ID: " + id));

        feedback.setMessage(request.getMessage());
        feedback.setDate(LocalDate.now());

        feedback = feedbackRepository.save(feedback);
        return mapToResponse(feedback);
    }

    @Override
    public void deleteFeedback(Long id) {
        if (!feedbackRepository.existsById(id)) {
            throw new ResourceNotFoundException("Feedback not found with ID: " + id);
        }
        feedbackRepository.deleteById(id);
    }

    private FeedbackResponse mapToResponse(FeedbackEntity feedback) {
        String username = (feedback.getUser() != null) ? feedback.getUser().getUsername() : "Unknown";
        String date = feedback.getDate() != null ? feedback.getDate().toString() : "";

        return new FeedbackResponse(
            feedback.getId(),
            feedback.getMessage(),
            date,
            username
        );
    }


}
