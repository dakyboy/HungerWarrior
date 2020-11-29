package com.dakiiii.hungerwarrior.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dakiiii.hungerwarrior.model.Food;

import java.util.List;

@Dao
public interface FoodDao {

    @Query("SELECT * FROM foods_table")
    List<Food> getAllFoods();

    @Query("DELETE FROM foods_table")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFood(Food food);

//    @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
//    public User[] loadAllUsersBetweenAges(int minAge, int maxAge);


    @Query("SELECT foodId, foodName, foodPrice, foodDescription FROM foods_table WHERE foodId = :foodId")
    Food getFood(int foodId);

}
