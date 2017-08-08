package com.newtimebox.getcook.helpers;

import android.content.Context;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.newtimebox.getcook.helpers.VolleyConfig.MyHurlStack;
import com.newtimebox.getcook.interfaces.ICallback;

import java.util.Map;


/**
 * Created by User on 27.01.2017.
 */


public class FullRequest extends StringRequest {

    public static String primaryDomain = "http://newtimebox.com/subdomains/getcook/";
   // private static final String REGISTER_REQUEST_URL = "";
    private Map<String , String> params;
    private Context context;
   // private static final String COOKIES_HEADER = "Set-Cookie";

    //public RegisterRequest(String name, String username, int age, String password, Response.Listener<String> listener){
    //,success_func,error_func,complete_func,upload_file
    public FullRequest(String page, Map<String, String> data, int method, Response.Listener<String> success_func,Response.ErrorListener errorListener){
        //heleki methoddan asili deyil
        super(method,page,success_func,errorListener);
        params = data;
        // params.put("log_mail",username);
        //params.put("log_pass",password)

    }
    public FullRequest( ){
        //params yoxdursa hecne etmir
        super(Method.POST,null,null,null);
        //heleki methoddan asili deyil

        // params.put("log_mail",username);
        //params.put("log_pass",password)

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }


    public void startasd(View.OnClickListener Activity_contect) {
        // RequestQueue queue = Volley.newRequestQueue((Context));
        //queue.add(this);
    }


    public FullRequest setContext(Context context){
        this.context = context;
        return this;
    }

    public FullRequest setRequest(String page, Map<String, String> data, String type, Response.Listener<String> success_func,Response.ErrorListener errorListener){
        //heleki methoddan asili deyil
        return this.setRequest(page,data,type,success_func,errorListener).send();
    }
//below functions have 2 mod 1) with hashmap,2 is {"key:value"}, 1 can be depricated in future
    public FullRequest send_request(String page, Map<String, String> data, String type, final ICallback success_func){
        return send_request(page,data,type ,success_func,null);
    }

    //asagidaki 2. usul iledi
    public FullRequest send_request(String page, String[] data, String type, final ICallback success_func){
        return send_request(page,TypeConverter.StringToMapString(data),type,success_func);
    }
    //asagidaki 2ci usul ile error callback
    public FullRequest send_request(String page, String[]  data, String type, final ICallback success_func, final ICallback error_func){
        return send_request(page,TypeConverter.StringToMapString(data),type,success_func,error_func);
    }

    //asagidaki 2ci usul ile no callback
    public FullRequest send_request(String page, String[]  data, String type){
        return send_request(page,TypeConverter.StringToMapString(data),type,null,null);
    }

    public FullRequest send_request(String page, Map<String, String> data, String type, final ICallback success_func, final ICallback error_func){

        params = data;
        //ozunden birdene de yaradib request yolladir ona
        //"http://www.newtimebox.com//Controller/login_cont.php"
        type = type.toLowerCase();
        int method = Method.POST;
        if(type.contentEquals("get")){
            //
            method = Method.GET;
        }else if(type.contentEquals("post")){
            //
            method = Method.POST;
        }
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(success_func!=null){
                    success_func.call(response);
                }

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error_func!=null){
                    error_func.call(error.toString());
                }
            }
        };
        FullRequest requester =  new FullRequest(page,params,method,responseListener,errorListener);
        requester.setContext(this.context).send();
        return requester;
    }

    public FullRequest send(){
        //asagidaki linkler session sharing ucundu
        //https://stackoverflow.com/questions/5960832/maintaining-session-in-android-application-stay-authenticated-on-the-server-si/38473193#38473193
        //https://stackoverflow.com/questions/16680701/using-cookies-with-android-volley-library
        MyHurlStack myHurlStack = new MyHurlStack();
        myHurlStack.setContext(this.context);
        RequestQueue queue =  Volley.newRequestQueue(this.context,myHurlStack);
        queue.add(this);
        return this;
    }






}
