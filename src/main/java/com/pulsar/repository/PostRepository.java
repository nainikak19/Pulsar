package com.pulsar.repository;

import com.pulsar.model.Post;
import com.pulsar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByUserOrderByCreatedAtDesc(User user);

	List<Post> findAllByUserInOrderByCreatedAtDesc(List<User> users);

	List<Post> findByContentContainingIgnoreCaseOrderByCreatedAtDesc(String keyword);

	List<Post> findByUser(User user);

	List<Post> findByUserInOrderByCreatedAtDesc(List<User> users);
}
