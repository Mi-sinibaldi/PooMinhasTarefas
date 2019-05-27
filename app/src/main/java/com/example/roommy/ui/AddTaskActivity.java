package com.example.roommy.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roommy.infra.DatabaseClient;
import com.example.roommy.Domain.Task;
import com.example.roommy.R;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextTask, editTextDesc;
    private TextView textViewAdicionarTarefa, textViewFinish;
    private Button button_save;
    private int day;
    private int month;
    private int year;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private ImageButton imageButtonCalendar;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        imageButtonCalendar.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            datePickerDialog = new DatePickerDialog(AddTaskActivity.this,
                    (view, year, month, dayOfMonth) ->
                            textViewFinish.setText(formatViewDate(dayOfMonth) + "/"
                                    + formatViewDate((month + 1)) + "/" + year), day, month, year);

            datePickerDialog.updateDate(year, (month), day);
//            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
//            calendar.add(Calendar.MONTH, -1);
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            datePickerDialog.show();
        });

        setFonts();
        loadUi();

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        //salva as tarefas
        final String sTask = editTextTask.getText().toString();
        final String sDesc = editTextDesc.getText().toString();
        final String sFinishBy = textViewFinish.getText().toString();


        //verifica se o campo "tarefa" esta vazio
        if (sTask.isEmpty()) {
            editTextTask.setError("Tarefa obrigatória");
            editTextTask.requestFocus();
            return;
        }
        //verifica se o campo "desc" esta vazio
        if (sDesc.isEmpty()) {
            editTextDesc.setError("Descrição obrigatória");
            editTextDesc.requestFocus();
            return;
        }


        //verifica se o campo data esta vazio
        if (sFinishBy.isEmpty()) {
            textViewFinish.setError("Selecione a data de finalização");
            textViewFinish.requestFocus();
            Toast.makeText(getApplicationContext(), "É necessário selecionar uma data válida!", Toast.LENGTH_LONG).show();
            return;
        }

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //criando tarefa
                Task task = new Task();
                task.setTask(sTask);
                task.setDesc(sDesc);
                task.setFinishBy(sFinishBy);
                task.setFinished(false);

                // adicionando ao banco de dados
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                //Toast.makeText(getApplicationContext(), "Salvo com sucesso!", Toast.LENGTH_LONG).show();

            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    private void setFonts() {
        Typeface normal = Typeface.createFromAsset(getAssets(),
                "BwModelica-RegularCondensed.ttf");
        Typeface negrito = Typeface.createFromAsset(getAssets(),
                "BwModelica-BoldCondensed.ttf");

        editTextTask.setTypeface(normal);
        editTextDesc.setTypeface(normal);
        textViewFinish.setTypeface(normal);
        textViewAdicionarTarefa.setTypeface(negrito);
        button_save.setTypeface(negrito);


    }

    private String formatViewDate(int data) {
        String result;
        if (data < 9) {
            result = "0" + data;
        } else {
            result = "" + data;
        }
        return result;
    }

    private void loadUi(){
        editTextTask = findViewById(R.id.editTextTask);
        editTextDesc = findViewById(R.id.editTextDesc);
        textViewFinish = findViewById(R.id.textViewFinish);
        textViewAdicionarTarefa = findViewById(R.id.textViewAdicionarTarefa);
        imageButtonCalendar = findViewById(R.id.imageButtonCalendar);

        button_save = findViewById(R.id.button_save);
    }


}