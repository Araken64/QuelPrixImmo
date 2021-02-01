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
        URL url;
        URLConnection urlConn;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        String line;

        try
        {
            if (params.length == 1) {
                url = params[0];
                url.openConnection();
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }
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
            if(bufferedReader != null)
            {
                try {
                    bufferedReader.close();
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
