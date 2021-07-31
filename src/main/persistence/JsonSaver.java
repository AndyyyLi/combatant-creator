package persistence;

import model.Character;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * JsonSaver class saves character's name and exact equipment (including special features and extremifications) to file
 * All methods are based on JsonSerializationDemo's JsonWriter class
 */

public class JsonSaver {
    private static final int INDENT = 2;
    private PrintWriter saver;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonSaver(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens saver; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        saver = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: saves JSON representation of character to file
    public void save(Character c) {
        JSONObject json = c.convertToJson();
        saveToFile(json.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: closes saver
    public void close() {
        saver.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        saver.print(json);
    }
}
