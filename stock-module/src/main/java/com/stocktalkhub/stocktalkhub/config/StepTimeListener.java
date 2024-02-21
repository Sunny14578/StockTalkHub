package com.stocktalkhub.stocktalkhub.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class StepTimeListener extends StepExecutionListenerSupport {

    private long startTime;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        startTime = System.currentTimeMillis();
    }
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Step " + stepExecution.getStepName() + " took " + duration + " milliseconds");
        return stepExecution.getExitStatus();
    }
}
