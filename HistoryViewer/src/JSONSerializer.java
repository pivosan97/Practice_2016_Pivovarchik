import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.*;
import java.math.BigInteger;
import java.sql.Timestamp;

public class JSONSerializer {
    private JsonReader reader = null;
    private JsonWriter writer;

    public JSONSerializer(){
    }
    public void initWriter(String fileName) throws IOException {
        writer = Json.createWriter(new FileWriter(new File(fileName)));
    }

    public void write(final Message msg){
        JsonObject msgObject = Json.createObjectBuilder()
                .add("author", msg.getAuthor())
                .add("text", msg.getText())
                .add("id", "ID" + msg.getID())
                .add("timestamp", msg.getTimestamp().toString())
                .build();
        writer.writeObject(msgObject);
    }

    public void closeWriter(){
        writer.close();
    }

    public void initReader(String fileName) throws FileNotFoundException {
        reader = Json.createReader(new FileReader(new File(fileName)));
    }

    public Message read(){
        try {
            JsonObject msgObject = reader.readObject();
            Message msg = new Message();
            msg.setAuthor(msgObject.getString("author"));
            msg.setText(msgObject.getString("text"));
            msg.setId(new BigInteger(msgObject.getString("id").substring(2)));
            msg.setTimestamp(Timestamp.valueOf(msgObject.getString("timestamp")));
            return msg;
        } catch(IllegalStateException err){
            return null;
        }
    }

    public void closeReader(){
        reader.close();
    }
}
