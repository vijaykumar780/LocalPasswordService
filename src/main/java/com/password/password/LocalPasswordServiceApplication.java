package com.password.password;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@Log4j2
@EnableAsync
@EnableScheduling
public class LocalPasswordServiceApplication {
	public static void main(String[] args) throws Exception {
		log.info("Local password App");

		if (!isPasswordAllowed()) {
			log.info("Password can not be provided at this time");
		} else {
			String password = "dummy";
			log.info("Password: " + password);
		}
		SpringApplication.run(LocalPasswordServiceApplication.class, args);
	}

	private static boolean isPasswordAllowed() throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
		ResponseEntity<String> responseEntity = restTemplate.exchange("http://worldtimeapi.org/api/timezone/Asia/Kolkata", HttpMethod.GET, httpEntity, String.class);
		log.info("Api response {}", responseEntity.toString());
		//JSONObject jsonObject = new JSONObject("{"+responseEntity.toString().substring(responseEntity.toString().indexOf("<200,")+5, ));
		String date = responseEntity.toString().substring(responseEntity.toString()
				.indexOf("datetime: ")+10, responseEntity.toString().indexOf("+05:30")+6);

		LocalDateTime now = LocalDateTime.parse(date,
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"));
		LocalTime timeNow = LocalTime.of(now.getHour(), now.getMinute(), now.getSecond());
		log.info("Time now {}, {}", now, timeNow);

		LocalTime localStartTime = LocalTime.of(9, 14, 59);
		LocalTime localEndTime = LocalTime.of(15, 30, 1);
		if (now.getDayOfWeek().equals(DayOfWeek.SATURDAY) || now.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			return true;
		} else if (timeNow.isAfter(localStartTime) && timeNow.isBefore(localEndTime)) {
			return false;
		} else {
			return true;
		}
	}
}
