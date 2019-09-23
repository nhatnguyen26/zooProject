package com.zoo.zooApplication.firebaseadaptor;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

/**
 * The significant of this Binder is to allow jersey to recognize {@link IFirebaseAuth}
 * and allow {@link javax.ws.rs.core.Context} injection at method level or class level without WARNING
 * This also make debugging the Injection easier as with pure Spring method, it might skip debug point.
 */
public class FirebaseAuthBinder extends AbstractBinder {

	@Override
	protected void configure() {
		// Bind proxiable IFirebaseAuth
		bindFactory(FirebaseAuthFactory.class)
			.to(IFirebaseAuth.class)
			.proxy(true)
			.proxyForSameScope(false)
			.in(RequestScoped.class);

	}
}
