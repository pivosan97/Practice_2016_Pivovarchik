import javax.json.*;
import java.io.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.LinkedList;

public class JSONSerializer {
    private JsonReader reader = null;
    private JsonWriter writer;

    public JSONSerializer(){
    }
    public void initWriter(String fileName) throws IOException {
        writer = Json.createWriter(new FileWriter(new File(fileName)));
    }

    public void write(final LinkedList<Message> messages){
        LinkedList<JsonObject> msgObjects = new LinkedList<>();
        for(Message msg : messages) {
            JsonObject msgObject = Json.createObjectBuilder()
                    .add("author", msg.getAuthor())
                    .add("text", msg.getText())
                    .add("id", "ID" + msg.getID())
                    .add("timestamp", msg.getTimestamp().toString()).build();
            msgObjects.add(msgObject);
        }

        JsonArrayBuilder histBuilder = Json.createArrayBuilder();
        for(JsonObject obj : msgObjects){
            histBuilder.add(obj);
        }
        JsonArray histArray = histBuilder.build();

        writer.writeArray(histArray);
    }

    public void closeWriter(){
        writer.close();
    }

    public void initReader(String fileName) throws FileNotFoundException {
        reader = Json.createReader(new FileReader(new File(fileName)));
    }

    public LinkedList<Message> read(){
        JsonArray msgObjArray = reader.readArray();

        LinkedList<Message> messages = new LinkedList<>();
        for (int i = 0; i < msgObjArray.size(); i++) {
            Message msg = new Message();
            msg.setAuthor(msgObjArray.getJsonObject(i).getString("author"));
            msg.setText(msgObjArray.getJsonObject(i).getString("text"));
            msg.setId(new BigInteger(msgObjArray.getJsonObject(i).getString("id").substring(2)));
            msg.setTimestamp(Timestamp.valueOf(msgObjArray.getJsonObject(i).getString("timestamp")));
            messages.add(msg);
        }

        return messages;
    }

    public void closeReader(){
        reader.close();
    }
}
