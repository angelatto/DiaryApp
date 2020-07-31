package ddwucom.mobile.finalreport;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//과제명 : 다이어리 앱을 기반으로 여행 일정을 기록하는 어플리케이션 개발
//분반 : 01분반
//학번 : 20170971 성명 : 이채정
//제출일 : 2019년 6월 26일
public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    final int REQ_CODE = 100;
    final int UPDATE_CODE = 200;

    ListView listView;
    DiaryAdapter diaryAdapter;
    ArrayList<Diary> myDiaryList;
    DiaryDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        myDiaryList = new ArrayList<Diary>();
        dbHelper = new DiaryDBHelper(this);
        diaryAdapter = new DiaryAdapter(this, R.layout.custom_adapter_view, myDiaryList);
        listView.setAdapter(diaryAdapter);

        // 리스트뷰의 항목 롱클릭 처리
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
               // 삭제 기능 작성 - 삭제 확인 대화상자 출력
                final int position = pos;

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("삭제 확인");
                builder.setMessage( myDiaryList.get(position).getPlace() + "을(를) 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //  DB 삭제 수행
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        String whereClause = DiaryDBHelper.COL_ID + "=?";
                        String[] whereArgs = new String[] { String.valueOf(myDiaryList.get(position).get_id()) };
                        db.delete(DiaryDBHelper.TABLE_NAME, whereClause, whereArgs);
                        dbHelper.close();

                        // 새로운 DB 내용으로 리스트뷰 갱신
                        readAllFoods();
                        diaryAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
                return true;
            }
        });

        //리스트뷰의 항목 클릭 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                // 수정 기능 작성 - UpdateActivity 를 결과를 받아오는 형태로 호출
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);


//                Food foodDto = foodList.get(pos);
//                intent.putExtra("foodDto", foodDto);
                intent.putExtra("diaryDto", myDiaryList.get(pos));

                startActivityForResult(intent, UPDATE_CODE);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        readAllFoods();
        diaryAdapter.notifyDataSetChanged();
    }

    private void readAllFoods() {
        myDiaryList.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_NAME, null);

        while(cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(dbHelper.COL_ID));
            String nation = cursor.getString(cursor.getColumnIndex(dbHelper.COL_NATION));
            String place = cursor.getString(cursor.getColumnIndex(dbHelper.COL_PLACE));
            String date = cursor.getString(cursor.getColumnIndex(dbHelper.COL_DATE));
            String time = cursor.getString(cursor.getColumnIndex(dbHelper.COL_TIME));

            myDiaryList.add ( new Diary (id, nation, place, date, time) );
        }

        cursor.close();
        dbHelper.close();
    }
    /*옵션 메뉴를 생성하는 Activity의 멤버 메소드 재정의*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);  // menu_main.xml 로 옵션 메뉴 inflation
        return true;    // 메뉴 생성을 처리하였으므로 true 반환
    }

    /*옵션 메뉴 선택을 처리하는 Activity의 멤버 메소드 재정의*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(this, AddActivity.class);
                startActivityForResult(intent, REQ_CODE);
                break;
            case R.id.introduce:
                //대화상자

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //내사진
                builder.setTitle("개발자 소개")
                        .setMessage("01분반 20170971 이채정")
                        .setIcon(R.mipmap.me)
                        .setPositiveButton("확인", null);

                builder.show();

                break;
            case R.id.finish:
                //대화상자
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);

                builder2.setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //클릭 시 앱 종료
                                finish();
                            }
                        });

                builder2.show();
                break;
            case R.id.search:
                //대화상자
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);

                final LinearLayout searchLayout = (LinearLayout)View.inflate(this, R.layout.activity_search, null);

                builder3.setTitle("검색하기")
                        .setMessage("도시 이름을 입력하세요")
                        .setIcon(R.mipmap.ic_launcher)
                        .setView(searchLayout)
                        .setPositiveButton("검색", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //검색결과를 보여주기
                                EditText place = (EditText)searchLayout.findViewById(R.id.input);
                                String  stPlace = place.getText().toString();
                                String result = "검색 결과: ";
                                //place와 같은 걸 찾아서
                                for(int i = 0; i < myDiaryList.size(); i++)
                                {
                                    if(myDiaryList.get(i).getPlace().equals(stPlace)) {
                                        result += "국가: " + myDiaryList.get(i).getNation() + "도시: " + myDiaryList.get(i).getPlace()
                                            + "날짜: " + myDiaryList.get(i).getDate() + "시간: " + myDiaryList.get(i).getTime();
                                    }
                                }
                                Toast.makeText(MainActivity.this, result + stPlace, Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("닫기", null)
                        .show();



                break;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {  // AddActivity 호출 후 결과 확인
            switch(resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "일정 추가 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "일정 추가 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (requestCode == UPDATE_CODE) {    // UpdateActivity 호출 후 결과 확인
            switch(resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "일정 수정 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "일정 수정 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }


    }


}
