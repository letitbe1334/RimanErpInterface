package biz.riman.erp.batch.biz.riman.erp.batch.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import biz.riman.erp.batch.job.SalesOrderJob;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
public class SalesOrderTest {
    private static final Logger logger = LoggerFactory.getLogger(SalesOrderTest.class);

    @Autowired 
    private ApplicationContext applicationContext;
    @Autowired
    private JobLauncher jobLauncher;

    @Test
    public void test() {
        try {
            Job job = applicationContext.getBean(SalesOrderJob.JOB_NAME, Job.class);
            
            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date time = new Date();
            String time1 = format1.format(time);
            jobParametersMap.put("date",new JobParameter(time1));
            JobParameters parameters = new JobParameters(jobParametersMap);
    
            JobExecution jobExecution = jobLauncher.run(job, parameters);
    
            while (jobExecution.isRunning()) {
                logger.info("...");
            }
            
            logger.info("## Job Execution: " + jobExecution.getStatus());
            logger.info("## Job getJobConfigurationName: " + jobExecution.getJobConfigurationName());
            logger.info("## Job getJobId: " + jobExecution.getJobId());
            logger.info("## Job getExitStatus: " + jobExecution.getExitStatus());
            logger.info("## Job getJobInstance: " + jobExecution.getJobInstance());
            logger.info("## Job getStepExecutions: " + jobExecution.getStepExecutions());
            logger.info("## Job getLastUpdated: " + jobExecution.getLastUpdated());
            logger.info("## Job getFailureExceptions: " + jobExecution.getFailureExceptions());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
