package com.eum.haetsal.messageq;

import com.eum.haetsal.domain.fcmtoken.FcmToken;
import com.eum.haetsal.domain.fcmtoken.FcmTokenRepository;
import com.eum.haetsal.domain.user.User;
import com.eum.haetsal.domain.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RequiredArgsConstructor
@Service
public class FcmConsumer {
    private final UserRepository userRepository;
    private final FcmTokenRepository fcmTokenRepository;
    @KafkaListener(topics = "eum-fcm-create")
    public void updateQty(String kafkaMessage) {
        log.info("fcm 토큰 Kafka Message: ->" + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        Integer index = (Integer) map.get("userId");
        String token = (String) map.get("token");

        User getUser = userRepository.findById(Long.valueOf(index)).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
        if(!token.equals("")){
            fcmTokenRepository.findByUser(getUser)
                    .map(existingToken -> {
                        existingToken.setToken(token); // 기존 토큰 값을 새로운 토큰 값으로 업데이트
                        return fcmTokenRepository.save(existingToken); // 업데이트된 토큰을 저장
                    })
                    .orElseGet(() -> {
                        FcmToken newToken = FcmToken.builder().user(getUser).token(token).build();
                        return fcmTokenRepository.save(newToken); // 새로 생성된 토큰을 저장
                    });
        }

    }
}
