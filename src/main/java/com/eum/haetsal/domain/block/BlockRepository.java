package com.eum.haetsal.domain.block;

import com.eum.haetsal.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block,Long> {
//    @Query("SELECT b.blocker FROM Block b WHERE b.blocker = :blocker")
//    Optional<List<User>> findByBlocker(@Param("blocker") User blocker);
    Optional<List<Block>> findByBlocker(Profile blocker);
//    Optional<List<Block>> findByBlocked(User blocker);
    Optional<List<Block>> findByBlocked( Profile blocked);


    Boolean existsByBlockerAndBlocked(Profile blocker, Profile blocked);
    Optional<Block> findByBlockerAndBlocked(Profile blocker, Profile blocked);
}
