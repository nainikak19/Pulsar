/*
 * package com.pulsar.service;
 * 
 * import java.util.List; import java.util.stream.Collectors;
 * 
 * import org.springframework.stereotype.Service; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * import com.pulsar.dto.CommentResponse; import
 * com.pulsar.exception.CommentNotFoundException; import
 * com.pulsar.exception.PostNotFoundException; import
 * com.pulsar.exception.UnauthorizedException; import
 * com.pulsar.exception.UserNotFoundException; import com.pulsar.model.Comment;
 * import com.pulsar.model.Post; import com.pulsar.model.User; import
 * com.pulsar.repository.CommentRepository; import
 * com.pulsar.repository.PostRepository; import
 * com.pulsar.repository.UserRepository;
 * 
 * import lombok.RequiredArgsConstructor; import lombok.extern.slf4j.Slf4j;
 * 
 * @Service
 * 
 * @RequiredArgsConstructor
 * 
 * @Transactional
 * 
 * @Slf4j public class CommentService {
 * 
 * private final CommentRepository commentRepository; private final
 * PostRepository postRepository; private final UserRepository userRepository;
 * 
 * public CommentResponse addComment(String username, Long postId, String
 * content) { log.info("Adding comment for user: {} on post: {}", username,
 * postId);
 * 
 * // Validate content if (content == null || content.trim().isEmpty()) { throw
 * new IllegalArgumentException("Content cannot be empty"); }
 * 
 * if (content.length() > 1000) { throw new
 * IllegalArgumentException("Content cannot exceed 1000 characters"); }
 * 
 * User user = userRepository.findByUsername(username) .orElseThrow(() -> new
 * UserNotFoundException("User not found: " + username));
 * 
 * Post post = postRepository.findById(postId) .orElseThrow(() -> new
 * PostNotFoundException("Post not found with id: " + postId));
 * 
 * Comment comment = Comment.builder() .user(user) .post(post)
 * .content(content.trim()) .build();
 * 
 * Comment savedComment = commentRepository.save(comment);
 * log.info("Comment added successfully with id: {}", savedComment.getId());
 * 
 * return mapToResponse(savedComment); }
 * 
 * @Transactional(readOnly = true) public List<CommentResponse>
 * getCommentsForPost(Long postId) { log.info("Fetching comments for post: {}",
 * postId);
 * 
 * Post post = postRepository.findById(postId) .orElseThrow(() -> new
 * PostNotFoundException("Post not found with id: " + postId));
 * 
 * List<Comment> comments =
 * commentRepository.findByPostOrderByCreatedAtDesc(post);
 * 
 * return comments.stream() .map(this::mapToResponse)
 * .collect(Collectors.toList()); }
 * 
 * @Transactional(readOnly = true) public CommentResponse getCommentById(Long
 * commentId) { log.info("Fetching comment with id: {}", commentId);
 * 
 * Comment comment = commentRepository.findById(commentId) .orElseThrow(() ->
 * new CommentNotFoundException("Comment not found with id: " + commentId));
 * 
 * return mapToResponse(comment); }
 * 
 * public CommentResponse updateComment(Long commentId, String username, String
 * content) { log.info("Updating comment: {} by user: {}", commentId, username);
 * 
 * // Validate content if (content == null || content.trim().isEmpty()) { throw
 * new IllegalArgumentException("Content cannot be empty"); }
 * 
 * if (content.length() > 1000) { throw new
 * IllegalArgumentException("Content cannot exceed 1000 characters"); }
 * 
 * Comment comment = commentRepository.findById(commentId) .orElseThrow(() ->
 * new CommentNotFoundException("Comment not found with id: " + commentId));
 * 
 * // Check if the user is the owner of the comment if
 * (!comment.getUser().getUsername().equals(username)) { throw new
 * UnauthorizedException("You can only update your own comments"); }
 * 
 * comment.setContent(content.trim()); Comment updatedComment =
 * commentRepository.save(comment);
 * 
 * log.info("Comment updated successfully: {}", commentId); return
 * mapToResponse(updatedComment); }
 * 
 * public void deleteComment(Long commentId, String username) {
 * log.info("Deleting comment: {} by user: {}", commentId, username);
 * 
 * Comment comment = commentRepository.findById(commentId) .orElseThrow(() ->
 * new CommentNotFoundException("Comment not found with id: " + commentId));
 * 
 * // Check if the user is the owner of the comment if
 * (!comment.getUser().getUsername().equals(username)) { throw new
 * UnauthorizedException("You can only delete your own comments"); }
 * 
 * commentRepository.delete(comment);
 * log.info("Comment deleted successfully: {}", commentId); }
 * 
 * @Transactional(readOnly = true) public List<CommentResponse>
 * getCommentsByUser(String username) {
 * log.info("Fetching comments for user: {}", username);
 * 
 * User user = userRepository.findByUsername(username) .orElseThrow(() -> new
 * UserNotFoundException("User not found: " + username));
 * 
 * List<Comment> comments =
 * commentRepository.findByUserOrderByCreatedAtDesc(user);
 * 
 * return comments.stream() .map(this::mapToResponse)
 * .collect(Collectors.toList()); }
 * 
 * private CommentResponse mapToResponse(Comment comment) { return
 * CommentResponse.builder() .id(comment.getId()) .content(comment.getContent())
 * .username(comment.getUser().getUsername()) .postId(comment.getPost().getId())
 * .createdAt(comment.getCreatedAt()) .build(); } }
 */