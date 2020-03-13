// https://blog.csdn.net/zhangphil/article/details/79891765
package edu.ucsb.cs.cs48.schedoptim.ui.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.room.Room;

import java.util.ArrayList;

import edu.ucsb.cs.cs48.schedoptim.AddTaskActivity;
import edu.ucsb.cs.cs48.schedoptim.MainActivity;
import edu.ucsb.cs.cs48.schedoptim.R;
import edu.ucsb.cs.cs48.schedoptim.RouteDatabase;
import edu.ucsb.cs.cs48.schedoptim.Task;
import edu.ucsb.cs.cs48.schedoptim.TaskDatabase;
import edu.ucsb.cs.cs48.schedoptim.TaskViewActivity;
import edu.ucsb.cs.cs48.schedoptim.onboarding.OnboardingActivity;

import static android.content.Context.MODE_PRIVATE;
import static edu.ucsb.cs.cs48.schedoptim.MainActivity.cal;


public class SettingFragment extends Fragment {

    private SettingViewModel settingViewModel;

    TextView text_clear_today;
    TextView text_help;
    TextView text_clear_all;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);

        text_clear_today = root.findViewById(R.id.text_clear_today);
        text_help = root.findViewById(R.id.textView_help);
        text_clear_all = root.findViewById(R.id.textView_clear_all);

        final TaskDatabase db = Room.databaseBuilder(getContext(),
                TaskDatabase.class, "database-task")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        final  RouteDatabase rd = Room.databaseBuilder(getContext(),
                RouteDatabase.class, "database-route")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();


        //获取80dp转换后的设备pix值。
        int mTextViewWidth = dip2px(getContext(), 80);
        //计算所有文本占有的屏幕宽度(pix)
        float textWidth = text_clear_all.getPaint().measureText(text_clear_all.getText().toString());

        while (textWidth > mTextViewWidth) {

            textWidth = text_clear_all.getPaint().measureText(text_clear_all.getText().toString());

            //如果所有文本的宽度超过TextView自身限定的宽度，那么就尝试迭代的减小字体的textSize，直到不超过TextView的宽度为止。
             if (textWidth > mTextViewWidth) {
                int textSize = (int) text_clear_all.getTextSize();
                textSize = textSize - 2;
                text_clear_all.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                text_help.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                text_clear_today.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }

        }

        text_clear_today.setText(" Delete all tasks and to-dos for today! ");
        text_clear_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(getContext())
                        .setTitle("Are you sure?")
                        .setMessage("Do you really want to delete all things today?")
                        .setIcon(R.drawable.outline_notification_important_24)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                db.taskDao().deleteAll( db.taskDao().loadTaskByDate(Task.formatTaskDate(cal.getTime())));
                                db.taskDao().deleteAll( db.taskDao().loadTodoByDate(Task.formatTaskDate(cal.getTime())));
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        text_help.setText(" Show the tutorial again ");
        text_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoPrefsData();
                Intent i = new Intent(getContext(), OnboardingActivity.class);
                startActivity(i);
            }
        });

        text_clear_all.setText(" Delete all data to free device storage ");
        text_clear_all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Are you sure?")
                        .setMessage("Do you really want to delete all tasks, to-dos, and routes?")
                        .setIcon(R.drawable.outline_notification_important_24)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                db.taskDao().deleteAll();
                                rd.getrouteDao().deleteAll();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        return root;
    }

    public void undoPrefsData() {
        SharedPreferences pref = getContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("openedBefore", false);
        editor.commit();
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}