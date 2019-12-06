package au.com.mayi.payment;

import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.annotation.ZeebeDeployment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@EnableZeebeClient
//@ZeebeDeployment(classPathResource = "bpmn/order-process.bpmn")
@ZeebeDeployment(classPathResource = "bpmn/task-workflow.bpmn")
@RequiredArgsConstructor
public class OrderProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderProcessApplication.class, args);
	}
}
