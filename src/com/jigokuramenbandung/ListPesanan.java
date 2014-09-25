package com.jigokuramenbandung;

import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled") 
public class ListPesanan extends ListActivity{
	
	private SharedPreferences jigokuPreferences;
	private Editor jigokuPreferencesEditor;
	private PengelolaanDB myDB = new PengelolaanDB();
	
	 char idRamen[] = new char[99];
     char namaRamen[] = new char[99];
     char namaKuah[] = new char[99];
     char level[] = new char[99];
     char harga[] = new char[99];
     char qty[] = new char[99];
	
     int ketemu = 0,cctr=0;
     
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        //String ndb = jigokuPreferences.getString("nama_database", zero);
		Intent i = getIntent();
        String ndb = i.getStringExtra("ndb");
//        Toast.makeText(getApplicationContext(), ndb, Toast.LENGTH_SHORT).show();
        myDB.buatDatabase(ndb);
        final String ram[] = myDB.loadRamen();
        final String min[] = myDB.loadMinuman();
        final String mak[] = myDB.loadMakanan();
        int allPesanan_Length = ram.length + min.length + mak.length;
        String allPesanan[] = new String[allPesanan_Length];
        
        int init = 0;
        for(int x=0;x<ram.length;x++){
        	allPesanan[init] = ram[x];
        	init++;
        }
        for(int x=0;x<min.length;x++){
        	allPesanan[init] = min[x];
        	init++;
        }
        for(int x=0;x<mak.length;x++){
        	allPesanan[init] = mak[x];
        	init++;
        }

        
        
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.listpesanan_activity, R.id.label, allPesanan));
        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
            	String product = ((TextView) view).getText().toString();
                if(position >= 0 && position < ram.length){
            		//keEditRamen(product);
                	Toast.makeText(getApplicationContext(), "Ke Edit Ramen", Toast.LENGTH_SHORT).show();
            	}else
            	if(position >= ram.length && position < ram.length + min.length){
            		Toast.makeText(getApplicationContext(), "Ke Edit Minuman", Toast.LENGTH_SHORT).show();	
                }else
                if(position >= ram.length + min.length && position < ram.length + min.length + mak.length){
                	Toast.makeText(getApplicationContext(), "Ke Edit Makanan", Toast.LENGTH_SHORT).show();	
                }
            }
        });
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		Intent i = new Intent(getApplicationContext(),MenuActivity.class);
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
	
	public void keEditRamen(String product){
		Intent i  = new Intent(getApplicationContext(),EditRamen.class);
		String str = getResources().getString(R.string.server).toString();
		String go = str.concat("edit_ramen/").concat(product);
		i.putExtra("product", go);
		startActivity(i);
		finish();
	}
	

	class JavaScriptInterface{
	    Context mContext;
	
	    JavaScriptInterface(Context c) {
	        mContext = c;
	    }
	        
	}
	
}

/*
 * http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences/
 */







