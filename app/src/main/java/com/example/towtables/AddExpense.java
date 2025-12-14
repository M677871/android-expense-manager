package com.example.towtables;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class AddExpense extends AppCompatActivity {

    EditText amount;
    EditText description;
    EditText date;
    Spinner categories;
    Button saveBtn;

    DBhandler db;
    ArrayList<CategoryModel> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        categories = findViewById(R.id.categories);
        saveBtn = findViewById(R.id.saveBtn);

        db = new DBhandler(this);

        categoryList = db.readAllCategories();

        ArrayList<String> names = new ArrayList<String>();

        /*
        for (CategoryModel category : categoryList)
        {
            names.add(category.getName());
        }
         */

        for (int i = 0 ; i < categoryList.size() ; i++)
            names.add(categoryList.get(i).getName());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this ,
                android.R.layout.simple_spinner_dropdown_item, names);

        categories.setAdapter(adapter);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountToSave = amount.getText().toString().trim();
                String des = description.getText().toString().trim();
                String dateToSave = date.getText().toString().trim();

                if (amountToSave.isEmpty() || des.isEmpty() || dateToSave.isEmpty()) {
                    Toast.makeText(AddExpense.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (categoryList == null || categoryList.isEmpty()) {
                    Toast.makeText(AddExpense.this, "No categories found. Reinstall app or increase DB version.", Toast.LENGTH_SHORT).show();
                    return;
                }

                double finalAmount;
                try {
                    finalAmount = Double.parseDouble(amountToSave);
                } catch (NumberFormatException e) {
                    Toast.makeText(AddExpense.this, "Amount must be a number", Toast.LENGTH_SHORT).show();
                    return;
                }

                int pos = categories.getSelectedItemPosition();
                int id = categoryList.get(pos).getId();

                db.addNewExpense(finalAmount, des, dateToSave, id);

                amount.setText("");
                description.setText("");
                date.setText("");


                Toast.makeText(AddExpense.this, "The new Expense add succesuffly" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}