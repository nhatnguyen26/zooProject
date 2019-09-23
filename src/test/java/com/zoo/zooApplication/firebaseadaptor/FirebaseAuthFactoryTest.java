package com.zoo.zooApplication.firebaseadaptor;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FirebaseAuthFactoryTest {

	@Test
	public void getFromNullRequest() {
		FirebaseAuthFactory factory = new FirebaseAuthFactory(null);
		assertNull(factory.get());
	}

	@Test
	public void getFromRequest() {
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		IFirebaseAuth expectFirebaseAuth = mock(IFirebaseAuth.class);
		when(mockRequest.getAttribute(IFirebaseAuth.NAME)).thenReturn(expectFirebaseAuth);
		FirebaseAuthFactory factory = new FirebaseAuthFactory(mockRequest);
		assertEquals(expectFirebaseAuth, factory.get());
	}
}