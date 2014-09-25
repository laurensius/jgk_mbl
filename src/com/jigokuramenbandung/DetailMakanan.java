package com.jigokuramenbandung;

import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.*;

@SuppressLint("SetJavaScriptEnabled") 
public class DetailMakanan extends Activity {
	
	private WebView menu_webview;
	private JavaScriptInterface JSInterface;
	private SharedPreferences jigokuPreferences;
	private Editor jigokuPreferencesEditor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.detailmakanan_activity);
	
		jigokuPreferences = getApplicationContext().getSharedPreferences("Jigoku_Ramen_Preferences", 0); 	
		jigokuPreferencesEditor = jigokuPreferences.edit();
		
		int id;
		Intent i = getIntent();
		id = i.getIntExtra("id",0);
		
		menu_webview = (WebView)findViewById(R.id.menu_webview);
		menu_webview.getSettings().setJavaScriptEnabled(true);
		menu_webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		menu_webview.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url){}
	        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){}
	    });
		JSInterface = new JavaScriptInterface(this);
		menu_webview.addJavascriptInterface(JSInterface, "JSInterface");
		menu_webview.loadUrl(getResources().getString(R.string.server).toString() + "detail_makanan/" + id);
	}
	
	@Override
	public void onBackPressed(){
		Intent i = new Intent(getApplicationContext(),ListMakanan.class);
		startActivity(i);
		finish();
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
	

	class JavaScriptInterface{
	    Context mContext;
	    PengelolaanDB myDB = new PengelolaanDB();
	    
	    JavaScriptInterface(Context c) {
	        mContext = c;
	    }
	    
	    public void terimaPesananRamen(String jsi_nama_ramen,String jsi_nama_kuah,String jsi_level,String jsi_harga,String jsi_qty){
	    	String ndb = jigokuPreferences.getString("nama_database", "");
	    	myDB.buatDatabase(ndb);
	    }
	}
	
}

/*
 * http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences/
 */







