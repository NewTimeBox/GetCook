package com.newtimebox.getcook.helpers.VolleyConfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.volley.toolbox.HurlStack;

import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLSocketFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by User on 16.07.2017.
 */

public class MyHurlStack extends HurlStack {
    public String CookieFileName = "CookieStore";
    static final String COOKIES_HEADER = "Set-Cookie";
    HttpURLConnection connection = null ;
    SharedPreferences someData;
    Context CurrentContext;

    static java.net.CookieManager msCookieManager = new java.net.CookieManager();
    //https://stackoverflow.com/questions/3398363/how-to-define-callbacks-in-android burdaki terzde aca bilerler
    //a = new x(){
    // sonra methodlar amma indilik bele qalsin}
    //Cookie management burda getmelidi

    public MyHurlStack(){
        super(null);
    }
    public MyHurlStack(UrlRewriter urlRewriter) {
        super(urlRewriter, null);
    }

    public MyHurlStack(UrlRewriter urlRewriter, SSLSocketFactory sslSocketFactory) {
         super(urlRewriter,sslSocketFactory);
    }

    public void setContext(Context context){
        CurrentContext=context;
    }


    @Override
    public HttpURLConnection getConnectionPreExecute(HttpURLConnection connection) {
        someData = CurrentContext.getSharedPreferences(CookieFileName,MODE_PRIVATE);
        Set<String> got_set = someData.getStringSet("cookies", null);
        List<String> cookie_list=new ArrayList<String>();
        if(got_set!=null){
            cookie_list = new ArrayList<String>(got_set);
            if(cookie_list!=null){
                for (String cookie : cookie_list) {
                    //Log.i("info",cookie);
                    msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }
        }

        if (msCookieManager.getCookieStore().getCookies().size() > 0) {
            // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
            connection.setRequestProperty("Cookie",
                    TextUtils.join(";",  msCookieManager.getCookieStore().getCookies()));
        }
        return connection;
    }

    @Override
    public HttpURLConnection getConnectionPostExecute(HttpURLConnection connection) {
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

        //someData = getSharedPreferences("Cookiestore",MODE_PRIVATE);
        if(cookiesHeader!=null){
            Set<String> set = new HashSet<String>();
            set.addAll(cookiesHeader);
            SharedPreferences.Editor editor = someData.edit();
            editor.putStringSet("cookies", set);
            editor.commit();


            if (cookiesHeader != null) {
                //Log.i("info",cookiesHeader.toString());
                for (String cookie : cookiesHeader) {
                  //  Log.i("info",cookie);
                    msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }
        }
        return connection;
    }
}
