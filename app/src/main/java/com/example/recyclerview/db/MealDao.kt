package com.example.recyclerview.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.recyclerview.pojo.Meal

@Dao
interface MealDao {
    @Insert
     suspend fun insertMeal(meal:Meal)

     @Update
     suspend fun updateMeal(meal: Meal)

/* Can use one function instead insert or update in single function upsert
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun upsertMeal(meal: Meal) */

     @Delete
     suspend fun deleteMeal(meal: Meal)

     @Query("Select * From  mealInformation")
             fun getAllMeals(): LiveData<List<Meal>>



}