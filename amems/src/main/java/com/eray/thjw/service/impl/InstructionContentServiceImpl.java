package com.eray.thjw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.InstructionContentMapper;
import com.eray.thjw.po.InstructionContent;
import com.eray.thjw.service.InstructionContentService;
@Service
public class InstructionContentServiceImpl implements InstructionContentService{

	@Autowired
	private InstructionContentMapper instructionContentMapper;
	@Override
	public List<InstructionContent> selectByPrimaryKeyMainId(String mainid) {
		return instructionContentMapper.selectByPrimaryKeyMainId(mainid);
	}

}
