package com.example.roommy.infra;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;


    // objeto de banco de dados de aplicativo
    private AppDatabase appDatabase;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;


        // criando o banco de dados do aplicativo com o construtor de banco de dados do Room
        // MyToDos é o nome do banco de dados.
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "MyToDos").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
