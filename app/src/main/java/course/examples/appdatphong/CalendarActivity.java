package course.examples.appdatphong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import activity.MainActivity;
import adapter.giohang_adapter;
import ultil.check_connection;

public class CalendarActivity extends AppCompatActivity {
    Toolbar toolbarChonNgay;
    CalendarView calendarViewnhan, calendarViewtra;
    Button button_xacnhan;
    int position = 0;
    String s_ngaynhan, s_ngaytra, s_current;
    Date d_ngaynhan, d_ngaytra, d_current;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    long one_day = 86400000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Anhxa();
        ActionToolbar();
        GetIdloaisp();
        LoadCalendar();
        EventButton();
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChonNgay);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChonNgay.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void LoadCalendar() {
        if (MainActivity.arr_giohang.get(position).getNgaynhanphong() != null && MainActivity.arr_giohang.get(position).getNgaytraphong() != null)
        {
            Date d_tra = formatter.parse(MainActivity.arr_giohang.get(position).getNgaytraphong(),new ParsePosition(0));
            calendarViewtra.setDate(d_tra.getTime());
            Date d_nhan = formatter.parse(MainActivity.arr_giohang.get(position).getNgaynhanphong(),new ParsePosition(0));
            calendarViewnhan.setDate(d_nhan.getTime());
            s_ngaynhan = MainActivity.arr_giohang.get(position).getNgaynhanphong();
            s_ngaytra = MainActivity.arr_giohang.get(position).getNgaytraphong();
            Date currentTime = Calendar.getInstance().getTime();
            s_current = formatter.format(currentTime);
        } else
        {
            s_ngaynhan = formatter.format(new Date(calendarViewnhan.getDate()));
            s_ngaytra = s_ngaynhan;
            s_current = s_ngaynhan;
        }
    }

    void GetIdloaisp() {
        position = getIntent().getIntExtra("id_donggiohang", -1);
        Log.d("id_donggiohang", position + "");
    }

    private void EventButton() {
        calendarViewnhan.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                s_ngaynhan = dayOfMonth + "/" + (month + 1) + "/" + year;
            }
        });
        calendarViewtra.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                s_ngaytra = dayOfMonth + "/" + (month + 1) + "/" + year;
            }
        });
        button_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d_ngaynhan = formatter.parse(s_ngaynhan,new ParsePosition(0));
                d_ngaytra = formatter.parse(s_ngaytra,new ParsePosition(0));
                d_current = formatter.parse(s_current,new ParsePosition(0));
                long so_ngay =  d_ngaytra.getTime() - d_ngaynhan.getTime();

                if (d_ngaynhan.getTime() == d_ngaytra.getTime() || d_ngaynhan.getTime() > d_ngaytra.getTime()
                        || d_current.getTime() > d_ngaynhan.getTime() || d_current.getTime() > d_ngaytra.getTime())
                {
                    check_connection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại dữ liệu");
                } else if ((so_ngay / one_day) != MainActivity.arr_giohang.get(position).getSodem())
                {
                    check_connection.ShowToast_Short(getApplicationContext(),"Bạn cần chọn ngày cho " + MainActivity.arr_giohang.get(position).getSodem() + " đêm");
                } else
                {
                    MainActivity.arr_giohang.get(position).setNgaynhanphong(s_ngaynhan);
                    MainActivity.arr_giohang.get(position).setNgaytraphong(s_ngaytra);

                    Intent data = new Intent();
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }
            }
        });
    }

    private void Anhxa() {
        calendarViewnhan = (CalendarView) findViewById(R.id.CalendarView_ngaynhanphong);
        calendarViewtra = (CalendarView) findViewById(R.id.CalendarView_ngaytraphong);
        button_xacnhan = (Button) findViewById(R.id.bt_xacnhanngay);
        toolbarChonNgay= (Toolbar) findViewById(R.id.toolbar_chonngay);
    }
}
