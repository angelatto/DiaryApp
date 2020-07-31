package ddwucom.mobile.finalreport;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

//국가 장소 날짜 시간
    EditText etNation;
    EditText etPlace;
    EditText etDate;
    EditText etTime;

    DiaryDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etNation = findViewById(R.id.etNation);
        etPlace =  findViewById(R.id.etPlace);
        etDate =  findViewById(R.id.etDate);
        etTime =  findViewById(R.id.etTime);

        dbHelper = new DiaryDBHelper(this);


        CalendarView calendar = (CalendarView)findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                etDate.setText(year + "년" + (month+1) + "월" + dayOfMonth + "일");
            }
        });

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues value = new ContentValues();

                if(etNation.getText().toString().equals("")){

                    setResult(RESULT_CANCELED);
                    finish();
                }
                else {
                    value.put(DiaryDBHelper.COL_NATION, etNation.getText().toString());
                    value.put(DiaryDBHelper.COL_PLACE, etPlace.getText().toString());
                    value.put(DiaryDBHelper.COL_DATE, etDate.getText().toString());
                    value.put(DiaryDBHelper.COL_TIME, etTime.getText().toString());
                    //                insert 메소드를 사용할 경우 데이터 삽입이 정상적으로 이루어질 경우 1 이상, 이상이 있을 경우 0 반환 확인 가능
                    long count = db.insert(DiaryDBHelper.TABLE_NAME, null, value);

                    if (count > 0) { //정상적으로 데이터 삽입
                        setResult(RESULT_OK, null);
                        dbHelper.close();
                        finish();
                    } else {
                        Toast.makeText(this, "새로운 일정 추가 실패!", Toast.LENGTH_SHORT).show();
                        dbHelper.close();
                    }
                }


                break;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
