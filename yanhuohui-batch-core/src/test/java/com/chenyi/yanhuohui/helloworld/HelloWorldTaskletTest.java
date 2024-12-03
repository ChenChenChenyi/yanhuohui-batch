package com.chenyi.yanhuohui.helloworld;

import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HelloWorldTaskletTest {

    private HelloWorldTasklet helloWorldTaskletUnderTest;

    @Before
    public void setUp() {
        helloWorldTaskletUnderTest = new HelloWorldTasklet();
    }

    @Test
    public void testExecute() throws Exception {
        // Setup
        final StepContribution stepContribution = new StepContribution(
                new StepExecution("stepName", new JobExecution(0L)));
        final ChunkContext chunkContext = new ChunkContext(
                new StepContext(new StepExecution("stepName", new JobExecution(0L))));

        // Run the test
        final RepeatStatus result = helloWorldTaskletUnderTest.execute(stepContribution, chunkContext);

        // Verify the results
        assertThat(result).isEqualTo(RepeatStatus.FINISHED);
    }

    @Test
    public void testExecute_ThrowsException() {
        // Setup
        final StepContribution stepContribution = new StepContribution(
                new StepExecution("stepName", new JobExecution(0L)));
        final ChunkContext chunkContext = new ChunkContext(
                new StepContext(new StepExecution("stepName", new JobExecution(0L))));

        // Run the test
        assertThatThrownBy(() -> helloWorldTaskletUnderTest.execute(stepContribution, chunkContext))
                .isInstanceOf(Exception.class);
    }
}
