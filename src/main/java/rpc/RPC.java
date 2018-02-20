package rpc;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.UUID;

public class RPC {
    private final static String JSONRPC = "jsonrpc";
    private final static String ID = "id";
    private final static String METHOD = "method";
    private final static String PARAMS = "params";
    private final static String BROADCAST = "true";
    private final static String RPCVERSION = "2.0";
    private final static String _METHOD = "transfer";
    private final static String FROM = "bitcoinlatinadummy1";
    private final static String CURRENCY = "BCL";

    private final static String UNLOCK_METHOD = "unlock";
    //TODO remove pk
    private final static String pk = "[]";

    public static void unlock_rpc_call() {
        //Create the json request object
        org.json.JSONObject jsonRequest = new org.json.JSONObject();
        try {
            jsonRequest.put(JSONRPC, RPCVERSION);
            jsonRequest.put(ID, UUID.randomUUID().hashCode());
            jsonRequest.put(METHOD, UNLOCK_METHOD);
            jsonRequest.put(PARAMS, pk);
        } catch (org.json.JSONException e1) {
            System.err.println("INVALID JSON ERROR");
        }
        sendRPC(jsonRequest);
    }

    public static void tranfer_rpc_call(String to, String amount) {
        //Create the json request object
        org.json.JSONObject jsonRequest = new org.json.JSONObject();
        JSONArray params = new JSONArray();
        params.put(FROM);
        params.put(to);
        params.put(amount);
        params.put(CURRENCY);
        params.put("");
        params.put(BROADCAST);
        try {
            jsonRequest.put(JSONRPC, RPCVERSION);
            jsonRequest.put(ID, UUID.randomUUID().hashCode());
            jsonRequest.put(METHOD, _METHOD);
            jsonRequest.put(PARAMS, params);
            System.out.println(jsonRequest.toString());
        } catch (org.json.JSONException e1) {
            System.err.println("INVALID JSON ERROR");
        }
        sendRPC(jsonRequest);
    }

    //TODO handle "ERROR" return
    private static void sendRPC(JSONObject j) {
        URL url;
        OutputStream out = null;
        try {
            url = new URL("http://127.0.0.1:8090");
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setDoOutput(true);
            connection.connect();
            out = connection.getOutputStream();

            out.write(j.toString().getBytes());
            out.flush();
            out.close();

            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                System.err.println("HTTP ERROR " + statusCode);
            }

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                response.append(line);
            }
            /*
            JsonParser parser = new JsonParser();
            JsonObject resp = (JsonObject) parser.parse(new StringReader(response.toString()));

            JsonElement result = resp.get("result");
            JsonElement error = resp.get("error");
                if (result != null)
                    //do nothing
                if (error != null)
                    System.err.println(error.toString());
            assert result != null;
            result.toString();*/
        } catch (IOException e) {
            System.err.println("COULDN'T COMPLETE RPC CALL \n\n"+ Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
