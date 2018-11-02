package sample.android.com.artistmanagement;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etCountry, etGender;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initVariable();
    }

    private void initVariable() {
        reference = FirebaseDatabase.getInstance().getReference("Artist");
    }

    private void initView() {
        etName = findViewById(R.id.etName);
        etCountry = findViewById(R.id.etCountry);
        etGender = findViewById(R.id.etGender);
    }

    public void save(View view) {

        String name, country, gender;

        name = etName.getText().toString().trim();
        country = etCountry.getText().toString().trim();
        gender = etGender.getText().toString().trim();

        String id = reference.push().getKey();

        Artist artist = new Artist(id, name, country, gender);

        reference.child(id).setValue(artist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
