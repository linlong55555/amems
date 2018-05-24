package com.eray.webservice.server.impl;

import javax.jws.WebService;

import com.eray.webservice.server.SimpleService;

@WebService(endpointInterface="com.eray.webservice.server.SimpleService") 
public class SimpleServiceImpl implements SimpleService {

	@Override
	public String test(String s) {
		// TODO Auto-generated method stub
		return "test";
	}

}
