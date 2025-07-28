package com.pulsar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pulsar.model.Follow;
import com.pulsar.model.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    
    boolean existsByFollowerAndFollowing(User follower, User following);
    
    @Modifying
    @Query("DELETE FROM Follow f WHERE f.follower = :follower AND f.following = :following")
    void deleteByFollowerAndFollowing(@Param("follower") User follower, @Param("following") User following);
    
    List<Follow> findByFollowing(User following);
    
    List<Follow> findByFollower(User follower);
    
    @Query("SELECT COUNT(f) FROM Follow f WHERE f.following = :user")
    Long countFollowers(@Param("user") User user);
    
    @Query("SELECT COUNT(f) FROM Follow f WHERE f.follower = :user")
    Long countFollowing(@Param("user") User user);
    
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
}