package com.example.towtables;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewExpense extends AppCompatActivity {

    private RecyclerView rvStudents;
    private DBhandler db;
    private ArrayList<ExpenseModel> expenses;
    private ExpenseRvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        expenses = new ArrayList<>();
        db = new DBhandler(ViewExpense.this);
        expenses = db.readAllExpenses();
        adapter = new ExpenseRvAdapter(expenses, ViewExpense.this);
        rvStudents =(RecyclerView) findViewById(R.id.idRv);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewExpense.this ,
                RecyclerView.VERTICAL, false);

        rvStudents.setLayoutManager(linearLayoutManager);
        rvStudents.setAdapter(adapter);
    }
}