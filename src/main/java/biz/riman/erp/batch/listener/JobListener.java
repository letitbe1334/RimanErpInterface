package biz.riman.erp.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobListener implements JobExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(JobListener.class);

//    @Override
//    public void afterJob(JobExecution jobExecution) {
//        logger.info("JOB 수행완료 {}", jobExecution);
//
//        if (jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
//            logger.info("성공 슬랙 API");
//        } else {
//            logger.info("실패 슬랙 API");
//        }
//    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("job name : " + jobExecution.getJobInstance().getJobName() + " start");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        long startTime = jobExecution.getStartTime().getTime();
        long endTime = jobExecution.getEndTime().getTime();
        long executionTime = endTime - startTime;
        logger.info("job name : " + jobName  + " end "+ " execution time : "+executionTime);

    }

}
