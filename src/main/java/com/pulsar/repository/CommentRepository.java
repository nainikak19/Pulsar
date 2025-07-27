/*
 * package com.pulsar.repository;
 * 
 * import java.util.List;
 * 
 * import org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.data.jpa.repository.Query; import
 * org.springframework.data.repository.query.Param; import
 * org.springframework.stereotype.Repository;
 * 
 * import com.pulsar.model.Comment; import com.pulsar.model.Post; import
 * com.pulsar.model.User;
 * 
 * @Repository public interface CommentRepository extends JpaRepository<Comment,
 * Long> {
 * 
 * List<Comment> findByPostOrderByCreatedAtDesc(Post post);
 * 
 * List<Comment> findByUserOrderByCreatedAtDesc(User user);
 * 
 * @Query("SELECT c FROM Comment c WHERE c.post.id = :postId ORDER BY c.createdAt DESC"
 * ) List<Comment> findByPostIdOrderByCreatedAtDesc(@Param("postId") Long
 * postId);
 * 
 * @Query("SELECT c FROM Comment c WHERE c.user.username = :username ORDER BY c.createdAt DESC"
 * ) List<Comment> findByUsernameOrderByCreatedAtDesc(@Param("username") String
 * username);
 * 
 * @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId") Long
 * countByPostId(@Param("postId") Long postId);
 * 
 * @Query("SELECT COUNT(c) FROM Comment c WHERE c.user.id = :userId") Long
 * countByUserId(@Param("userId") Long userId);
 * 
 * void deleteByPostId(Long postId);
 * 
 * void deleteByUserId(Long userId); }
 */