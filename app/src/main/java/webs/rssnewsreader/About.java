package webs.rssnewsreader;

import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        String[] data =new String[]{"331345","330924","331189","331439","331346"};
        String result1,result2,result3,result4,result5;
        result1 = parsingHTML(data[0]);
        result2 = parsingHTML(data[1]);
        result3 = parsingHTML(data[2]);
        result4 = parsingHTML(data[3]);
        result5 = parsingHTML(data[4]);
        TextView anggota1 = (TextView)findViewById(R.id.nama1);
        anggota1.setText(result1);
        TextView anggota2 = (TextView)findViewById(R.id.nama2);
        anggota2.setText(result2);
        TextView anggota3 = (TextView)findViewById(R.id.nama3);
        anggota3.setText(result3);
        TextView anggota4 = (TextView)findViewById(R.id.nama4);
        anggota4.setText(result4);
        TextView anggota5 = (TextView)findViewById(R.id.nama5);
        anggota5.setText(result5);
    }

    private String parsingHTML(String NIU){
        String hasil=null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            URL url = new URL("http://akademik.ugm.ac.id/2013/home.php?ma=profil&ms=mhs_profile");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setInstanceFollowRedirects(true);  
            HttpURLConnection.setFollowRedirects(true);
            boolean redirect = false;
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("key", NIU)
                    .appendQueryParameter("ma", "profil")
                    .appendQueryParameter("ms", "mhs_profile")
                    .appendQueryParameter("cari", "cari");
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
            BufferedReader buff = new BufferedReader(in);
            StringBuffer text = new StringBuffer();
            String line;
            do {
                line = buff.readLine();
                text.append(line + "\n");
            } while (line != null);
            String teks = text.toString();
            Document html = Jsoup.parse(teks);
            hasil = html.body().getElementsByTag("td").text();
            conn.connect();
            writer.close();
            os.close();
        } catch (MalformedURLException e){

        } catch (ProtocolException e){

        } catch (IOException e){

        }
        return hasil;
    }
}
