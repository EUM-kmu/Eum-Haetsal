package com.eum.haetsal.service;

import com.eum.haetsal.domain.fcmtoken.FcmToken;
import com.eum.haetsal.domain.fcmtoken.FcmTokenRepository;
import com.eum.haetsal.domain.user.User;
import com.eum.haetsal.domain.user.UserRepository;
import com.eum.haetsal.messageq.FcmProducer;
import com.eum.haetsal.service.DTO.FcmTokenDTO;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService {
    private  final FcmTokenRepository fcmTokenRepository;
    private final FcmProducer fcmProducer;
    private final UserRepository userRepository;

    public void updateToken(String token, Long userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("invalid userId"));

        FcmToken fcmToken = fcmTokenRepository.findByUser(user)
                .map(existingToken -> {
                    existingToken.setToken(token); // 기존 토큰 값을 새로운 토큰 값으로 업데이트
                    return fcmTokenRepository.save(existingToken); // 업데이트된 토큰을 저장
                })
                .orElseGet(() -> {
                    FcmToken newToken = FcmToken.builder().user(user).token(token).build();
                    return fcmTokenRepository.save(newToken); // 새로 생성된 토큰을 저장
                });

        FcmTokenDTO fcmTokenDTO = new FcmTokenDTO(fcmToken);
        fcmProducer.send(fcmTokenDTO);
    }
    public  String sendTestNotification(String token, String title, String body) {
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setToken(token)
                .build();

        try {
            return FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send notification";
        }
    }
    public  void sendNotification(User user, String title, String body) {
        fcmTokenRepository.findByUser(user).ifPresent(existingToken -> {
            String getToken = existingToken.getToken();
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .setToken(getToken)
                    .build();

            try {
                FirebaseMessaging.getInstance().send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}



