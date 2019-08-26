package com.zoo.zooApplication.dao.util;

import org.apache.commons.text.RandomStringGenerator;
import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

import java.util.HashSet;
import java.util.Set;

public class ClaimKeyGenerator implements ValueGenerator<String> {

	private static final RandomStringGenerator RANDOM_STRING_GENERATOR;
	private static final Set<String> GENERATED_CLAIM_KEY;

	static {
		RANDOM_STRING_GENERATOR = new RandomStringGenerator.Builder()
			.withinRange('0', '9')
			.build();
		GENERATED_CLAIM_KEY = new HashSet<>();
	}

	@Override
	public String generateValue(Session session, Object owner) {
		String claimKey;
		do {
			claimKey = RANDOM_STRING_GENERATOR.generate(8);
		} while (GENERATED_CLAIM_KEY.contains(claimKey));
		GENERATED_CLAIM_KEY.add(claimKey);
		return claimKey;
	}
}
