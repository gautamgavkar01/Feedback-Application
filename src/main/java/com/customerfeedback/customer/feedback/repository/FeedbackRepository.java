package com.customerfeedback.customer.feedback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customerfeedback.customer.feedback.entity.FeedbackEntity;
import com.customerfeedback.customer.feedback.entity.UserEntity;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {

	Optional<FeedbackEntity> findByUser(UserEntity user);
    boolean existsByUser(UserEntity userEntity);
}