package com.example.towtables;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateExpense extends AppCompatActivity {

    EditText updateAmount;
    EditText updateDesc;
    Button editBtn;
    Button deleteBtn;
    String oldAmount;
    String oldDesc;

    DBhandler db;
    int eid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editBtn = findViewById(R.id.editBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        updateAmount = findViewById(R.id.updateAmount);
        updateDesc = findViewById(R.id.updateDesc);
        db = new DBhandler(this);

        oldAmount = getIntent().getStringExtra("amount");
        oldDesc = getIntent().getStringExtra("desc");
        eid = getIntent().getIntExtra("eid", 0);

        updateAmount.setText(oldAmount);
        updateDesc.setText(oldDesc);

        editBtn.setOnClickListener(v -> {

            String newAmount = updateAmount.getText().toString().trim();
            String newDesc = updateDesc.getText().toString().trim();

            if (eid == 0) {
                Toast.makeText(UpdateExpense.this, "Invalid expense id (eid=0). Fix readAllExpenses()", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newAmount.isEmpty() || newDesc.isEmpty()) {
                Toast.makeText(UpdateExpense.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double a;
            try {
                a = Double.parseDouble(newAmount);
            } catch (NumberFormatException e) {
                Toast.makeText(UpdateExpense.this, "Amount must be a number", Toast.LENGTH_SHORT).show();
                return;
            }

            db.updateExpense(eid, newDesc, a);
            Toast.makeText(UpdateExpense.this, "Updated", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(UpdateExpense.this, ViewExpense.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        });


    deleteBtn.setOnClickListener(v -> {
        new AlertDialog.Builder(this)
                .setTitle("Delete Expense")
                .setMessage("Do you want to delete the selected expense ?")
                .setPositiveButton("Yes", (DialogInterface dialog, int which) -> {
                    db.deleteExpenses(eid);
                    Toast.makeText(this, "Expense deleted successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    });

}
}