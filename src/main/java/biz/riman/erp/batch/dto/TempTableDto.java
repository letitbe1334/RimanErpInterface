package biz.riman.erp.batch.dto;

import lombok.Data;

@Data
public class TempTableDto extends BaseDto {
    // 아이디
    private String tableId;
    
    // 컬럼1
    private String tableColumn1;
    
    // 컬럼2
    private String tableColumn2;
}
