package com.guotiny.mydemoapplication;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.guotiny.mydemoapplication.micalendar.MonthPager;
import com.guotiny.mydemoapplication.micalendar.adpter.CalendarViewAdapter;
import com.guotiny.mydemoapplication.micalendar.model.CustomDate;
import com.guotiny.mydemoapplication.micalendar.views.CalendarView;

public class MiCalendarActivity extends AppCompatActivity {


    private MonthPager mViewPager;
    private CalendarView[] mShowViews;
    private CalendarViewAdapter<CalendarView> adapter;

    private TextView textViewYearDisplay;
    private TextView textViewMonthDisplay;
    private TextView textViewWeekDisplay;
    private TextView monthCalendarView;
    private TextView weekCalendarView;

    private CalendarView.OnCellCallBack mCallback;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private CustomDate lastClickCustomDate;
    private CalendarView[] viewsMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_calendar);
        initUI();
    }

    private void initUI() {
        textViewYearDisplay = (TextView) findViewById(R.id.show_year_view);
        textViewMonthDisplay = (TextView) findViewById(R.id.show_month_view);
        textViewWeekDisplay = (TextView) findViewById(R.id.show_week_view);
        monthCalendarView = (TextView) this.findViewById(R.id.month_calendar_button);
        weekCalendarView = (TextView) this.findViewById(R.id.week_calendar_button);
        mViewPager = (MonthPager) this.findViewById(R.id.vp_calendar);
        mCallback = new CalendarView.OnCellCallBack() {

            @Override
            public void clickDate(CustomDate date) {
                textViewYearDisplay.setText(date.getYear() + "");
                textViewMonthDisplay.setText(date.getMonth() + "月");
                textViewWeekDisplay.setText(date.getDisplayWeek(date.getWeek()) + "");
            }

            @Override
            public void clickDatePosition(CustomDate date, CalendarView.State state) {
                lastClickCustomDate = date;
                switch (state){
                    case CURRENT_MONTH_DAY:
                        break;
                    case PAST_MONTH_DAY:
                        mViewPager.setCurrentItem(mCurrentPage - 1);
                        break;
                    case NEXT_MONTH_DAY:
                        mViewPager.setCurrentItem(mCurrentPage + 1);
                        break;
                }
            }

            @Override
            public void clickPosition(int position) {
                mViewPager.setSelectedIndex(position);
            }

            @Override
            public void onMesureCellHeight(int cellSpace) {

            }

            @Override
            public void changeDate(CustomDate date) {
                //move to onPageSelected
            }

            @Override
            public void init(CustomDate date) {
                textViewYearDisplay.setText(date.getYear() + "");
                textViewMonthDisplay.setText(date.getMonth() + "月");
                textViewWeekDisplay.setText(date.getDisplayWeek(date.getWeek()) + "");
            }
        };

        viewsMonth = new CalendarView[3];
        for (int i = 0; i < 3; i++) {
            viewsMonth[i] = new CalendarView(this,
                    CalendarView.MONTH_STYLE,
                    mCallback);
        }

        adapter = new CalendarViewAdapter<CalendarView>(viewsMonth);
        setViewPager();
    }


    private void setViewPager() {
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                mShowViews = adapter.getAllItems();

                if(mShowViews[position % mShowViews.length] instanceof CalendarView){
                    CustomDate date = mShowViews[position % mShowViews.length].getmShowDate();
                    textViewYearDisplay.setText(date.getYear() + "");
                    textViewMonthDisplay.setText(date.getMonth() + "月");
                    textViewWeekDisplay.setText(date.getDisplayWeek(date.getWeek()) + "");
                    if(lastClickCustomDate != null){
                        mShowViews[position % mShowViews.length].setSelect(lastClickCustomDate);
                        lastClickCustomDate = null;
                    }else {
                        //mShowViews[position % mShowViews.length].setSelect(date);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
