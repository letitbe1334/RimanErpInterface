package biz.riman.erp.batch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biz.riman.erp.batch.dto.TempTableDto;
import biz.riman.erp.batch.mapper.DbConnectionMapper;

@Service
public class DbConnectionService {

    @Autowired
    private DbConnectionMapper mapper;
    
    public List<TempTableDto> getTempTables() {
        return mapper.getTempTables();
    }
}
