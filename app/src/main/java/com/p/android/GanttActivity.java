package com.p.android;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhh.android.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class GanttActivity extends Activity {
    private Context context;
    private LinearLayout llprogress;
    private LinearLayout llshijian;
    //  设置表示1小时的宽度
    private int dayWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gante);
        context = GanttActivity.this;
//      单位是px
        dayWidth = getPingMuWidth() / getTimeData().size();
//      根据返回的天数判断，大于多少天*时间轴的长度  就是向左的距离
//      进度条的长度就是，任务天数*时间周的长度，就是进度条的总长度
        llshijian = (LinearLayout) findViewById(R.id.llshijian);
        llprogress = (LinearLayout) findViewById(R.id.llprogress);
        addTextView();

        List<User> users = listMyTask();
        for (int i = 0; i < users.size(); i++) {
            //
            addRelativeLayout(users.get(i));

        }
    }

    /**
     * 添加TextView的方法
     * 添加表头(时间)
     */
    private void addTextView() {
        for (int i = 0; i < getTimeData().size(); i++) {
//          设置textView 并添加到 布局中
            TextView textView = new TextView(context);
            llshijian.addView(textView);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
            params.width = dayWidth;
            params.height = dip2px(context, 20);
            // params.leftMargin = dip2px(context, 1);
            // params.setMargins(dip2px(MainActivity.this, 1), 0, 0, 0); // 可以实现设置位置信息，如居左距离，其它类推
            // params.leftMargin = dip2px(MainActivity.this, 1);
            textView.setLayoutParams(params);
            //靠左垂直居中对齐
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setText(getTimeData().get(i) + "");
//            textView.setBackgroundColor(getResources().getColor(R.color.blue));
            textView.setBackgroundResource(R.mipmap.xhdpi);
        }
    }

    private List<String> getTimeData() {
        List<String> list = new ArrayList<>();
        list.add("00:00");
        list.add("01:00");
        list.add("02:00");
        list.add("03:00");
        list.add("04:00");
        list.add("05:00");
        list.add("06:00");
        list.add("07:00");
        list.add("08:00");
        list.add("09:00");
        list.add("10:00");
        list.add("11:00");
        list.add("12:00");
        list.add("13:00");
        list.add("14:00");
        list.add("15:00");
        list.add("16:00");
        list.add("17:00");
        list.add("18:00");
        list.add("19:00");
        list.add("20:00");
        list.add("21:00");
        list.add("22:00");
        list.add("23:00");
        return list;
    }

    /**
     * 任务时长
     * 假设任务是12.1 到 12.9
     * end - start + 1
     */
    //      开始
    int start = 0;
    int end = 0;

    private void renwuTime(DateTime startDay, DateTime endDay) {
        List<String> list = getTimeData();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(startDay)) {
                start = i;
            }
            if (list.get(i).equals(endDay)) {
                end = i;
            }
        }
    }

    private void addRelativeLayout(User user) {
//      添加相对布局
        RelativeLayout relativeLayout = new RelativeLayout(context);
        llprogress.addView(relativeLayout);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.topMargin = dip2px(context, 10);
//      宽高默认都是包裹
        layoutParams.height = dip2px(context, 45);

//      把进度条添加到相对布局里面
        for (MyTask task : user.getTask()) {
            addProgress(relativeLayout, task.startTask, task.endTask, task.title);
        }
    }

    private void addProgress(RelativeLayout relativeLayout, DateTime startTime, DateTime endTime, String title) {

//      任务时间
        int fenzhongshu = (int) ((endTime.getMillis() - startTime.getMillis()) / (1000 * 60));

        ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
//        ProgressBar progressBar = new ProgressBar(context);
//      进度条装到相对布局里面
        relativeLayout.addView(progressBar);
        RelativeLayout.LayoutParams progressBarParams = (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
//      一天的宽度*天数+边框的宽度
        progressBarParams.width = dayWidth / 60 * fenzhongshu;
        progressBarParams.height = dip2px(context, 20);
        progressBarParams.leftMargin = (int) (dayWidth / 60 * (startTime.getMillis() - getMillis("2019-05-24 00:00:00")) / 1000 / 60);
        progressBar.setLayoutParams(progressBarParams);

        progressBar.setIndeterminate(false);
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar));
//        progressBar.setProgress(title);
        progressBar.setMax(100);

//      把TextView放入相对布局中===
        TextView textView = new TextView(context);
        relativeLayout.addView(textView);
        RelativeLayout.LayoutParams textViewParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        textViewParams.height = dip2px(context, 20);
//      距离左边是10
        textViewParams.leftMargin = progressBarParams.leftMargin + dip2px(context, 10);
        textView.setLayoutParams(textViewParams);

        textView.setText(title);
        textView.setGravity(Gravity.CENTER_VERTICAL);
    }

    /**
     * 造数据
     *
     * @return
     */
    private List<User> listMyTask() {
        List<MyTask> list = new ArrayList<>();
        MyTask myTask1 = new MyTask();
        myTask1.startTask = new DateTime(getMillis("2019-05-24 09:00:00"));
        myTask1.endTask = myTask1.startTask.plusMinutes(60);
        myTask1.title = "Title1";


        MyTask myTask2 = new MyTask();
        myTask2.startTask = new DateTime(getMillis("2019-05-24 11:30:00"));
        myTask2.endTask = myTask2.startTask.plusMinutes(120);
        myTask2.title = "Title2";

        MyTask myTask3 = new MyTask();
        myTask3.startTask = new DateTime(getMillis("2019-05-24 16:30:00"));
        myTask3.endTask = myTask3.startTask.plusMinutes(45);
        myTask3.title = "Title3";

        list.add(myTask1);
        list.add(myTask2);
        list.add(myTask3);

        List<User> users = new ArrayList<>();
        users.add(new User("1", "User1", list));

        list = new ArrayList<>();
        myTask1 = new MyTask();
        myTask1.startTask = new DateTime(getMillis("2019-05-24 10:00:00"));
        myTask1.endTask = myTask1.startTask.plusMinutes(60);
        myTask1.title = "Title1";


        myTask2 = new MyTask();
        myTask2.startTask = new DateTime(getMillis("2019-05-24 11:30:00"));
        myTask2.endTask = myTask2.startTask.plusMinutes(120);
        myTask2.title = "Title2";

        list.add(myTask1);
        list.add(myTask2);
        users.add(new User("2", "User2", list));

        list = new ArrayList<>();
        myTask1 = new MyTask();
        myTask1.startTask = new DateTime(getMillis("2019-05-24 11:00:00"));
        myTask1.endTask = myTask1.startTask.plusMinutes(60);
        myTask1.title = "Title1";


        myTask2 = new MyTask();
        myTask2.startTask = new DateTime(getMillis("2019-05-24 13:30:00"));
        myTask2.endTask = myTask2.startTask.plusMinutes(120);
        myTask2.title = "Title2";

        list.add(myTask1);
        list.add(myTask2);

        users.add(new User("3", "User3", list));

        return users;
    }

    /**
     * 拿到手机屏幕的宽度
     * 单位是px
     */
    private int getPingMuWidth() {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        return width;
    }

    /**
     * 拿到数据屏幕的高度
     * 单位是px
     *
     * @return
     */
    private int getPingMuHeight() {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        return height;
    }

    /**
     * dp转为px
     * Android内部是px
     *
     * @param context  上下文
     * @param dipValue dp值
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private long getMillis(String day) {
        DateTime date = getDateTimePattern().parseDateTime(day);
        return date.getMillis();
    }

    private DateTimeFormatter getDateTimePattern() {
        return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    }
}
