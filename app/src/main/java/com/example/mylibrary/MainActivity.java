package com.example.mylibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText etBook = findViewById(R.id.etBook);
        EditText etAuthor = findViewById(R.id.etAuthor);
        EditText etPages = findViewById(R.id.etPages);

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
            }
        });
    }
}