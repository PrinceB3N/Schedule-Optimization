package edu.ucsb.cs.cs48.schedoptim;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.ui.calendar.todo.TodoViewModel;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class TodoViewModelTest {
    private TaskDao taskDao;
    private TaskDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TaskDatabase.class).build();
        taskDao = db.taskDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }
    @Test
    public void onlyTwo15MinuteSpotsAtMidnightAndBeforeMidnightTest() throws Exception {
        Task task1 = new Task();
        task1.setBegin_time("00:15");
        task1.setEnd_time("23:45");
        task1.setDuration("23:30");
        task1.setType("task");

        Task todo1 = new Task();
        todo1.setBegin_time("00:00");
        todo1.setEnd_time("24:00");
        todo1.setDuration("00:15");
        todo1.setType("todo");

        Task todo2 = new Task();
        todo2.setBegin_time("00:00");
        todo2.setEnd_time("24:00");
        todo2.setDuration("00:15");
        todo2.setType("todo");

        TodoViewModel viewModel = new TodoViewModel();
        viewModel.moveTodosToTasks(taskDao, Calendar.getInstance().getTime());

        List<Task> tasks = taskDao.loadAllTasks();
        String tasksOutput = "BeginTime1: " + tasks.get(0).getBegin_time() + " EndTime1: " + tasks.get(0).getEnd_time() +
                " BeginTime2: " + tasks.get(1).getBegin_time() + " EndTime2: " + tasks.get(1).getEnd_time();
        assertThat(tasksOutput, is("BeginTime1: 00:00 EndTime1: 00:15 BeginTime2: 23:45 EndTime2: 24:00"));
    }
    }