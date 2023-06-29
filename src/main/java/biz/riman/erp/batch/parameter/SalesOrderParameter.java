package biz.riman.erp.batch.parameter;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@JobScope
@Component
public class SalesOrderParameter {

    @Value("#{jobParameters[date]}")
    private String date;
    
    @Getter
    @Value("#{jobParameters[version]}")
    private String version;


    public LocalDate getDate() {
        if (Objects.isNull(this.date) || this.date.isBlank()) {
            return getYesterday();
        }
        return LocalDate.parse(this.date, ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private LocalDate getYesterday() {
        return LocalDate.now().minusDays(1);
    }

}
