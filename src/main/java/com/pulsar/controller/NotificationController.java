                                /* WILL  ADD THIS FEATURE LATER */




/*
 * package com.pulsar.controller;
 * 
 * import java.util.List;
 * 
 * import org.springframework.http.ResponseEntity; import
 * org.springframework.security.core.annotation.AuthenticationPrincipal; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.pulsar.model.Notification; import
 * com.pulsar.service.NotificationService;
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @RestController
 * 
 * @RequestMapping("/api/notifications")
 * 
 * @RequiredArgsConstructor public class NotificationController {
 * 
 * private final NotificationService notificationService;
 * 
 * @GetMapping public ResponseEntity<List<Notification>> getUserNotifications(
 * 
 * @AuthenticationPrincipal UserDetails userDetails) { String username =
 * userDetails.getUsername(); return
 * ResponseEntity.ok(notificationService.getNotifications(username)); } }
 */