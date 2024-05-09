package com.example.recyclerview.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MealDatabase::class], version = 6)
abstract  class MealDatabase : RoomDatabase() {
        abstract fun mealDao() : MealDao

        companion object {
            @Volatile
            var Instances : MealDatabase? = null

            @Synchronized
            fun getInstance(context: Context): MealDatabase {
                val tempInstance = Instances
                if(tempInstance != null){
                    return tempInstance
                }
                synchronized(this){
                    val instance = Room.databaseBuilder(
                            context.applicationContext,
                            MealDatabase::class.java,
                            "user_database"
                    ).build()
                   Instances = instance
                    return instance
                }
            }
        }

}
