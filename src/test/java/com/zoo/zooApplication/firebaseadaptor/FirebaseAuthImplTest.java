package com.zoo.zooApplication.firebaseadaptor;

import com.google.common.collect.ImmutableMap;
import com.google.firebase.auth.FirebaseToken;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Map;

import static org.junit.Assert.*;

public class FirebaseAuthImplTest {

	private FirebaseToken firebaseToken;

	@Before
	public void setUp() throws Exception {
		Map<String, Object> claimMap = ImmutableMap.of("sub", "testSub", "name", "testName", "email", "testEmail");
		Constructor<FirebaseToken> firebaseTokenConstructor = FirebaseToken.class.getDeclaredConstructor(Map.class);
		firebaseTokenConstructor.setAccessible(true);
		firebaseToken = firebaseTokenConstructor.newInstance(claimMap);
	}

	@Test
	public void testGetUid() {
		FirebaseAuthImpl firebaseAuth = new FirebaseAuthImpl(firebaseToken);
		assertEquals("testSub", firebaseAuth.getUid());
	}

	@Test
	public void testGetEmail() {
		FirebaseAuthImpl firebaseAuth = new FirebaseAuthImpl(firebaseToken);
		assertEquals("testEmail", firebaseAuth.getEmail());
	}

	@Test
	public void testGetDisplayName() {
		FirebaseAuthImpl firebaseAuth = new FirebaseAuthImpl(firebaseToken);
		assertEquals("testName", firebaseAuth.getDisplayName());
	}
}