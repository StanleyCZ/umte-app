package com.example.umte_app.models;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.umte_app.models.daos.CartDao;
import com.example.umte_app.models.daos.CartItemDao;
import com.example.umte_app.models.entities.Cart;
import com.example.umte_app.models.entities.CartItem;
import com.example.umte_app.utils.Converters;

@Database(version = 2, entities = {Cart.class, CartItem.class})
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "umte-db";
    private static AppDatabase instance;

    public abstract CartDao cartDao();
    public abstract CartItemDao itemsDao();

    public static synchronized AppDatabase getInstance(Context ctx){
        if(instance == null){
            instance = Room.databaseBuilder(ctx.getApplicationContext(),AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private CartDao cartDao;

        private PopulateDbAsyncTask(AppDatabase db){
            cartDao = db.cartDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cartDao.insert(new Cart("Košík 1","Penny market"));
            cartDao.insert(new Cart("Pařba", "Lidl"));
            cartDao.insert(new Cart("Grilovačka", "Albert"));
            return null;
        }
    }

}
