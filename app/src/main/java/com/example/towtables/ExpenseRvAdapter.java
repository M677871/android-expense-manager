package com.example.towtables;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseRvAdapter extends RecyclerView.Adapter<ExpenseRvAdapter.EViewHolder> {

    public class EViewHolder extends RecyclerView.ViewHolder {
        TextView amount, description, date, catName;

        public EViewHolder(@NonNull View itemView1) {
            super((itemView1));

            amount = itemView1.findViewById(R.id.viewAmount);
            description = itemView1.findViewById((R.id.ViewDesc));
            date = itemView1.findViewById(R.id.viewDate);
            catName = itemView1.findViewById(R.id.viewCategory);
        }
    }
        private ArrayList<ExpenseModel> eModalArrayList;
        private Context context;

        public ExpenseRvAdapter(ArrayList<ExpenseModel> eModalArrayList, Context context) {
            this.eModalArrayList = eModalArrayList;
            this.context = context;
        }

    @NonNull
    @Override
    public EViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.std_rv_item, parent, false);
        return new EViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EViewHolder holder, int position) {
            ExpenseModel exp = eModalArrayList.get(position);

            holder.amount.setText("Amount : " + exp.getAmount());
            holder.catName.setText("Category : " + exp.getC_name());
            holder.description.setText("Description : " + exp.getDesc());
            holder.date.setText("Date : " + exp.getDate());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, UpdateExpense.class);
                    i.putExtra("eid", exp.getE_id());
                    i.putExtra("amount", String.valueOf(exp.getAmount()));
                    i.putExtra("desc", exp.getDesc());
                    i.putExtra("date", exp.getDate());
                    i.putExtra("catName", exp.getC_name());
                    context.startActivity(i);
                }
            });

    }

    @Override
    public int getItemCount() {
        return eModalArrayList.size();
    }
}
