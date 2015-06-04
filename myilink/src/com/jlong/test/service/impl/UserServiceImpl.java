package com.jlong.test.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;


import com.jlong.test.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public void say() {
			List<String> list = null;
			list.add("String");
	}
}
