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
@RequestMapping("/order")
@RequiredArgsConstructor
@ZeebeDeployment(classPathResource = "bpmn/order-process.bpmn")
public class OrderProcessController {
	private final ZeebeClientLifecycle client;

	@Value("order-process")
	private String processId;

	@PostMapping("/submit")
	@ResponseBody
	public WorkflowInstanceEvent deploy(@RequestBody Map<String, Object> order) {
		final WorkflowInstanceEvent wfInstance = client.newCreateInstanceCommand()
				.bpmnProcessId(processId)
				.latestVersion()
				.variables(order)
				.send()
				.join();
		final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();
		log.info("Started instance for workflowKey='{}', bpmnProcessId='{}', version='{}' with workflowInstanceKey='{}'",
				wfInstance.getWorkflowKey(), wfInstance.getBpmnProcessId(), wfInstance.getVersion(), wfInstance.getWorkflowInstanceKey());
		return wfInstance;
	}

	@PutMapping("/{orderId}/payment")
	@ResponseBody
	public String paymentReceived(@PathVariable("orderId") String correlationId) {
		client.newPublishMessageCommand()
				.messageName("payment-received")
				.correlationKey(correlationId)
				.send()
				.join();
		return "Done";
	}

}
