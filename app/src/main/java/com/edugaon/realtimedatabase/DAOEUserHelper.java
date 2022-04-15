package com.edugaon.realtimedatabase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class DAOEUserHelper {
    private final DatabaseReference databaseReference;
    public  DAOEUserHelper(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(UserDetails.class.getSimpleName());
    }

    public Task<Void> add(UserDetails userHelper){
       return databaseReference.push().setValue(userHelper);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap){
        Task<Void> voidTask = databaseReference.child(key).updateChildren(hashMap);
        return voidTask;
    }

    public Task<Void> remove(String key){
        return  databaseReference.child(key).removeValue();
    }
}
