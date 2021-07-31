package persistence;

import org.json.JSONObject;

/**
 * Savable interface contains one method that can be used by any other classes that implement it to convert into
 * a JSON Object
 * Savable interface is based on JsonSerializationDemo's Writable interface
 */
public interface Savable {
    // EFFECTS: returns this as JSON Object
    JSONObject convertToJson();
}
