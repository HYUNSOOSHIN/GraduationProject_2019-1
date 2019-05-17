package com.example.graduationproject_2019_1.Manager;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIDService";
    private static final String FCM_TOPIC_FOR_NOTICE = "notice-for-all";

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Override
    public void onTokenRefresh(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }


    private void sendRegistrationToServer(String token) {
        FirebaseMessaging.getInstance().subscribeToTopic(FCM_TOPIC_FOR_NOTICE);

        // Device Token: cbl8eVcfkNE:APA91bFCMzhOVy8-n-4uQujonahiS_rEkR6M46VR1HpfN4mfscMiDjBcMkm-rAB1KQgm13O0JMItBWg3Q7kPRfZ4s2G31INwbpoiAsXKhzCAH4Bub3sw1mb_CzpyPNW-AKgc7R09Co7B
        Log.d(TAG, "new token: "+token);
        databaseReference.child("users").setValue(token);

        // Read from the database (내가 저장한 값 잘 들어가있나 확인)
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.child("users").getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


}

