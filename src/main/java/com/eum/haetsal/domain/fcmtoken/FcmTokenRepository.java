package com.eum.haetsal.domain.fcmtoken;

import com.eum.haetsal.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken,Long> {
    Optional<FcmToken> findByUser(User user);
}
