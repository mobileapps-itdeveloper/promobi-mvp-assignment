package com.promobitech.nytimesapidemo.room.convertors;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.promobitech.nytimesapidemo.room.tables.Link;

import java.lang.reflect.Type;

public class LinkTypeConverters {
    static Gson gson = new Gson();

    @TypeConverter
    public static Link stringToSomeObjectList(String data) {
        if (data == null) {
            return null;
        }
        Type listType = new TypeToken<Link>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(Link someObjects) {
        return gson.toJson(someObjects);
    }
}