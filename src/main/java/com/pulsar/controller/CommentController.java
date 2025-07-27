                              /* WILL WORK ON THIS FEATURE LATER*/





/* package com.pulsar.controller;
 * 
 * import java.security.Principal; import java.util.List;
 * 
 * import org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.security.core.annotation.AuthenticationPrincipal; import
 * org.springframework.web.bind.annotation.DeleteMapping; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.PutMapping; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.pulsar.dto.CommentResponse; import
 * com.pulsar.service.CommentService;
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @RestController
 * 
 * @RequestMapping("/api/comments")
 * 
 * @RequiredArgsConstructor public class CommentController {
 * 
 * private final CommentService commentService;
 * 
 * @GetMapping("/test") public ResponseEntity<String> test() { return
 * ResponseEntity.ok("Comments endpoint is working!"); }
 * 
 * @PostMapping public ResponseEntity<CommentResponse> commentOnPost(
 * 
 * @AuthenticationPrincipal Principal principal,
 * 
 * @RequestParam Long postId,
 * 
 * @RequestParam String content) { CommentResponse response =
 * commentService.addComment(principal.getName(), postId, content); return
 * ResponseEntity.status(HttpStatus.CREATED).body(response); }
 * 
 * @GetMapping("/post/{postId}") public ResponseEntity<List<CommentResponse>>
 * getComments(@PathVariable Long postId) { List<CommentResponse> comments =
 * commentService.getCommentsForPost(postId); return
 * ResponseEntity.ok(comments); }
 * 
 * @GetMapping("/{commentId}") public ResponseEntity<CommentResponse>
 * getComment(@PathVariable Long commentId) { CommentResponse comment =
 * commentService.getCommentById(commentId); return ResponseEntity.ok(comment);
 * }
 * 
 * @PutMapping("/{commentId}") public ResponseEntity<CommentResponse>
 * updateComment(
 * 
 * @PathVariable Long commentId,
 * 
 * @AuthenticationPrincipal Principal principal,
 * 
 * @RequestParam String content) { CommentResponse response =
 * commentService.updateComment(commentId, principal.getName(), content); return
 * ResponseEntity.ok(response); }
 * 
 * @DeleteMapping("/{commentId}") public ResponseEntity<Void> deleteComment(
 * 
 * @PathVariable Long commentId,
 * 
 * @AuthenticationPrincipal Principal principal) {
 * commentService.deleteComment(commentId, principal.getName()); return
 * ResponseEntity.noContent().build(); }
 * 
 * @GetMapping("/user/{username}") public ResponseEntity<List<CommentResponse>>
 * getCommentsByUser(@PathVariable String username) { List<CommentResponse>
 * comments = commentService.getCommentsByUser(username); return
 * ResponseEntity.ok(comments); } }
 */