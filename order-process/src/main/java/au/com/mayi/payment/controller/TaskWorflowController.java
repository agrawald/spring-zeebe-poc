package au.com.mayi.payment.controller;

import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.spring.client.ZeebeClientLifecycle;
import io.zeebe.spring.client.annotation.ZeebeDeployment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@ZeebeDeployment(classPathResource = "bpmn/task-workflow.bpmn")
public class TaskWorflowController {
	private final ZeebeClientLifecycle client;

	@Value("task-workflow")
	private String processId;

	@PostMapping("/submit")
	@ResponseBody
	public WorkflowInstanceEvent submitTask(@RequestBody Map<String, Object> task) {
		final WorkflowInstanceEvent wfInstance = client.newCreateInstanceCommand()
				.bpmnProcessId(processId)
				.latestVersion()
				.variables(task)
				.send()
				.join();
		final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();
		log.info("Started instance for workflowKey='{}', bpmnProcessId='{}', version='{}' with workflowInstanceKey='{}'",
				wfInstance.getWorkflowKey(), wfInstance.getBpmnProcessId(), wfInstance.getVersion(), wfInstance.getWorkflowInstanceKey());
		return wfInstance;
	}

	@PutMapping("/{taskId}/{status}")
	@ResponseBody
	public String paymentReceived(@PathVariable("taskId") String correlationId, @PathVariable("status") String status) {
		client.newPublishMessageCommand()
				.messageName("manual_approval")
				.correlationKey(correlationId)
				.variables("{\"status\": \""+status+"\"}")
				.send()
				.join();
		return "Done";
	}

}
