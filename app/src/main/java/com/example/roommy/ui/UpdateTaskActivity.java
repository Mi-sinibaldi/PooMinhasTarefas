package com.example.roommy.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roommy.infra.DatabaseClient;
import com.example.roommy.Domain.Task;
import com.example.roommy.R;

import java.util.Calendar;

public class UpdateTaskActivity extends AppCompatActivity {

    private EditText editTextTask, editTextDesc;
    private CheckBox checkBoxFinished;
    private TextView textViewTarefa, textViewFinish, textViewCheck;
    private Button button_update, button_delete, buttonChosserYes, buttonChosserNo, buttonConfirmCheck;
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
        setContentView(R.layout.activity_update_task);

        editTextTask = findViewById(R.id.editTextTask);
        editTextDesc = findViewById(R.id.editTextDesc);
        textViewFinish = findViewById(R.id.textViewFinish);

        checkBoxFinished = findViewById(R.id.checkBoxFinished);
        textViewTarefa = findViewById(R.id.textViewTarefa);

        button_update = findViewById(R.id.button_update);
        button_delete = findViewById(R.id.button_delete);

        buttonChosserYes = findViewById(R.id.button_dialog_chooser_yes);
        buttonChosserNo = findViewById(R.id.buttton_dialog_chooser_no);


        setFonts();

        imageButtonCalendar = findViewById(R.id.imageButtonCalendar);
        imageButtonCalendar.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            datePickerDialog = new DatePickerDialog(UpdateTaskActivity.this,
                    (view, year, month, dayOfMonth) ->
                            textViewFinish.setText(formatViewDate(dayOfMonth) + "/"
                                    + formatViewDate((month + 1)) + "/" + year), day, month, year);

            datePickerDialog.updateDate(year, (month), day);
//            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
//            calendar.add(Calendar.MONTH, -1);

            //mostra no calendario do dia atual para frente.
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            datePickerDialog.show();
        });


        final Task task = (Task) getIntent().getSerializableExtra("task");

        //carrega tarefa
        loadTask(task);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogConfirmFinalizado();

            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialogChooser();

            }

        });
    }


    //carrega tarefas
    private void loadTask(Task task) {
        editTextTask.setText(task.getTask());
        editTextDesc.setText(task.getDesc());
        textViewFinish.setText(task.getFinishBy());
        checkBoxFinished.setChecked(task.isFinished());
    }

    //atualiza tarefas
    private void updateTask(final Task task) {
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

        //verifica se o campo "tarefa finalizada" esta vazio
        if (sFinishBy.isEmpty()) {
            textViewFinish.setError("Obrigatória");
            textViewFinish.requestFocus();
            Toast.makeText(getApplicationContext(), "É necessário selecionar uma data válida!", Toast.LENGTH_LONG).show();
            return;
        }

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            //atualiza alguma tarefa existente
            @Override
            // executa fora da UI thread e simula o processamento que queremos fazer através de um loop, o qual cria um contador.
            // Método que processa algo assíncronamente.
            protected Void doInBackground(Void... voids) {
                task.setTask(sTask);
                task.setDesc(sDesc);
                task.setFinishBy(sFinishBy);
                task.setFinished(checkBoxFinished.isChecked());
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .update(task);
                return null;
            }

            @Override
            // é utilizado para a comunicação entre o seu AsyncTask e a activity.
            // Método que envia os produtos encontrados para a main thread.
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));
            }
        }

        UpdateTask ut = new UpdateTask();
        ut.execute();
    }

    //deleta tarefa
    private void deleteTask(final Task task) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Excluido com sucesso", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));

            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }

    private void setFonts() {
        Typeface normal = Typeface.createFromAsset(getAssets(),
                "BwModelica-RegularCondensed.ttf");
        Typeface negrito = Typeface.createFromAsset(getAssets(),
                "BwModelica-BoldCondensed.ttf");

        editTextTask.setTypeface(normal);
        editTextDesc.setTypeface(normal);
        textViewFinish.setTypeface(normal);
        textViewTarefa.setTypeface(negrito);
        button_update.setTypeface(negrito);
        button_delete.setTypeface(negrito);
        checkBoxFinished.setTypeface(normal);

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

    public void showDialogChooser() {
        dialog = new Dialog(this, R.style.CustomAlertDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_chooser);
        dialog.setCancelable(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();

        buttonChosserYes = dialog.findViewById(R.id.button_dialog_chooser_yes);
        buttonChosserNo = dialog.findViewById(R.id.buttton_dialog_chooser_no);

        buttonChosserYes.setOnClickListener(v -> {
            final Task task = (Task) getIntent().getSerializableExtra("task");
            deleteTask(task);
            Intent intent = new Intent(UpdateTaskActivity.this, MainActivity.class);
            startActivity(intent);
        });
        buttonChosserNo.setOnClickListener(v -> dialog.dismiss());
    }

    public void showDialogConfirmFinalizado() {
        dialog = new Dialog(this, R.style.CustomAlertDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_check);
        TextView textView = dialog.findViewById(R.id.textViewCheck);

        textView.setText("Terefa atualizada com sucesso!");
        dialog.setCancelable(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();

        buttonConfirmCheck = dialog.findViewById(R.id.button_dialog_ckeck);
        buttonConfirmCheck.setOnClickListener(v -> {
            final Task task = (Task) getIntent().getSerializableExtra("task");
            updateTask(task);
            dialog.dismiss();
        });
    }
}