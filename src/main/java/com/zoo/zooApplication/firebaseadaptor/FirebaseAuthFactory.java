package com.zoo.zooApplication.firebaseadaptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.function.Supplier;

public class FirebaseAuthFactory implements Supplier<IFirebaseAuth> {

	private HttpServletRequest request;

	@Inject
	public FirebaseAuthFactory(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public IFirebaseAuth get() {
		return request == null ? null : (IFirebaseAuth) request.getAttribute(IFirebaseAuth.NAME);
	}
}
