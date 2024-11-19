package com.chenyi.yanhuohui.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloWorldController.class)
public class HelloWorldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimpleJobLauncher mockJobLauncher;
    @MockBean
    private Job mockHelloWorldJob;

    @Test
    public void testHelloWorld() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/hello")
                        .param("name", "name")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    public void testHelloWorldJob() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/helloWorldJob")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockJobLauncher).run(any(Job.class), eq(new JobParameters(new HashMap<>())));
    }

    @Test
    public void testHelloWorldJob_SimpleJobLauncherThrowsJobExecutionAlreadyRunningException() throws Exception {
        // Setup
        when(mockJobLauncher.run(any(Job.class), eq(new JobParameters(new HashMap<>()))))
                .thenThrow(JobExecutionAlreadyRunningException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/helloWorldJob")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    public void testHelloWorldJob_SimpleJobLauncherThrowsJobRestartException() throws Exception {
        // Setup
        when(mockJobLauncher.run(any(Job.class), eq(new JobParameters(new HashMap<>()))))
                .thenThrow(JobRestartException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/helloWorldJob")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    public void testHelloWorldJob_SimpleJobLauncherThrowsJobInstanceAlreadyCompleteException() throws Exception {
        // Setup
        when(mockJobLauncher.run(any(Job.class), eq(new JobParameters(new HashMap<>()))))
                .thenThrow(JobInstanceAlreadyCompleteException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/helloWorldJob")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    public void testHelloWorldJob_SimpleJobLauncherThrowsJobParametersInvalidException() throws Exception {
        // Setup
        when(mockJobLauncher.run(any(Job.class), eq(new JobParameters(new HashMap<>()))))
                .thenThrow(JobParametersInvalidException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/helloWorldJob")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
