package com.example.shadow.sortlistviewitem;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static int MAX = 8;

    private static int ON_TOP = 1;

    private static int CANCEL_TOP = 0;

    public static String TOP_STATES = "TOP";

    private ListView mListView;

    private List<Session> sessionList;

    private SessionItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        registerListener();
    }

    private void registerListener() {
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Session session = (Session) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putInt(TOP_STATES, session.getTop());
                PopupDialogFragment popupDialog = new PopupDialogFragment();
                popupDialog.setArguments(bundle);
                popupDialog.setItemOnClickListener(new PopupDialogFragment.DialogItemOnClickListener() {
                    @Override
                    public void onTop() {
                        //置顶
                        session.setTop(ON_TOP);
                        session.setTime(System.currentTimeMillis());
                        refreshView();
                    }

                    @Override
                    public void onCancel() {
                        //取消
                        session.setTop(CANCEL_TOP);
                        refreshView();
                    }

                });
                ;
                popupDialog.show(getFragmentManager(), "POPUP");
                return true;
            }
        });

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        mListView = (ListView) findViewById(R.id.listView);
        sessionList = new ArrayList<>();
        //简单实现Item数据
        TypedArray iconArray = getResources().obtainTypedArray(R.array.icon_array);
        for (int i = 0; i < MAX; i++) {
            Session session = new Session();
            session.setAvatar(iconArray.getResourceId(i,R.mipmap.ic_launcher));
            sessionList.add(session);
        }
        itemAdapter = new SessionItemAdapter(this);
        mListView.setAdapter(itemAdapter);
        refreshView();
        iconArray.recycle();
    }

    private void refreshView() {
        //如果不调用sort方法，是不会进行排序的，也就不会调用compareTo
        Collections.sort(sessionList);
        itemAdapter.updateData(sessionList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
