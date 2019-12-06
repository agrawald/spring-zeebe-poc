package au.com.mayi.payment.handler;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class TaskWorkflowHandler {
	@ZeebeWorker(type="create-task")
	public void handleCreateTask(final JobClient jobClient, final ActivatedJob activatedJob) throws Exception {
		log.info("Got some work: {}", activatedJob);
		final Map<String, Object> message = activatedJob.getVariablesAsMap();
		log.info("Message: {}", message);
		jobClient.newCompleteCommand(activatedJob.getKey())
				.send()
				.join();
	}

	@ZeebeWorker(type="update-task-status")
	public void handleUpdateTaskStatus(final JobClient jobClient, final ActivatedJob activatedJob) throws Exception {
		log.info("Got some work: {}", activatedJob);
		final Map<String, Object> message = activatedJob.getVariablesAsMap();
		log.info("Message: {}", message);
		jobClient.newCompleteCommand(activatedJob.getKey())
				.send()
				.join();
	}

	@ZeebeWorker(type="send-notification")
	public void handleSendNotification(final JobClient jobClient, final ActivatedJob activatedJob) throws Exception {
		log.info("Got some work: {}", activatedJob);
		final Map<String, Object> message = activatedJob.getVariablesAsMap();
		log.info("Message: {}", message);
		jobClient.newCompleteCommand(activatedJob.getKey())
				.send()
				.join();
	}
}
