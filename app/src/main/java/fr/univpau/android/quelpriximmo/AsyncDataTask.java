package fr.univpau.android.quelpriximmo;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class AsyncDataTask extends AsyncTask<URL, Void, JSONObject>
{
    @Override
    protected JSONObject doInBackground(URL... params)
    {
        URLConnection urlConn1 = null;
        URLConnection urlConn2 = null;
        BufferedReader bufferedReader1 = null;
        BufferedReader bufferedReader2 = null;
        try
        {
            URL url1 = params[0];
            URL url2 = params[1];
            urlConn1 = url1.openConnection();
            urlConn2 = url2.openConnection();
            bufferedReader1 = new BufferedReader(new InputStreamReader(urlConn1.getInputStream()));
            bufferedReader2 = new BufferedReader(new InputStreamReader(urlConn2.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader1.readLine()) != null)
            {
                stringBuffer.append(line);
            }
            while ((line = bufferedReader2.readLine()) != null)
            {
                stringBuffer.append(line);
            }
            return new JSONObject(stringBuffer.toString());
        }
        catch(Exception ex)
        {
            Log.e("RES", "yourDataTask", ex);
            return null;
        }
        finally
        {
            if(bufferedReader1 != null && bufferedReader2 != null)
            {
                try {
                    bufferedReader1.close();
                    bufferedReader2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPostExecute(JSONObject response)
    {

    }
}
