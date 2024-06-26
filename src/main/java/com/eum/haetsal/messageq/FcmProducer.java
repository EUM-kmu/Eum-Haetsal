package com.eum.haetsal.messageq;

import com.eum.haetsal.service.DTO.FcmTokenDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class FcmProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    public static String TOPIC = "eum-fcm-create";

    public FcmTokenDTO send(FcmTokenDTO fcmTokenDTO) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
//        주문 정보 Json 포맷
        try {
            jsonInString = mapper.writeValueAsString(fcmTokenDTO);
        } catch(JsonProcessingException ex) {
            ex.printStackTrace();
        }

        kafkaTemplate.send(TOPIC, jsonInString);
        log.info("producer >> : " + fcmTokenDTO);

        return fcmTokenDTO;
    }
}
