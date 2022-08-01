package me.dio.futnews.data;

import androidx.room.Room;

import me.dio.futnews.App;
import me.dio.futnews.data.local.FutNewsDb;
import me.dio.futnews.data.remote.FutNewsApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FutNewsRepository {

    //region Constantes
    private static final String REMOTE_API_URL = "https://victor-drt.github.io/fut-news-api/";
    private static final String LOCAL_DB_NAME = "fut_news";
    //endregion

    //region Atributos:encapsular o acesso a nossa API (Retrofit) e banco de dados local(Room)
    private FutNewsApi remoteApi;
    private FutNewsDb localDb;

    public FutNewsApi getRemoteApi() {
        return remoteApi;
    }

    public FutNewsDb getLocalDb() {
        return localDb;
    }

//endregion

    //region Singleton: garante uma instancia unica dos atributos relacionados ao Retrofit e Room
    private FutNewsRepository() {
        remoteApi = new Retrofit.Builder()
                .baseUrl(REMOTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FutNewsApi.class);

        localDb = Room.databaseBuilder(App.getInstance(), FutNewsDb.class, LOCAL_DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public static FutNewsRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final FutNewsRepository INSTANCE = new FutNewsRepository();
    }
    //endregion

}
