package com.claricetechnologies.parsers;

import android.text.TextUtils;

import com.claricetechnologies.data.ClConstants;
import com.claricetechnologies.utils.ClLogger;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class ClParserImpl implements ClParser {
    @Override
    public <T> T parseObject(String data, Class<T> model) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (!TextUtils.isEmpty(data)) {
                return mapper.readValue(data, model);
            } else {
                return null;
            }
        } catch (JsonParseException e) {
            ClLogger.e(ClConstants.TAG, e.getLocalizedMessage(), e);
        } catch (JsonMappingException e) {
            ClLogger.e(ClConstants.TAG, e.getLocalizedMessage(), e);
        } catch (IOException e) {
            ClLogger.e(ClConstants.TAG, e.getLocalizedMessage(), e);
        }
        return null;
    }
}
