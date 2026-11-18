package com.example.mylibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;
    EditText etBook, etAuthor, etPages;
    TextView tvStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etBook = findViewById(R.id.etBook);
        etAuthor = findViewById(R.id.etAuthor);
        etPages = findViewById(R.id.etPages);
        tvStatus = findViewById(R.id.tvStatus);

        Button btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String bookN = etBook.getText().toString();
                if (bookN.isEmpty()){
                    etBook.setError("חובה לכתוב את שם הספר");
                    etBook.requestFocus();
                    return;
                }
                String atName=etAuthor.getText().toString();
                if(atName.isEmpty()){
                    etAuthor.setError("חובה לכתוב את שם הסופר");
                    etAuthor.requestFocus();
                    return;
                }
                String pagesText = etPages.getText().toString();
                if (pagesText.isEmpty()) {
                    etPages.setError("חובה למלא מספר עמודים");
                    etPages.requestFocus();
                    return;
                }
                int pages = Integer.parseInt(pagesText);
                Toast.makeText(MainActivity.this,"הנתונים נקלטו בהצלחה",Toast.LENGTH_SHORT).show();
                Map<String, Object> book = new HashMap<>();
                book.put("title", etBook.getText().toString());
                book.put("author", etAuthor.getText().toString());
                book.put("pages", pages);

                tvStatus.setText("שומר...");

                db.collection("books").document("current").set(book)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                tvStatus.setText("נשמר בענן");
                                Toast.makeText(MainActivity.this, "הספר נשמר", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                tvStatus.setText("השמירה נכשלה: " + e.getMessage());
                            }
                        });
            }
        });
    }
}