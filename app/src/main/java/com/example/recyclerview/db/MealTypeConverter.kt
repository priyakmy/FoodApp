package com.example.recyclerview.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters


@TypeConverters
 class MealTypeConverter {
     // call function to  insert data inside table
    fun fromAnyToString(attribute: Any?) : String
    {
        if (attribute == null)
            return ""
        return attribute as String
    }

    // Call to retrieve data from Database
    @TypeConverter
    fun fromStringToAny(attribute: Any?) : Any {
        if (attribute == null)
            return ""
        return attribute

    }
}



