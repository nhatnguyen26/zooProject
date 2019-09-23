package com.zoo.zooApplication.application.filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;

import static org.mockito.Mockito.*;

public class ZooMasterAuthFilterTest {
	private ZooMasterAuthFilter testFilter;

	@Before
	public void setUp() {
		testFilter = new ZooMasterAuthFilter();
		ReflectionTestUtils.setField(testFilter, "masterKey", "testKey");
	}

	@Test
	public void testFilterPass() {
		ContainerRequestContext mockContext = mock(ContainerRequestContext.class);
		when(mockContext.getHeaderString("X-Authorization-Master")).thenReturn("testKey");
		testFilter.filter(mockContext);
	}

	@Test(expected = WebApplicationException.class)
	public void testFilterWrongHeaderValue() {
		ContainerRequestContext mockContext = mock(ContainerRequestContext.class);
		when(mockContext.getHeaderString("X-Authorization-Master")).thenReturn("testToken");
		testFilter.filter(mockContext);
	}

	@Test(expected = WebApplicationException.class)
	public void testFilterNoHeader() {
		ContainerRequestContext mockContext = mock(ContainerRequestContext.class);
		testFilter.filter(mockContext);
	}
}