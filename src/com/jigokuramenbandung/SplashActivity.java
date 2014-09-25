package com.jigokuramenbandung;

import java.text.SimpleDateFormat;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.*;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled") 
public class SplashActivity extends Activity {
	
	
	private Dialog dialogExit;
	private Button btnExitYa,btnExitTidak;
	private WebView menu_webview;
	private JavaScriptInterface JSInterface;
	private SharedPreferences jigokuPreferences;
	private Editor jigokuPreferencesEditor;
	
	String zero = "zero_no_result_aka_null";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash_activity);
	
		jigokuPreferences = getApplicationContext().getSharedPreferences("Jigoku_Ramen_Preferences", 0); 	
		jigokuPreferencesEditor = jigokuPreferences.edit();
		
		String nama_konsumen = jigokuPreferences.getString("nama_konsumen", zero);
		
		if(nama_konsumen.equals(zero)){
			Toast.makeText(getApplicationContext(), "Hai, Selamat datang di Jigoku Ramen", Toast.LENGTH_SHORT).show();
			menu_webview = (WebView)findViewById(R.id.menu_webview);
			menu_webview.getSettings().setJavaScriptEnabled(true);
			menu_webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
			menu_webview.setWebViewClient(new WebViewClient() {
				public void onPageFinished(WebView view, String url){}
		        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){}
		    });
			JSInterface = new JavaScriptInterface(this);
			menu_webview.addJavascriptInterface(JSInterface, "JSInterface");
			menu_webview.loadUrl(getResources().getString(R.string.local_splash).toString());
		}else{
			Intent i = new Intent(getApplicationContext(),MenuActivity.class);
			startActivity(i);
			finish();
		}
	}
	
	@Override
	public void onBackPressed(){
		menuKeluarAplikasi();
	}
	
	public void simpanSharedPreferences(String key,String value){
		jigokuPreferencesEditor.putString(key, value);
		jigokuPreferencesEditor.commit();
	}
	
	public void hapusSharedPreferences(){
		Editor jigokuPreferencesEditor = jigokuPreferences.edit();
		jigokuPreferencesEditor.clear();
		jigokuPreferencesEditor.commit();
		}
	
	public String ambilSharedPreferences(){
		Map<String,?> keys = jigokuPreferences.getAll();
		String sf="";
		for(Map.Entry<String,?> entry : keys.entrySet()){
			entry.getKey();
			sf = sf.concat("Key : " + entry.getKey() + " value : " + entry.getValue()).concat("\n");
		}
		return sf;
	}
	
	
	public void menuKeluarAplikasi(){
    	dialogExit = new Dialog(SplashActivity.this);
		dialogExit.setContentView(R.layout.dialogexit_activity);
		dialogExit.setTitle("Jigoku Ramen Bandung");
		btnExitYa = (Button) dialogExit.findViewById(R.id.btnExitYa);
		btnExitTidak = (Button) dialogExit.findViewById(R.id.btnExitTidak);
		dialogExit.show();
		btnExitYa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnExitTidak.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogExit.dismiss();
			}
		});
    }
	
	@SuppressLint("SimpleDateFormat") class JavaScriptInterface{
	    Context mContext;
	 
	    JavaScriptInterface(Context c) {
	        mContext = c;
	    }
	    
	    public void keListRamen(){
	    	Intent i = new Intent(getApplicationContext(),ListRamen.class);
	    	startActivity(i);
	    	finish();
	    }
	    
	    public void savePreferences(String nama){
	    	String nk = nama.replace(" ", "_");
			SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
			String ndb = sdf.toString().concat("__").concat(nk);
			
			simpanSharedPreferences("nama_konsumen",nama);
			simpanSharedPreferences("nama_database",ndb);
			
			
	    	Intent i = new Intent(getApplicationContext(),MenuActivity.class);
	    	startActivity(i);
	    	finish();
	    }
	    
	    public void deletePreferences(){
	    	hapusSharedPreferences();
	    }
	}
}

/*
 * http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences/
 */







