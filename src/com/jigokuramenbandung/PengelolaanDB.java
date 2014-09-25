package com.jigokuramenbandung;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


public class PengelolaanDB {

	private SQLiteDatabase db;
	
	public String error_create_table_ramen   = "Error Create Table Ramen";
	public String error_create_table_makanan = "Error Create Table Makanan";
	public String error_create_table_minuman = "Error Create Table Minuman";
	
	public String error_insert_table_ramen   = "Error Insert Table Ramen";
	public String error_insert_table_makanan = "Error Insert Table Makanan";
	public String error_insert_table_minuman = "Error Insert Table Minuman";
	
	public String error_update_table_ramen   = "Error Update Table Ramen";
	public String error_update_table_makanan = "Error Update Table Makanan";
	public String error_update_table_minuman = "Error Update Table Minuman";

	public String error_delete_table_ramen   = "Error Delete Table Ramen";
	public String error_delete_table_makanan = "Error Delete Table Makanan";
	public String error_delete_table_minuman = "Error Delete Table Mminuman";
	
	public String success_create_table_ramen   = "Success Create Table Ramen";
	public String success_create_table_makanan = "Success Create Table Makanan";
	public String success_create_table_minuman = "Success Create Table Minuman";
	
	public String success_insert_table_ramen   = "Success Inset Table Ramen";
	public String success_insert_table_makanan = "Success Inset Table Makanan";
	public String success_insert_table_minuman = "Success Inset Table Minuman";
	
	public String success_update_table_ramen   = "Success Update Table Ramen";
	public String success_update_table_makanan = "Success Update Table Makanan";
	public String success_update_table_minuman = "Success Update Table Minuman";

	public String success_delete_table_ramen   = "Success Delete Table Ramen";
	public String success_delete_table_makanan = "Success Delete Table Makanan";
	public String success_delete_table_minuman = "Success Delete Table Minuman";
	
	//-----------------------------------BUAT / BUKA DATABASE-------------------------------
	@SuppressLint("SdCardPath") 
	public boolean buatDatabase(String ndb){
		String dirName = "/data/data/com.jigokuramenbandung/";
		try{
			db = SQLiteDatabase.openDatabase(dirName.concat(ndb),null,SQLiteDatabase.CREATE_IF_NECESSARY);
			return true;
		}catch(SQLiteException e){
			return false;
		}
	}
	//-----------------------------------END OF BUAT / BUKA DATABASE-------------------------------
	
	
	//-----------------------------------CEK KEBERADAAN TABEL-------------------------------
	public boolean cekTable(String nama_table){
		String query = "Select name from sqlite_master where type ='table' and name='"+nama_table+"'";
		int numRow = db.rawQuery(query, null).getCount();
		if(numRow == 0){
			return true;
		}else{
			return false;
		}
	}
	//-----------------------------------END OF CEK KEBERADAAN TABEL-------------------------------
	
	
	//-----------------------------------BUAT TABEL RAMEN , MINUMAN, MAKANAN-------------------------------
	public String buatTabelRamen(){
		String retVal;
		String sql = "create table t_ramen" +
				"(id integer primary key autoincrement," +
				"nama_ramen text," +
				"nama_kuah text," +
				"level text," +
				"harga text," +
				"qty text);";
		try{
			db.execSQL(sql);
			retVal = this.success_create_table_ramen;
		}catch(SQLiteException e){
			retVal = this.error_create_table_ramen;
		}
		return retVal;
	}
	
	public String buatTabelMakanan(){
		String retVal;
		String sql = "create table t_makanan" +
				"(id integer primary key autoincrement," +
				"nama_makanan text," +
				"harga text," +
				"qty text);";
		try{
			db.execSQL(sql);
			retVal = this.success_create_table_makanan;
		}catch(SQLiteException e){
			retVal = this.error_create_table_makanan;
		}
		return retVal;
	}
	
	public String buatTabelMinuman(){
		String retVal;
		String sql = "create table t_minuman" +
				"(id integer primary key autoincrement," +
				"nama_minuman text," +
				"hot_cold text," +
				"harga text," +
				"qty text);";
		try{
			db.execSQL(sql);
			retVal = this.success_create_table_minuman;
		}catch(SQLiteException e){
			retVal = this.error_create_table_minuman;
		}
		return retVal;
	}
	//-----------------------------------END OF BUAT TABEL RAMEN , MINUMAN, MAKANAN-------------------------------
	
	
	//-----------------------------------ISI/INSERT TABEL RAMEN , MINUMAN, MAKANAN-------------------------------
	public String isiRamen(String nama_ramen,String nama_kuah,String level,String harga,String qty){
		String retVal = "";
		String sql = "insert into t_ramen " +
				"(nama_ramen,nama_kuah,level,harga,qty) " +
				" values('"+ nama_ramen +"','"+ nama_kuah +"','"+ level +"','"+ harga +"','"+ qty +"');";
		try{
			boolean iSR = isSavedRamen(nama_ramen, nama_kuah, level, harga, qty);
			if(iSR==true){
				retVal = updateQtyAvailableRamen(nama_ramen, nama_kuah, level, qty);
			}else{
				db.execSQL(sql);
				retVal= this.success_insert_table_ramen;
			} 	
		}catch(SQLiteException e){
			retVal =  this.error_insert_table_ramen;
		}
		return retVal;
	}
	
	public String isiMinuman(String nama_minuman,String hot_cold,String harga,String qty){
		String retVal;
		String sql = "insert into t_minuman " +
				"(nama_minuman,hot_cold,harga,qty) " +
				" values('"+ nama_minuman +"','"+ hot_cold +"','"+ harga +"','"+ qty +"');";
		try{
			boolean iSMi = isSavedMinuman(nama_minuman,hot_cold);
			if(iSMi==true){
				retVal = updateQtyAvailableMinuman(nama_minuman, hot_cold, qty);
			}else{
				db.execSQL(sql);
				retVal =  this.success_insert_table_minuman;
			}
		}catch(SQLiteException e){
			retVal =  this.error_insert_table_minuman;
		}
		return retVal;
	}
	
	public String isiMakanan(String nama_makanan,String harga,String qty){
		String retVal;
		String sql = "insert into t_makanan " +
				"(nama_makanan,harga,qty) " +
				" values('"+ nama_makanan +"','"+ harga +"','"+ qty +"');";
		try{
			boolean iSMa = isSavedMakanan(nama_makanan);
			if(iSMa==true){
				retVal = updateQtyAvailableMakanan(nama_makanan,qty);
			}else{
				db.execSQL(sql);
				retVal =  this.success_insert_table_makanan;
			}
		}catch(SQLiteException e){
			retVal =  this.error_insert_table_makanan;
		}
		return retVal;
	}
	//-----------------------------------END OF ISI/INSERT TABEL RAMEN , MINUMAN, MAKANAN-------------------------------
	
	
	//-----------------------------------SELECT * TABEL RAMEN , MINUMAN, MAKANAN-------------------------------
	public String[] loadRamen(){
		String sql = "select * from t_ramen";
		try{
			Cursor c = db.rawQuery(sql, null);
			int id = c.getColumnIndex("id");
			int nama_ramen = c.getColumnIndex("nama_ramen");
			int nama_kuah = c.getColumnIndex("nama_kuah");
			int level = c.getColumnIndex("level");
			int harga = c.getColumnIndex("harga");
			int qty = c.getColumnIndex("qty");
			int ctr = 0;
			String[] ret = new String[c.getCount()];
			while(c.moveToNext()){
				String current = "";
//				ret = ret + "Data ke- " + ctr + ". " + c.getInt(id) + "-" + c.getString(nama_ramen) + "-" + c.getString(nama_kuah) + "-" + c.getString(level) + "-" + c.getString(harga) + "-" + c.getString(qty) + "\n" ;
				current =  c.getInt(id) + "/" + c.getString(nama_ramen) + "/" + c.getString(nama_kuah) + "/" + c.getString(level) + "/" + c.getString(harga) + "/" + c.getString(qty) ;
				ret[ctr] = current;
				ctr++;
			}
			return ret;
		}catch(SQLiteException e){
			return null;
		}
	}
	
	public String[] loadMinuman(){
		String sql = "select * from t_minuman";
		try{
			Cursor c = db.rawQuery(sql, null);
			int id = c.getColumnIndex("id");
			int nama_minuman = c.getColumnIndex("nama_minuman");
			int hot_cold = c.getColumnIndex("hot_cold");
			int harga = c.getColumnIndex("harga");
			int qty = c.getColumnIndex("qty");
			int ctr = 0;
			String[] ret = new String[c.getCount()];
			while(c.moveToNext()){
				String current = "";
//				ret = ret + "Data ke- " + ctr + ". " + c.getInt(id) + "-" + c.getString(nama_minuman) + "-" + c.getString(hot_cold) + "-" + c.getString(harga) + "-" + c.getString(qty) + "\n" ;
				current = current + c.getInt(id) + "/" + c.getString(nama_minuman) + "/" + c.getString(hot_cold) + "/" + c.getString(harga) + "/" + c.getString(qty) + "\n" ;
				ret[ctr] = current;
				ctr++;
			}
			return ret;
		}catch(SQLiteException e){
			return  null;
		}
	}

	public String[] loadMakanan(){
		String sql = "select * from t_makanan";
		try{
			Cursor c = db.rawQuery(sql, null);
			int id = c.getColumnIndex("id");
			int nama_makanan = c.getColumnIndex("nama_makanan");
			int harga = c.getColumnIndex("harga");
			int qty = c.getColumnIndex("qty");
			int ctr = 0;
			String[] ret = new String[c.getCount()];
			while(c.moveToNext()){
				String current = "";
				current = current +  c.getInt(id) + "/" + c.getString(nama_makanan) + "/" + c.getString(harga) + "/" + c.getString(qty) + "\n" ;
				ret[ctr] = current;
				ctr++;
			}
			return ret;
		}catch(SQLiteException e){
			return  null;
		}
	}
	//-----------------------------------END OF SELECT * TABEL RAMEN , MINUMAN, MAKANAN-------------------------------
	
	
	//-----------------------------------IS DATA AVAILABLE ?  TABEL RAMEN , MINUMAN, MAKANAN-------------------------------
	public boolean isSavedRamen(String nama_ramen,String nama_kuah,String level,String harga,String qty){
		String sql = "select * from t_ramen " +
				"where " +
				" nama_ramen  = '"+ nama_ramen +"' and " +
				" nama_kuah = '"+ nama_kuah +"' and " +
				" level = '"+ level +"'";
		int numRow = db.rawQuery(sql, null).getCount();
		boolean retVal = true;
		if(numRow==1){
			retVal = true;
		}else
	    if(numRow==0){
	    	retVal = false;
		}
		return retVal;
	}
	
	public boolean isSavedMinuman(String nama_minuman, String hot_cold){
		String sql = "select * from t_minuman " +
				"where " +
				" nama_minuman  = '"+ nama_minuman + "' and " + 
				" hot_cold ='" + hot_cold + "'";
		int numRow = db.rawQuery(sql, null).getCount();
		boolean retVal = true;
		if(numRow==1){
			retVal = true;
		}else
	    if(numRow==0){
	    	retVal = false;
		}
		return retVal;
	}

	public boolean isSavedMakanan(String nama_makanan){
		String sql = "select * from t_makanan " +
				"where " +
				" nama_makanan = '"+ nama_makanan +"'";
		int numRow = db.rawQuery(sql, null).getCount();
		boolean retVal = true;
		if(numRow==1){
			retVal = true;
		}else
	    if(numRow==0){
	    	retVal = false;
		}
		return retVal;
	}
	//-----------------------------------END OF IS DATA AVAILABLE ?  TABEL RAMEN , MINUMAN, MAKANAN-------------------------------

	
	//-----------------------------------SELECT QTY IS AVAILABLE--------------------------------------------
	public int selectQtyAvailableRamen(String nama_ramen,String nama_kuah,String level){
		String sql = "select * from t_ramen where " +
				"nama_ramen = '" + nama_ramen + "' and " +
				"nama_kuah = '" + nama_kuah + "' and " +
				"level = '" + level + "'";
		try{
			//String q = "0";
			int q = 0;
			Cursor c = db.rawQuery(sql, null);
			int qty = c.getColumnIndex("qty");
			while(c.moveToNext()){
				q = c.getInt(qty);
			}
			return q;
		}catch(SQLiteException e){
			return 0;
		}
	}
	
	public int selectQtyAvailableMinuman(String nama_minuman){
		String sql = "select * from t_minuman where " +
				"nama_minuman = '" + nama_minuman + "'";
		try{
			//String q = "0";
			int q = 0;
			Cursor c = db.rawQuery(sql, null);
			int qty = c.getColumnIndex("qty");
			while(c.moveToNext()){
				q = c.getInt(qty);
			}
			return q;
		}catch(SQLiteException e){
			return 0;
		}
	}
	
	public int selectQtyAvailableMakanan(String nama_makanan){
		String sql = "select * from t_makanan where " +
				"nama_makanan = '" + nama_makanan + "'";
		try{
			//String q = "0";
			int q = 0;
			Cursor c = db.rawQuery(sql, null);
			int qty = c.getColumnIndex("qty");
			while(c.moveToNext()){
				q = c.getInt(qty);
			}
			return q;
		}catch(SQLiteException e){
			return 0;
		}
	}
	//-----------------------------------END OF QTY QTY AVAILABLE--------------------------------------------
	
	
	
	
	//-----------------------------------UPDATE IS AVAILABLE--------------------------------------------
	public String updateQtyAvailableRamen(String nama_ramen,String nama_kuah,String level,String qty){
		int qty_new = Integer.parseInt(qty);
		int current_qty = selectQtyAvailableRamen(nama_ramen,nama_kuah,level);
		int to_save_qty = qty_new + current_qty;
		String retVal;
		
		String sql = "update t_ramen set " +
				"qty = '" + to_save_qty + "' where " +
				"nama_ramen = '" + nama_ramen + "' and " +
				"nama_kuah = '" + nama_kuah + "' and " +
				"level = '" + level + "'";
		try{
			db.execSQL(sql);
			retVal =  this.success_update_table_ramen;
			
		}catch(SQLiteException e){
			retVal =  this.error_update_table_ramen;
		}
		return retVal;
	}
	
	public String updateQtyAvailableMinuman(String nama_minuman,String hot_cold,String qty){
		int qty_new = Integer.parseInt(qty);
		int current_qty = selectQtyAvailableMinuman(nama_minuman);
		int to_save_qty = qty_new + current_qty;
		String retVal;
		
		String sql = "update t_minuman set " +
				"qty = '" + to_save_qty + "' where " +
				"nama_minuman = '" + nama_minuman + "' and " +
				"hot_cold = '" + hot_cold + "'";
		try{
			db.execSQL(sql);
			retVal =  this.success_update_table_minuman;
			
		}catch(SQLiteException e){
			retVal =  this.error_update_table_minuman;
		}
		return retVal;
	}
	
	public String updateQtyAvailableMakanan(String nama_makanan,String qty){
		int qty_new = Integer.parseInt(qty);
		int current_qty = selectQtyAvailableMakanan(nama_makanan);
		int to_save_qty = qty_new + current_qty;
		String retVal;
		String sql = "update t_makanan set " +
				"qty = '" + to_save_qty + "' where " +
				"nama_makanan = '" + nama_makanan + "'";
		try{
			db.execSQL(sql);
			retVal =  this.success_update_table_makanan;
		}catch(SQLiteException e){
			retVal =  this.error_update_table_makanan;
		}
		return retVal;
	}
	//---------------------------------------------------------------------------------------------------	
	
	

	
	//-----------------------------------UPDATE IS AVAILABLE--------------------------------------------
	public String updatePesananRamen(String nama_ramen,String nama_kuah,String level,String qty){
		int qty_new = Integer.parseInt(qty);
		String retVal;
		String sql = "update t_ramen set " +
				"qty = '" + qty_new + "' where " +
				"nama_ramen = '" + nama_ramen + "' and " +
				"nama_kuah = '" + nama_kuah + "' and " +
				"level = '" + level + "'";
		try{
			db.execSQL(sql);
			retVal =  this.success_update_table_ramen;
		}catch(SQLiteException e){
			retVal =  this.error_update_table_ramen;
		}
		return retVal;
	}
	//--------------------------------------------------------------------------------------------------
	
	
	//-----------------------------------DELETE IS AVAILABLE--------------------------------------------
	public String deletePesananRamen(String namaRamen,String namaKuah,String level,String qty){
		String retVal;
		String sql = "delete from t_ramen where " +
					"qty = '" + qty + "' and " +
					"nama_ramen = '" + namaRamen + "' and " +
					"nama_kuah = '" + namaKuah + "' and " +
					"level = '" + level + "'";
		try{
			db.execSQL(sql);
			retVal =  this.success_delete_table_ramen;
		}catch(SQLiteException e){
			retVal =  this.error_delete_table_ramen;
		}
		return retVal;
	}

	//--------------------------------------------------------------------------------------------------
	
	
}
