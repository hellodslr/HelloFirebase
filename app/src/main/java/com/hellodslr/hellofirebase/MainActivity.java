package com.hellodslr.hellofirebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private EditText input, input2;
    private Button btn, btnUpdaet;
    private Button btnRead, btnDelete;
    private TextView textView, textView2;
    private DatabaseReference rootDatabaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input);
        input2 = findViewById(R.id.input2);
        btn = findViewById(R.id.btn);
        btnUpdaet = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnRead = findViewById(R.id.btnRead);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        rootDatabaseref = FirebaseDatabase.getInstance().getReference().child("Users");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(input.getText().toString());
                String name = input2.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("ID", id);
                hashMap.put("Name", name);

                // rootDatabaseref 고유 key값을 받아서 넘겨줌
//                String key = rootDatabaseref.push().getKey();
//                rootDatabaseref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {

                // 현재는 기본적인 기능구현만

                rootDatabaseref.child("user1").setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Your Data is Successfully added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Sorry!!, Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addValueEventListener 실행 후 데이터를 실시간 체크하여 가져 옴
//                rootDatabaseref.child("user1").addValueEventListener(new ValueEventListener() {

//                addListenerForSingleValueEvent 실행 할때만 데이터를 읽어 옴
                rootDatabaseref.child("user1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                            Object id = map.get("ID");
                            String name = (String) map.get("Name");

                            textView.setText("" + id);
                            textView2.setText(name);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        btnUpdaet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(input.getText().toString());
                String name = input2.getText().toString();
                HashMap hashMap = new HashMap();
                hashMap.put("ID", id);
                hashMap.put("Name", name);

                rootDatabaseref.child("user1").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(MainActivity.this, "Your Data is Successfully Updated", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootDatabaseref.child("user1").removeValue();

            }
        });
    }
}