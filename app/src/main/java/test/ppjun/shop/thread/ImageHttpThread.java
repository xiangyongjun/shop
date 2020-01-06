package test.ppjun.shop.thread;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import test.ppjun.shop.util.HttpHelp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageHttpThread extends  Thread{
    private  String imageUrl; //用于接收图片路径

    private Bitmap resultBitmap;

    public ImageHttpThread(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void run() {
        try{

            URL url = new URL(imageUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            setResultBitmap(BitmapFactory.decodeStream(inputStream));

        }catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String getImageUrl() {
        return imageUrl;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public Bitmap getResultBitmap() {
        return resultBitmap;
    }


    public void setResultBitmap(Bitmap resultBitmap) {
        this.resultBitmap = resultBitmap;
    }

}
