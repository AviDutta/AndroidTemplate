package com.claricetechnologies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.claricetechnologies.data.ClConstants;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class ClFileUtils {
    protected static ClFileUtils fileUtils;
    protected SharedPreferences.Editor mEditor = null;
    protected SharedPreferences mSettings = null;

    public static ClFileUtils getInstance() {
        if (fileUtils == null) {
            fileUtils = new ClFileUtils();
        }
        return fileUtils;
    }

    /**
     * @param appContext : application Context
     */
    public void initPreferences(Context appContext) {
        if (mSettings == null || mEditor == null) {
            mSettings = appContext.getSharedPreferences(getFileName(),
                    Context.MODE_PRIVATE);
            mEditor = mSettings.edit();
        }
    }

    protected String getFileName() {
        return "clarice-data";
    }

    public void setInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public void setDouble(String key, double value) {
        mEditor.putLong(key, Double.doubleToRawLongBits(value));
        mEditor.commit();
    }

    public void setString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public void setParcel(String key, Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mEditor.putString(key, mapper.writeValueAsString(object));
        } catch (JsonGenerationException e) {
            ClLogger.e(ClConstants.TAG, e.getLocalizedMessage(), e);
        } catch (JsonMappingException e) {
            ClLogger.e(ClConstants.TAG, e.getLocalizedMessage(), e);
        } catch (IOException e) {
            ClLogger.e(ClConstants.TAG, e.getLocalizedMessage(), e);
        }
        mEditor.commit();
    }

    public <T> T getParcel(String key, Class<T> classType) {
        ObjectMapper mapper = new ObjectMapper();
        String json = mSettings.getString(key, "");
        T object = null;
        try {
            if (!TextUtils.isEmpty(json))
                object = mapper.readValue(json, classType);
            else
                return null;
        } catch (JsonParseException e) {
            ClLogger.e(ClConstants.TAG, e.getLocalizedMessage(), e);
        } catch (JsonMappingException e) {
            ClLogger.e(ClConstants.TAG, e.getLocalizedMessage(), e);
        } catch (IOException e) {
            ClLogger.e(ClConstants.TAG, e.getLocalizedMessage(), e);
        }
        return object;
    }

    public String getString(String key, String defValue) {
        return mSettings.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mSettings.getInt(key, defValue);
    }

    public double getDouble(String key, double defValue) {
        return Double.longBitsToDouble(mSettings.getLong(key, Double.doubleToLongBits(defValue)));
    }

    public void setBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSettings.getBoolean(key, defValue);
    }

    public void clearKey(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    public void clearAll() {
        mEditor.clear();
        mEditor.commit();
    }

    public void printAll() {
        Map<String, ?> keys = mSettings.getAll();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            ClLogger.d(ClConstants.TAG,
                    getFileName() + "map values " + entry.getKey() + ": "
                            + entry.getValue().toString());
        }
    }
}
