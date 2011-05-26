package com.prefabsoft.dev.test;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

//@Controller
@Scope("session")
public class TestManagedBean {

	private String testString = "test";

	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}
	
}
