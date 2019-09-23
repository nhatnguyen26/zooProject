package com.zoo.zooApplication.firebaseadaptor;

import com.google.firebase.auth.FirebaseToken;

public class FirebaseAuthImpl implements IFirebaseAuth {

    private final FirebaseToken token;

    public FirebaseAuthImpl(FirebaseToken token) {
        this.token = token;
    }

    @Override
    public String getUid() {
        return token.getUid();
    }

    @Override
    public String getEmail() {
        return token.getEmail();
    }

    @Override
    public String getDisplayName() {
        return token.getName();
    }

}
