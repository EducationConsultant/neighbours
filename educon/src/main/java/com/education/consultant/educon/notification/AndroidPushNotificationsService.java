package com.education.consultant.educon.notification;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class AndroidPushNotificationsService {

        private static final String FIREBASE_SERVER_KEY = "AAAAu4RaJx8:APA91bGbmxbLMuQSesOsdIJ8ymcvF2N6ghrDyharZpK761FF6trDPBHxxBIy8KUjUxwn9uQc9tZj5Zp1lStO8r6Gr0zdUS8LuEkkECslr25F93tHhCRL6n8tIe7JqLzJiOy2EdQ5nT_Y";
        private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

        @Async
        public CompletableFuture<String> send(HttpEntity<String> entity) {

            RestTemplate restTemplate = new RestTemplate();

            /**
             https://fcm.googleapis.com/fcm/send
             Content-Type:application/json
             Authorization:key=FIREBASE_SERVER_KEY*/

            ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
            interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
            interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
            restTemplate.setInterceptors(interceptors);

            String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

            return CompletableFuture.completedFuture(firebaseResponse);
        }
}