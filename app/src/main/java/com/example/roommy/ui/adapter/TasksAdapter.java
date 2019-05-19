package com.example.roommy.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.roommy.Domain.Task;
import com.example.roommy.R;
import com.example.roommy.ui.AddTaskActivity;
import com.example.roommy.ui.UpdateTaskActivity;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {
    //lê a tarefa, salva no banco de dados e exibe no RecyclerView
    private Context c;
    private List<Task> taskList;
    private TextView textViewStatus, textViewTask, textViewDesc, textViewFinishBy;

    public TasksAdapter(Context c, List<Task> taskList) {
        this.c = c;
        this.taskList = taskList;

    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.recyclerview_tasks, parent, false);
        return new TasksViewHolder(view);

    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Task t = taskList.get(position);
        holder.textViewTask.setText(t.getTask());
        holder.textViewDesc.setText(t.getDesc());
        holder.textViewFinishBy.setText(t.getFinishBy());

        if (t.isFinished()) {
            holder.textViewStatus.setText("Finalizado");
            holder.textViewStatus.setBackgroundColor(ContextCompat.getColor(c, R.color.colorVerde));

        } else
            holder.textViewStatus.setText("A Fazer");

    }


    @Override
    //obter contagem de itens da lista
    public int getItemCount() {
        return taskList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStatus, textViewTask, textViewDesc, textViewFinishBy;


        public TasksViewHolder(View itemView) {
            super(itemView);

            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewFinishBy = itemView.findViewById(R.id.textViewFinishBy);

            setFonts();

            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            Task task = taskList.get(getAdapterPosition());
            //intent de atualizar tarefa
            Intent intent = new Intent(c, UpdateTaskActivity.class);
            intent.putExtra("task", task);

            c.startActivity(intent);
        }

        private void setFonts() {
            Typeface normal = Typeface.createFromAsset(c.getAssets(),
                    "BwModelica-RegularCondensed.ttf");
            Typeface negrito = Typeface.createFromAsset(c.getAssets(),
                    "BwModelica-BoldCondensed.ttf");

            textViewStatus.setTypeface(negrito);
            textViewTask.setTypeface(negrito);
            textViewDesc.setTypeface(normal);
            textViewFinishBy.setTypeface(normal);

        }


    }


}