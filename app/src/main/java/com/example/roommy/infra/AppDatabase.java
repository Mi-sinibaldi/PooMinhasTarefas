package com.example.roommy.infra;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.roommy.Domain.Task;
import com.example.roommy.infra.dao.TaskDao;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
