package com.zoo.zooApplication.application.filter;

import com.google.common.collect.ImmutableMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.zoo.zooApplication.firebaseadaptor.FirebaseAuthImpl;
import com.zoo.zooApplication.firebaseadaptor.IFirebaseAuth;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.mockito.Mockito.*;

public class FirebaseAuthFilterTest {
	@Mock
	private FirebaseAuth mockFirebaseAuth;

	@InjectMocks
	private FirebaseAuthFilter testFilter;

	private FirebaseToken firebaseToken;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		Map<String, Object> claimMap = ImmutableMap.of("sub", "testSub", "name", "testName", "email", "testEmail");
		Constructor<FirebaseToken> firebaseTokenConstructor = FirebaseToken.class.getDeclaredConstructor(Map.class);
		firebaseTokenConstructor.setAccessible(true);
		firebaseToken = firebaseTokenConstructor.newInstance(claimMap);
	}

	@Test
	public void testFilterPass() throws IOException, FirebaseAuthException {
		ContainerRequestContext mockContext = mock(ContainerRequestContext.class);
		when(mockContext.getHeaderString("X-Authorization-Firebase")).thenReturn("testToken");
		when(mockFirebaseAuth.verifyIdToken("testToken")).thenReturn(firebaseToken);
		testFilter.filter(mockContext);
		verify(mockContext, times(1)).setProperty(eq(IFirebaseAuth.NAME), any(FirebaseAuthImpl.class));
	}

	@Test(expected = WebApplicationException.class)
	public void testFilterFailedException() throws IOException, FirebaseAuthException {
		ContainerRequestContext mockContext = mock(ContainerRequestContext.class);
		when(mockContext.getHeaderString("X-Authorization-Firebase")).thenReturn("testToken");
		when(mockFirebaseAuth.verifyIdToken("testToken")).thenThrow(new FirebaseAuthException("test", "test"));
		testFilter.filter(mockContext);
	}

	@Test(expected = WebApplicationException.class)
	public void testFilterNoHeader() throws IOException {
		ContainerRequestContext mockContext = mock(ContainerRequestContext.class);
		testFilter.filter(mockContext);
	}
}