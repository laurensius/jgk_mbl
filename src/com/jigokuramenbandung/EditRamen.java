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
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled") 
public class EditRamen extends Activity {
	
	private WebView menu_webview;
	private JavaScriptInterface JSInterface;
	private SharedPreferences jigokuPreferences;
	private Editor jigokuPreferencesEditor;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.editramen_activity);
	
		jigokuPreferences = getApplicationContext().getSharedPreferences("Jigoku_Ramen_Preferences", 0); 	
		jigokuPreferencesEditor = jigokuPreferences.edit();
//		
		Intent i = getIntent();
		Toast.makeText(getApplicationContext(), i.getStringExtra("product"), Toast.LENGTH_SHORT).show();
		menu_webview = (WebView)findViewById(R.id.menu_webview);
		menu_webview.getSettings().setJavaScriptEnabled(true);
		menu_webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		menu_webview.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url){}
	        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){}
	    });
		JSInterface = new JavaScriptInterface(this);
		menu_webview.addJavascriptInterface(JSInterface, "JSInterface");	
		menu_webview.loadUrl(i.getStringExtra("product"));
		
		//Toast.makeText(getApplicationContext(), menu_webview.getUrl().toString(),Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onBackPressed(){
		Intent i = new Intent(getApplicationContext(),ListPesanan.class);
		i.putExtra("ndb", jigokuPreferences.getString("nama_database", ""));
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
	    
	    public void updatePesananRamen(String namaRamen,String namaKuah,String level,String qty,String jsi_nama_kuah,String jsi_level,String jsi_harga,String jsi_qty){
	    	String res;
	    	String ndb = jigokuPreferences.getString("nama_database", "");
	    	myDB.buatDatabase(ndb);
	    	if(namaKuah.equals(jsi_nama_kuah)){
	    		res  = myDB.updatePesananRamen(namaRamen, namaKuah, jsi_level, jsi_qty);
	    	}else{
	    		res = myDB.isiRamen(namaRamen, jsi_nama_kuah, jsi_level, jsi_harga, jsi_qty);
	    		res = myDB.deletePesananRamen(namaRamen, namaKuah, level, qty);
	    	}
	    	Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
	    }
	}
	
}

/*
 * http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences/
 */







