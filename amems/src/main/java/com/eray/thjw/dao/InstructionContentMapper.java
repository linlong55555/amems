package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.InstructionContent;

public interface InstructionContentMapper {
    int deleteByPrimaryKey(String id);

    int insert(InstructionContent record);

    int insertSelective(InstructionContent record);

    InstructionContent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InstructionContent record);

    int updateByPrimaryKey(InstructionContent record);
    
    List<InstructionContent> selectByPrimaryKeyMainId(String mainid);
}