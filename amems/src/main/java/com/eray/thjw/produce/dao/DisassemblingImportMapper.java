package com.eray.thjw.produce.dao;

import com.eray.thjw.produce.po.DisassemblingImport;

public interface DisassemblingImportMapper {
    int deleteByPrimaryKey(String id);

    int insert(DisassemblingImport record);

    int insertSelective(DisassemblingImport record);

    DisassemblingImport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DisassemblingImport record);

    int updateByPrimaryKey(DisassemblingImport record);
}