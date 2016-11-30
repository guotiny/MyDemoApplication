package com.guotiny.mydemoapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.guotiny.mydemoapplication.calendar.CollapseCalendarView;
import com.guotiny.mydemoapplication.calendar.manager.CalendarManager;

import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    private CollapseCalendarView calendarView;
    private CalendarManager mManager;
    private JSONObject json;
    private SimpleDateFormat sdf;
    private boolean show = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendarView = (CollapseCalendarView) findViewById(R.id.calendar);
        mManager = new CalendarManager(LocalDate.now(),
                CalendarManager.State.MONTH, LocalDate.now().withYear(100),
                LocalDate.now().plusYears(100));
        //月份切换监听器
        mManager.setMonthChangeListener(new CalendarManager.OnMonthChangeListener() {

            @Override
            public void monthChange(String month, LocalDate mSelected) {
                // TODO Auto-generated method stub
                Toast.makeText(CalendarActivity.this, month, Toast.LENGTH_SHORT).show();
            }

        });
        /**
         * 日期选中监听器
         */
        calendarView.setDateSelectListener(new CollapseCalendarView.OnDateSelect() {

            @Override
            public void onDateSelected(LocalDate date) {
                // TODO Auto-generated method stub
                Toast.makeText(CalendarActivity.this, date.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        calendarView.setTitleClickListener(new CollapseCalendarView.OnTitleClickListener() {

            @Override
            public void onTitleClick() {
                // TODO Auto-generated method stub
                Toast.makeText(CalendarActivity.this, "点击标题", Toast.LENGTH_SHORT).show();
            }
        });
        //回到今天
        findViewById(R.id.btn_today).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                calendarView.changeDate(LocalDate.now().toString());
            }
        });
        //周月切换
        findViewById(R.id.btn_changemode).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mManager.toggleView();
                calendarView.populateLayout();
            }
        });
        //显示或者隐藏农历
        findViewById(R.id.btn_hide).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                calendarView.showChinaDay(show);
                show = !show;
            }
        });
        Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DAY_OF_MONTH, 5);
        json = new JSONObject();
        try {
            for (int i = 0; i < 30; i++) {
                JSONObject jsonObject2 = new JSONObject();
                if (i <= 6) {
                    jsonObject2.put("type", "休");
                } else if ( i > 6 && i< 11) {
                    jsonObject2.put("type", "班");
                }
                if (i%3 == 0) {
                    jsonObject2.put("list", new JSONArray());
                }

                json.put(sdf.format(cal.getTime()), jsonObject2);

                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //设置数据显示
        calendarView.setArrayData(json);
        //初始化日历管理器
        calendarView.init(mManager);
    }

}
