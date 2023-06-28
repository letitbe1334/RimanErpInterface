package biz.riman.erp.batch.mapper;

import java.util.List;

import biz.riman.erp.batch.config.database.annotation.UniverseConnection;
import biz.riman.erp.batch.dto.TempTableDto;

@UniverseConnection
public interface DbConnectionMapper {
    List<TempTableDto> getTempTables();
}
