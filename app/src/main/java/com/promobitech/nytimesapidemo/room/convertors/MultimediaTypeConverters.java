package com.promobitech.nytimesapidemo.room.convertors;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.promobitech.nytimesapidemo.room.tables.Multimedia;

import java.lang.reflect.Type;

public class MultimediaTypeConverters {
    static Gson gson = new Gson();

    @TypeConverter
    public static Multimedia stringToSomeObjectList(String data) {
        if (data == null) {
            return null;
        }
        Type listType = new TypeToken<Multimedia>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(Multimedia someObjects) {
        return gson.toJson(someObjects);
    }
}