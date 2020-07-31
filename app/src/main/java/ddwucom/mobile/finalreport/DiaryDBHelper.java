package ddwucom.mobile.finalreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DiaryDBHelper extends SQLiteOpenHelper {
    final static String TAG = "DiaryDBHelper";
    final static String DB_NAME = "diary.db";
    public final static String TABLE_NAME = "diary_table";
    public final static String COL_ID = "_id";
    public final static String COL_NATION = "nation";
    public final static String COL_PLACE = "place";
    public final static String COL_DATE = "date";
    public final static String COL_TIME = "time";

    public DiaryDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
                COL_NATION + " TEXT, " + COL_PLACE + " TEXT, " + COL_DATE + " TEXT, " + COL_TIME + " TEXT)";
        Log.d(TAG, sql);
        db.execSQL(sql);

        db.execSQL("insert into " + TABLE_NAME + " values (null, 'Spain', 'Sevile', '2019년7월13일', '12:00');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, 'Praha', 'Cheski', '2019년8월1일', '07:20');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, 'Hungary', 'Danuve', '2019년8월5일', '17:50');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, 'Austrailia', 'Hallsttaat', '2019년8월27일', '15:00');");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }








}
