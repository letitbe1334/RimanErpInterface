package biz.riman.erp.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import biz.riman.erp.batch.listener.JobListener;
import biz.riman.erp.batch.service.BTPConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SalesOrderJob {
    private static final Logger logger = LoggerFactory.getLogger(SalesOrderJob.class);
    
    @Autowired 
    public JobBuilderFactory jobBuilderFactory;
    @Autowired 
    public StepBuilderFactory stepBuilderFactory;
    @Autowired 
    public BTPConnectionService service;

    public static final String JOB_NAME = "order.SalesBatch";
    public static final String STEP_NAME = ".syncStep";

    @Bean(name = JOB_NAME)
    public Job job() {
        logger.info("## {} Job 실행 ##", JOB_NAME);
        return jobBuilderFactory.get(JOB_NAME)
                .start(syncStep())
                .listener(new JobListener())
                .build();
    }

    @Bean(name = JOB_NAME + STEP_NAME)
    @JobScope
    public Step syncStep() {
        logger.info("## {} - {} Step 실행 ##", JOB_NAME, STEP_NAME);
        return stepBuilderFactory.get(JOB_NAME + STEP_NAME)
                .tasklet((contribution, chunkContext) -> {
                    String response = service.BTPConnectionSalesOrder();
                    logger.info("## response : {}", response);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
