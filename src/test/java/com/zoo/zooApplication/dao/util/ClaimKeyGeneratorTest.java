package com.zoo.zooApplication.dao.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClaimKeyGeneratorTest {

	@Test
	public void testGenerateValue() {
		ClaimKeyGenerator generator = new ClaimKeyGenerator();
		for (int i = 0; i < 100; i++) {
			assertNotNull(generator.generateValue(null, null));
		}
	}
}