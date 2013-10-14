package net.wtfitio.http;

import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.Menu;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HTTPTest extends Activity {
    String dwnload_file_path = "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-prn2/1172674_10202119145119770_1446640107_o.jpg";
    String dest_file_path = "/sdcard/dwnloaded_file.png";
    Button b1;
    ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button)findViewById(R.id.Button01);
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = ProgressDialog.show(HTTPTest.this, "", "Downloading file...", true);
                new Thread(new Runnable() {
                    public void run() {
                        downloadFile(dwnload_file_path, dest_file_path);
                    }
                }).start();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.httptest, menu);
        return true;
    }
    


    public void downloadFile(String url, String dest_file_path) {
        try {
           
            File dest_file = new File(dest_file_path);
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();
            DataInputStream stream = new DataInputStream(u.openStream());
            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();
            DataOutputStream fos = new DataOutputStream(new FileOutputStream(dest_file));
            fos.write(buffer);
            fos.flush();
            fos.close();
            hideProgressIndicator();

        } catch(FileNotFoundException e) {
            hideProgressIndicator();
            return;
        } catch (IOException e) {
            hideProgressIndicator();
            return;
        }
    }

    void hideProgressIndicator(){
        runOnUiThread(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        });
    }
}

