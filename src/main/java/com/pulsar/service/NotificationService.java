/*
 * package com.pulsar.service;
 * 
 * import java.util.List;
 * 
 * import org.springframework.stereotype.Service;
 * 
 * import com.pulsar.model.Notification; import
 * com.pulsar.repository.NotificationRepository; import
 * com.pulsar.repository.UserRepository;
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @Service
 * 
 * @RequiredArgsConstructor public class NotificationService {
 * 
 * private final NotificationRepository notificationRepository; private final
 * UserRepository userRepository;
 * 
 * public void createNotification(String username, String message) {
 * userRepository.findByUsername(username).ifPresent(user -> { Notification
 * notification = Notification.builder() .message(message) .user(user) .build();
 * notificationRepository.save(notification); }); }
 * 
 * public List<Notification> getNotifications(String username) { return
 * userRepository.findByUsername(username)
 * .map(notificationRepository::findByUserOrderByCreatedAtDesc) .orElseThrow(()
 * -> new RuntimeException("User not found")); } }
 */