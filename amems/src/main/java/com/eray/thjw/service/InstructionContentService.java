package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.InstructionContent;

public interface InstructionContentService {

	public List<InstructionContent> selectByPrimaryKeyMainId(String mainid);
}
