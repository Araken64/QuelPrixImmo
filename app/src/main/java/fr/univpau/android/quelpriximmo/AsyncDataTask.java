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
        URLConnection urlConn = null;
        BufferedReader bufferedReader = null;
        try
        {
            URL url = params[0];
            Log.i("RES", url.toString());
            urlConn = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null)
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
