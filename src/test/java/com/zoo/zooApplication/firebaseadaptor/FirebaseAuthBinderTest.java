package com.zoo.zooApplication.firebaseadaptor;

import org.glassfish.jersey.internal.inject.SupplierClassBinding;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FirebaseAuthBinderTest {

	@Test
	public void testConfigure() {
		FirebaseAuthBinder binder = spy(new FirebaseAuthBinder());

		SupplierClassBinding<IFirebaseAuth> mockFactorySupplier = mock(SupplierClassBinding.class);
		when(binder.bindFactory(FirebaseAuthFactory.class)).thenReturn(mockFactorySupplier);
		when(mockFactorySupplier.to(IFirebaseAuth.class)).thenReturn(mockFactorySupplier);
		when(mockFactorySupplier.proxy(true)).thenReturn(mockFactorySupplier);
		when(mockFactorySupplier.proxyForSameScope(false)).thenReturn(mockFactorySupplier);
		when(mockFactorySupplier.in(RequestScoped.class)).thenReturn(mockFactorySupplier);

		binder.configure();

		verify(mockFactorySupplier).to(IFirebaseAuth.class);
		verify(mockFactorySupplier).proxy(true);
		verify(mockFactorySupplier).proxyForSameScope(false);
		verify(mockFactorySupplier).in(RequestScoped.class);
	}
}