package au.com.mayi.payment.handler;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShipWithoutInsuranceHandler {
	@ZeebeWorker(type = "ship-without-insurance")
	public void handle(final JobClient jobClient, final ActivatedJob activatedJob) throws Exception {
		log.info("Got some work: {}", activatedJob);
		final Map<String, Object> message = activatedJob.getVariablesAsMap();
		log.info("Message: {}", message);
		jobClient.newCompleteCommand(activatedJob.getKey())
				.send()
				.join();
	}
}
