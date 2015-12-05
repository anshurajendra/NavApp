package com.example.navapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class locationdb {
	
		public static final String KEY_ROWID = "_id";
		public static final String KEY_LOCATIONNAME = "locationname";
		public static final String KEY_COORDINATES_X = "x";
		public static final String KEY_COORDINATES_Y = "y";
	//	public static final String KEY_REFERENCECOUNTER = "ref";
		private static final String DATABASE_NAME = "LOCATION";
		private static final String DATABASE_TABLE = "DESTLOCATION";
		private static final int DATABASE_VERSION = 1;
		private DbHelper ourHelper;
		private final Context ourContext;
		private SQLiteDatabase ourdatabase;

		private static class DbHelper extends SQLiteOpenHelper {
			public DbHelper(Context context) {
				super(context, DATABASE_NAME, null, DATABASE_VERSION);
			}

			@Override
			public void onCreate(SQLiteDatabase db) {
				db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ROWID
						+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_LOCATIONNAME
						+ " TEXT NOT NULL ," 
						+ KEY_COORDINATES_X + " REAL ," + KEY_COORDINATES_Y
						+ " REAL); ");
			}

			@Override
			// For subsequent calls to DB, on Upgrade is used
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				// TODO Auto-generated method stub
				db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
				onCreate(db);
			}
		}

		public locationdb(Context c) {
			ourContext = c;
		}

		public locationdb open() throws SQLException {
			ourHelper = new DbHelper(ourContext);
			ourdatabase = ourHelper.getWritableDatabase();
			return this;
		}

		public void close() {
			ourHelper.close();
		}

		public int createentry(String name, Position pos ) {
			// TODO Auto-generated method stub
			ContentValues cv = new ContentValues();
			cv.put(KEY_LOCATIONNAME, name);
			cv.put(KEY_COORDINATES_X, pos.getPosX());
			cv.put(KEY_COORDINATES_Y, pos.getPosY());
			return (int) ourdatabase.insert(DATABASE_TABLE, null, cv);
		}

		public String[] getdestsuggestion() {
			String[] result;
			Cursor cursor = ourdatabase.query(true, DATABASE_TABLE,
					new String[] { KEY_LOCATIONNAME }, null, null, null, null,
					null, null);
			int count = 0;
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				count++;

			}
			result = new String[count];
			int i = 0;
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				result[i] = cursor
						.getString(cursor.getColumnIndex(KEY_LOCATIONNAME));
				i++;
			}

			return result;

		}

	/*	public int getclicks(String loc) {
			int result;
			String[] columns = new String[] { KEY_REFERENCECOUNTER };
			Cursor c = ourdatabase.query(DATABASE_TABLE, columns, KEY_DESTINATION
					+ " = '" + loc + "'", null, null, null, null);
			if ((c.getCount() == 0) || !c.moveToFirst()) {
				result = 0;
			}
			result = c.getInt(c.getColumnIndex(KEY_REFERENCECOUNTER));
			return result;

		}
		public String getloc(String loc) {
			String result="";
			String[] columns = new String[] { KEY_DESTINATION };
			Cursor c = ourdatabase.query(DATABASE_TABLE, columns, KEY_DESTINATION
					+ " = '" + loc + "'", null, null, null, null);
			if ((c.getCount() == 0) || !c.moveToFirst()) {
				return result;
			}
			else
			result = c.getString(c.getColumnIndex(KEY_DESTINATION));
			
			return result;

		}

	    public boolean tablecheck()
	    {
	      boolean result = false;
		  String[] columns = new String[] { KEY_ROWID };
		  Cursor c = ourdatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		 if(c!=null && c.getCount()>0)
		 {
			 result = true;
		 }
	          return result;
	    }*/
	    
		public Position getlocation(String lname) {
			Position result;
			Cursor cursor = ourdatabase.query(true, DATABASE_TABLE, new String[] {
					KEY_COORDINATES_X, KEY_COORDINATES_Y }, KEY_LOCATIONNAME
					+ " = " + "'" + lname + "'", null, null, null, null, null);
			if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
				result = new Position(-1, -1);
			}
			result = new Position(cursor.getDouble(cursor
					.getColumnIndex(KEY_COORDINATES_X)), cursor.getDouble(cursor
					.getColumnIndex(KEY_COORDINATES_Y)));
			return result;

		}

	/*	public void updateonclicks(String dest, int n, LatLng loc) {
			ContentValues cv = new ContentValues();
			cv.put(KEY_DESTINATION, dest);
			cv.put(KEY_REFERENCECOUNTER, n);
			cv.put(KEY_COORDINATES_LAT, loc.latitude);
			cv.put(KEY_COORDINATES_LON, loc.longitude);
			ourdatabase.update(DATABASE_TABLE, cv, KEY_DESTINATION + " = '" + dest
					+ "'", null);
		}*/

	}

