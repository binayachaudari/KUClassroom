package com.example.binaya.kuclassroom;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class GetInstanceID extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshToken = FirebaseInstanceId.getInstance().getToken();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notification");
        reference.child("token").setValue(refreshToken);
    }

}
