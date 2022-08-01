package me.dio.futnews.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import me.dio.futnews.domain.News;

@Database(entities = {News.class}, version = 1)
public abstract class FutNewsDb extends RoomDatabase {

    public abstract NewsDao newsDao();

}
