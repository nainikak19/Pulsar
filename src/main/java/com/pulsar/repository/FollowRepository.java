package com.pulsar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pulsar.model.Follow;
import com.pulsar.model.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // Basic CRUD operations
    List<Follow> findByFollower(User follower);
    List<Follow> findByFollowing(User following);
    
    // Check if follow relationship exists
    boolean existsByFollowerAndFollowing(User follower, User following);
    
    // Find specific follow relationship
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    
    // Delete operations - IMPORTANT: Add @Modifying and @Transactional
    @Modifying
    @Transactional
    void deleteByFollowerAndFollowing(User follower, User following);
    
    // Count operations for performance
    long countByFollowing(User following); // Count followers
    long countByFollower(User follower);   // Count following
    
    // Optimized queries with JOIN FETCH to avoid N+1 problem
    @Query("SELECT f FROM Follow f JOIN FETCH f.follower WHERE f.following = :user")
    List<Follow> findByFollowingWithFollower(@Param("user") User user);

    @Query("SELECT f FROM Follow f JOIN FETCH f.following WHERE f.follower = :user")
    List<Follow> findByFollowerWithFollowing(@Param("user") User user);
    
    // Get followers as User objects directly
    @Query("SELECT f.follower FROM Follow f WHERE f.following = :user")
    List<User> findFollowersByFollowing(@Param("user") User user);
    
    // Get following as User objects directly
    @Query("SELECT f.following FROM Follow f WHERE f.follower = :user")
    List<User> findFollowingByFollower(@Param("user") User user);
    
    // Check mutual following (both users follow each other)
    @Query("SELECT CASE WHEN COUNT(f1) > 0 AND COUNT(f2) > 0 THEN true ELSE false END " +
           "FROM Follow f1, Follow f2 " +
           "WHERE f1.follower = :user1 AND f1.following = :user2 " +
           "AND f2.follower = :user2 AND f2.following = :user1")
    boolean areMutualFollowers(@Param("user1") User user1, @Param("user2") User user2);
    
    // Get mutual followers (users who follow each other)
    @Query("SELECT f1.following FROM Follow f1 " +
           "WHERE f1.follower = :user " +
           "AND EXISTS (SELECT f2 FROM Follow f2 WHERE f2.follower = f1.following AND f2.following = :user)")
    List<User> findMutualFollowers(@Param("user") User user);
    
    // Find users who follow both users (common followers)
    @Query("SELECT f1.follower FROM Follow f1 " +
           "WHERE f1.following = :user1 " +
           "AND EXISTS (SELECT f2 FROM Follow f2 WHERE f2.follower = f1.follower AND f2.following = :user2)")
    List<User> findCommonFollowers(@Param("user1") User user1, @Param("user2") User user2);
    
    // Find users both users follow (common following)
    @Query("SELECT f1.following FROM Follow f1 " +
           "WHERE f1.follower = :user1 " +
           "AND EXISTS (SELECT f2 FROM Follow f2 WHERE f2.follower = :user2 AND f2.following = f1.following)")
    List<User> findCommonFollowing(@Param("user1") User user1, @Param("user2") User user2);
    
    // Get recent followers (ordered by ID - you can change this if you have createdAt field)
    @Query("SELECT f FROM Follow f WHERE f.following = :user ORDER BY f.id DESC")
    List<Follow> findRecentFollowersByFollowing(@Param("user") User user);
    
    // Get followers count efficiently
    @Query("SELECT COUNT(f) FROM Follow f WHERE f.following = :user")
    long countFollowers(@Param("user") User user);
    
    // Get following count efficiently
    @Query("SELECT COUNT(f) FROM Follow f WHERE f.follower = :user")
    long countFollowing(@Param("user") User user);
    
    // Delete all follows for a user (useful when deleting user account)
    @Modifying
    @Transactional
    @Query("DELETE FROM Follow f WHERE f.follower = :user OR f.following = :user")
    void deleteAllByUser(@Param("user") User user);
    
    // Find users who are not followed by a specific user (for suggestions)
    @Query("SELECT u FROM User u WHERE u != :user " +
           "AND NOT EXISTS (SELECT f FROM Follow f WHERE f.follower = :user AND f.following = u)")
    List<User> findUsersNotFollowedBy(@Param("user") User user);
}