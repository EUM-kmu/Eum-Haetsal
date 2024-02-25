package com.eum.haetsal.domain.profile;

import com.eum.haetsal.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile,Long> {
    Boolean existsByNickname(String nickname);

    Optional<Profile> findByNickname(String nickName);

    Optional<Profile> findByUser(User users);

    Boolean existsByUser(User user);

}
