package com.eum.haetsal.messageq;

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
@Service
@RequiredArgsConstructor
public class FcmDeleteConsumer {
    private final UserRepository userRepository;
    private final FcmTokenRepository fcmTokenRepository;
    @KafkaListener(topics = "eum-fcm-delete")
    public void updateQty(String kafkaMessage) {
        log.info("fcm delete Kafka Message: ->" + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        Integer index = (Integer) map.get("userId");
        User getUser = userRepository.findById(Long.valueOf(index)).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
        fcmTokenRepository.findByUser(getUser).ifPresent(existingToken -> fcmTokenRepository.delete(existingToken));


    }
}
