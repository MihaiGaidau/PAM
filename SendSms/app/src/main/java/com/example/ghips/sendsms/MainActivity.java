package com.example.ghips.sendsms;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String url;
    Document doc;
    Bitmap captchaImg;
    Handler handler;


    EditText sentEt;
    EditText toEt;
    EditText mess;
    ImageView myCapcha;
    Button sendBtn;
    EditText captchaTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler(Looper.getMainLooper());
        url ="https://www.moldcell.md/sendsms";
        sentEt = (EditText)findViewById(R.id.fromET);
        toEt = (EditText)findViewById(R.id.toET);
        mess = (EditText) findViewById(R.id.messageT);
        myCapcha = (ImageView)findViewById(R.id.capchaIV);
        captchaTxt = (EditText)findViewById(R.id.capchaET);
        sendBtn = (Button)findViewById(R.id.sendBtn);
        Log.d("misa","point1");
        GetRequest();
        Log.d("misa","point2");
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("misa","point3");
                PostRequest();
                Log.d("misa","point4");
            }
        });

    }

    private void PostRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Connection.Response resp = Jsoup.connect(url)
                            .data("phone", toEt.getText().toString())
                            .data("name", sentEt.getText().toString())
                            .data("message" ,mess.getText().toString())
                            .data("captcha_sid" ,doc.select(".captcha > input[name=captcha_sid]").val())
                            .data("captcha_token" ,doc.select(".captcha > input[name=captcha_token]").val())
                            .data("captcha_response" ,captchaTxt.getText().toString())
                            .data("conditions" ,"1")
                            .data("op" ,"")
                            .data("form_build_id" ,doc.select(".websms-form > input[name=form_build_id]").val())
                            .data("form_id" ,"websms_main_form")
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36")
                            .referrer(url)
                            .followRedirects(true)
                            .validateTLSCertificates(false)
                            .method(Connection.Method.POST)
                            .execute();

                }catch (IOException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        GetRequest();
                    }
                });

            }
        }).start();
    }

    private void GetRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36").validateTLSCertificates(false);
                    doc = connection.get();
                  //  System.out.println("qqq" + doc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Element captcha = doc.select(".captcha > img").first();
                if (captcha == null) {
                    throw new RuntimeException("error");
                }
                Connection.Response response = null;
                try {
                    response = Jsoup
                            .connect(captcha.absUrl("src"))
                            .validateTLSCertificates(false)
                            .ignoreContentType(true)
                            .execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                captchaImg = BitmapFactory.decodeStream(new ByteArrayInputStream(response.bodyAsBytes()));

                //view image
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCapcha.setImageBitmap(captchaImg);
                    }
                });
            }
        }).start();
    }
}
