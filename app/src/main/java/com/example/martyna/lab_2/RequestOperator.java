package com.example.martyna.lab_2;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RequestOperator extends Thread{

    public interface RequestOperatorListener{
        void success (ModelPost publication);
        void failed (int responseCode);
    }

    private RequestOperatorListener listener;
    private int responseCode;
    private List<String> re = new ArrayList<>();


    public void setListener (RequestOperatorListener listener){ this.listener=listener; }

    @Override
    public void run(){
        super.run();
        try {
            ModelPost publication = request();

            if (publication != null)
                success(publication);
            else
                failed(responseCode);
        }catch (IOException e) {
            failed(-1);
        }catch (JSONException e){
            failed(-2);
        }
    }

    private ModelPost request() throws IOException, JSONException{

        URL obj = new URL("http://jsonplaceholder.typicode.com/posts");

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");

        responseCode=con.getResponseCode();
        System.out.println("Response Code:" + responseCode);

        InputStreamReader streamReader;

        if(responseCode == 200){
            streamReader=new InputStreamReader(con.getInputStream());
        }else{
            streamReader = new InputStreamReader(con.getErrorStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuffer response = new StringBuffer();


        while((inputLine=in.readLine())!=null){
            if(inputLine.toCharArray()[0] !='[' && inputLine.toCharArray()[0]!=']'){
                response = new StringBuffer();
                response.append(inputLine);
                while(!inputLine.contains("}")){
                    inputLine = in.readLine();
                    response.append(inputLine);
                }
                System.out.println(response.toString());
                re.add(response.toString());
            }
        }
        in.close();
        System.out.println(response.toString());
        if(responseCode == 200)
            return parsingJsonObject(re);
        else
            return null;
    }
    public ModelPost parsingJsonObject(List<String> res) throws JSONException{

        JSONObject object = new JSONObject(res.get(0));
        ModelPost post = new ModelPost();

        post.setId(object.optInt("id",0));
        post.setUserId(object.optInt("userId",0));

        post.setTitle(object.getString("title"));
        post.setBodyText(object.getString("body"));
        post.setSize(res.size());

        return post;
    }

    private void failed(int code){
        if(listener!=null)
            listener.failed(code);
    }

    private void success(ModelPost publication){
        if(listener!=null)
            listener.success(publication);
    }
}
