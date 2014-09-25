package com.jigokuramenbandung;

import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.*;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint({ "SetJavaScriptEnabled", "SimpleDateFormat", "SdCardPath" }) 
public class MenuActivity extends Activity {
	
	
	private Dialog dialogExit;
	private Button btnExitYa,btnExitTidak;
	private WebView menu_webview;
	private JavaScriptInterface JSInterface;
	private SharedPreferences jigokuPreferences;
	private Editor jigokuPreferencesEditor;
	private String zero = "zero_no_result_aka_null";
	private PengelolaanDB myDB = new PengelolaanDB();
	public String ndb;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.menu_activity);
	
		jigokuPreferences = getApplicationContext().getSharedPreferences("Jigoku_Ramen_Preferences", 0); 	
		jigokuPreferencesEditor = jigokuPreferences.edit();
		
		ndb = jigokuPreferences.getString("nama_database", zero);
//        Toast.makeText(getApplicationContext(), ndb, Toast.LENGTH_SHORT).show();
		try{
			myDB.buatDatabase(ndb);
//			boolean ramen = myDB.cekTable("t_ramen");
//			boolean minuman = myDB.cekTable("t_minuman");
//			boolean makanan = myDB.cekTable("t_makanan");
//			if(ramen==true){
//				Toast.makeText(getBaseContext(), myDB.buatTabelRamen(), Toast.LENGTH_SHORT).show();
//			}else{
//				Toast.makeText(getBaseContext(), "Tabel ramaen sudah ada !", Toast.LENGTH_SHORT).show();
//			}
//			if(makanan==true){
//				Toast.makeText(getBaseContext(), myDB.buatTabelMakanan(), Toast.LENGTH_SHORT).show();
//			}else{
//				Toast.makeText(getBaseContext(), "Tabel makanan sudah ada !", Toast.LENGTH_SHORT).show();
//			}
//			if(minuman==true){
//				Toast.makeText(getBaseContext(), myDB.buatTabelMinuman(), Toast.LENGTH_SHORT).show();
//			}else{
//				Toast.makeText(getBaseContext(), "Tabel minuman sudah ada !", Toast.LENGTH_SHORT).show();
//			}
			myDB.buatTabelRamen();
			myDB.buatTabelMakanan();
			myDB.buatTabelMinuman();
		}catch(SQLiteException e){
			Toast.makeText(getBaseContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
		}finally{}
	
		menu_webview = (WebView)findViewById(R.id.menu_webview);
		menu_webview.getSettings().setJavaScriptEnabled(true);
		menu_webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		menu_webview.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url){}
	        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){}
	    });
		JSInterface = new JavaScriptInterface(this);
		menu_webview.addJavascriptInterface(JSInterface, "JSInterface");
		menu_webview.loadUrl(getResources().getString(R.string.local_menu).toString());
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
    	dialogExit = new Dialog(MenuActivity.this);
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
	
	class JavaScriptInterface{
	    Context mContext;
	 
	    JavaScriptInterface(Context c) {
	        mContext = c;
	    }
	    
	    public void keListRamen(){
	    	Intent i = new Intent(getApplicationContext(),ListRamen.class);
	    	startActivity(i);
	    	finish();
	    }
	    
	    public void keListMinuman(){
	    	Intent i = new Intent(getApplicationContext(),ListMinuman.class);
	    	startActivity(i);
	    	finish();
	    }
	    
	    public void keListMakanan(){
	    	Intent i = new Intent(getApplicationContext(),ListMakanan.class);
	    	startActivity(i);
	    	finish();
	    }
	    
	    public void keListPesanan(){
	    	Intent i = new Intent(getApplicationContext(),ListPesanan.class);
	    	i.putExtra("ndb", ndb);
	    	startActivity(i);
	    	finish();
	    }
	    
	    public void deletePreferences(){
	    	hapusSharedPreferences();
	    	Intent i = new Intent(getApplicationContext(),SplashActivity.class);
	    	startActivity(i);
	    	finish();
	    }
	}
}

/*
 * http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences/
 */







