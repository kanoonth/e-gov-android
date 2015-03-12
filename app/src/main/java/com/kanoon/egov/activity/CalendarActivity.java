package com.kanoon.egov.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.kanoon.egov.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class CalendarActivity extends FragmentActivity {
    private Date selectedDate;
    private CaldroidFragment caldroidFragment;
    private Date today;
    private TimePicker timePicker;
    private SimpleDateFormat formatter;
    private String date;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_calendar);

        formatter = new SimpleDateFormat("yyyy-MM-dd");
        today = new Date();
        selectedDate = today;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNativeCalendar();
        } else {
            createCaldroid(savedInstanceState);
            caldroidFragment.setCaldroidListener( createCaldroidListener());
        }

        timePicker = (TimePicker) findViewById(R.id.timePicker1);
        timePicker.setIs24HourView(true);

        //Set Time for TimePicker
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(min);
        setDate(hour, min);

        timePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

        final Button resBtn = (Button) findViewById(R.id.set_reserve_button);
        resBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(v);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(new Explode());
                    Intent intent = new Intent(CalendarActivity.this, ConfirmationActivity.class);
                    intent.putExtra("DateTime", date);
                    startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(CalendarActivity.this).toBundle());
                } else {
                    Intent newActivity = new Intent(CalendarActivity.this, ConfirmationActivity.class);
                    newActivity.putExtra("DateTime", date);
                    startActivity(newActivity);
                }
            }
        });
    }

    private void createNativeCalendar() {
        datePicker = (DatePicker) findViewById(R.id.l_date_picker);
        datePicker.setMinDate(Calendar.getInstance().getTimeInMillis());

        Log.v(this.getClass().toString(), "v21.DatePicker: " + datePicker);
    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }
    }

    public void createCaldroid(Bundle savedInstanceState){
        caldroidFragment = new CaldroidFragment();

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            caldroidFragment.setArguments(args);
        }

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();
    }

    public CaldroidListener createCaldroidListener(){
        CaldroidListener listener = new CaldroidListener() {

        @Override
        public void onSelectDate(Date date, View view) {
            if(selectedDate != null){
                if(selectedDate.getDate() == today.getDate())
                    caldroidFragment.setBackgroundResourceForDate(com.caldroid.R.drawable.red_border,selectedDate);
                else
                    caldroidFragment.setBackgroundResourceForDate(R.color.white, selectedDate);
            }
            caldroidFragment.setBackgroundResourceForDate(R.color.blue, date);
            caldroidFragment.refreshView();
            selectedDate = date;
            }
        };
        return listener;
    }

    public void setTime(View view) {
        int hour = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();

        // THIS IS SO TERRIBLE!!
        // Low cohesion
        // Coupling
        // Bad Design
        // Side Effect
        // Low maintenancability
        setDate(hour, min);
    }

    public void setDate(int hour, int min) {
        String resultTime = (new StringBuilder().append(hour).append(":").append(min).append(":0")).toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year =  datePicker.getYear();
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            date = formatter.format(cal.getTime())+","+resultTime;


        } else {
            date = formatter.format(selectedDate)+","+resultTime;

        }
    }

}
