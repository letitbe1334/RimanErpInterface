package biz.riman.erp.batch.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class BaseDto {
    // 등록자
    private String createdUserId;
    
    // 등록자
    private String createdUserType;

    // 수정자
    private String modifiedUserId;
    
    // 수정자
    private String modifiedUserType;

    // 최초등록일
    private String createdDatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    
    // 최종변경일
    private String modifiedDatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

}
