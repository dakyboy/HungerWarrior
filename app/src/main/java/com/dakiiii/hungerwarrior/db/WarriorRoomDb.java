package com.dakiiii.hungerwarrior.db;

import androidx.room.Database;

import com.dakiiii.hungerwarrior.model.Food;

@Database(entities = {Food.class}, version = 1, exportSchema = false)
public class WarriorRoomDb {

}
