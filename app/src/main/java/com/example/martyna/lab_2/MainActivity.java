package com.example.martyna.lab_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements RequestOperator.RequestOperatorListener{

    Button sendRequestButton;
    TextView title;
    TextView bodyText;
    private ModelPost publication;
    private IndicatingView indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sendRequestButton=(Button) findViewById(R.id.send_request);
        sendRequestButton.setOnClickListener(requestButtonClicked);

        title=(TextView)findViewById(R.id.title);
        bodyText=(TextView)findViewById(R.id.body_text);

        indicator=(IndicatingView)findViewById(R.id.generated_graphic);



    }

    View.OnClickListener requestButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setIndicatorStatus(IndicatingView.PASPAUDUS);
            updatePublication();
            sendRequest();
        }
    };
    private void sendRequest(){
        RequestOperator ro = new RequestOperator();
        ro.setListener(this);
        ro.start();
    }

    public void updatePublication(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(publication!=null){
                    title.setText(publication.getTitle());
                    bodyText.setText(publication.getBodyText());

                }else{
                    title.setText("");
                    bodyText.setText("");
                }
            }
        });
    }

    @Override
    public void success(ModelPost publication) {
        this.publication = publication;
        setIndicatorStatus(IndicatingView.SUCCESS);
        updatePublication();

    }

    @Override
    public void failed(int responseCode) {
        this.publication = null;
        setIndicatorStatus(IndicatingView.FAILED);
        updatePublication();
    }


    public void setIndicatorStatus(final int status){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                indicator.setState(status);
                indicator.invalidate();
            }
        });
    }
}
