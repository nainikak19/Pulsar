/*
 * package com.pulsar.model;
 * 
 * import java.time.LocalDateTime;
 * 
 * import org.hibernate.annotations.CreationTimestamp;
 * 
 * import jakarta.persistence.Column; import jakarta.persistence.Entity; import
 * jakarta.persistence.FetchType; import jakarta.persistence.GeneratedValue;
 * import jakarta.persistence.GenerationType; import jakarta.persistence.Id;
 * import jakarta.persistence.JoinColumn; import jakarta.persistence.ManyToOne;
 * import jakarta.persistence.Table; import
 * jakarta.validation.constraints.NotBlank; import
 * jakarta.validation.constraints.Size; import lombok.AllArgsConstructor; import
 * lombok.Builder; import lombok.Data; import lombok.NoArgsConstructor;
 * 
 * @Entity
 * 
 * @Table(name = "comments")
 * 
 * @Data
 * 
 * @Builder
 * 
 * @NoArgsConstructor
 * 
 * @AllArgsConstructor public class Comment {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
 * 
 * @ManyToOne(fetch = FetchType.LAZY)
 * 
 * @JoinColumn(name = "user_id", nullable = false) private User user;
 * 
 * @ManyToOne(fetch = FetchType.LAZY)
 * 
 * @JoinColumn(name = "post_id", nullable = false) private Post post;
 * 
 * @NotBlank(message = "Content cannot be empty")
 * 
 * @Size(max = 1000, message = "Content cannot exceed 1000 characters")
 * 
 * @Column(nullable = false, length = 1000) private String content;
 * 
 * @CreationTimestamp
 * 
 * @Column(name = "created_at", nullable = false, updatable = false) private
 * LocalDateTime createdAt; }
 */