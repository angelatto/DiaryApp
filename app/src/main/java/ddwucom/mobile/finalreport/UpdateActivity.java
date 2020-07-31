package ddwucom.mobile.finalreport;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText etNation;
    EditText etPlace;
    EditText etDate;
    EditText etTime;

    DiaryDBHelper dbHelper;

    Diary diaryDto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHelper = new DiaryDBHelper(this);
        etNation = findViewById(R.id.etNation);
        etPlace =  findViewById(R.id.etPlace);
        etDate =  findViewById(R.id.etDate);
        etTime =  findViewById(R.id.etTime);

        // MainActivity 에서 전달한 intent 를 통해 foodDto 추출
        Intent intent = getIntent();
        diaryDto = (Diary) intent.getSerializableExtra("diaryDto");

        etNation.setText(diaryDto.getNation());
        etPlace.setText(diaryDto.getPlace());
        etDate.setText(diaryDto.getDate());
        etTime.setText(diaryDto.getTime());

        CalendarView calendar = (CalendarView)findViewById(R.id.calendarView2);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                etDate.setText(year + "년" + (month+1) + "월" + dayOfMonth + "일");
            }
        });
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_update:
                String nation = etNation.getText().toString();
                String place = etPlace.getText().toString();
                String date = etDate.getText().toString();
                String time = etTime.getText().toString();


                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues row = new ContentValues();
                row.put(DiaryDBHelper.COL_NATION, nation);
                row.put(DiaryDBHelper.COL_PLACE, place);
                row.put(DiaryDBHelper.COL_DATE, date);
                row.put(DiaryDBHelper.COL_TIME, time);

                String whereClause = DiaryDBHelper.COL_ID + "=?";
                //  MainActivity 에서 전달받은 foodDto 의 id를 사용하여 수정할 데이터 식별
                String[] whereArgs = new String[] { String.valueOf(diaryDto.get_id()) };

                // update 메소드 사용 시 변경된 레코드 수 반환
                long count = db.update(DiaryDBHelper.TABLE_NAME, row, whereClause, whereArgs);

                if (count > 0) {
                    setResult(RESULT_OK, null);
                    dbHelper.close();
                    finish();
                } else {
                    Toast.makeText(this, "일정 수정 실패!", Toast.LENGTH_SHORT).show();
                    dbHelper.close();
                }

                break;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                break;
        }
        finish();

    }
}
