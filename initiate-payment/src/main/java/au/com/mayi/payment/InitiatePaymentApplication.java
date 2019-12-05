package au.com.mayi.payment;

import io.zeebe.spring.client.EnableZeebeClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
@EnableZeebeClient
public class InitiatePaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitiatePaymentApplication.class, args);
	}
}
