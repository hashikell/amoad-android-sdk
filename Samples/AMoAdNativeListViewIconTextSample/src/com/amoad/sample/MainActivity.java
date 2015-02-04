package com.amoad.sample;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.amoad.AMoAdLogger;
import com.amoad.AMoAdLoggerListener;
import com.amoad.AMoAdNativeViewManager;

public class MainActivity extends ListActivity {
    private static final String TAG = "ad-01";
    // TODO 管理画面から発行されるリストビューのアイコン画像テキスト型のsidを設定してください
    private static final String SID = "管理画面から発行されるリストビューのアイコン画像テキスト型のsidを設定してください";
    // TODO 管理画面に設定した「広告表示位置」の「開始位置」を設定してください
    private static final int BEGIN_INDEX = 1;
    // TODO 管理画面に設定した「広告表示位置」の「間隔」を設定してください
    private static final int INTERVAL = 5;

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AMoAdLogger.getInstance().setEnabled(true);
        AMoAdLogger.getInstance().addAMoAdLoggerListener(new AMoAdLoggerListener() {
            @Override
            public void onLog(int level, String tag, String msg, Throwable throwable) {
                // ログ確認
            }
        });

        AMoAdNativeViewManager.getInstance(this).prepareAd(SID, TAG, BEGIN_INDEX, INTERVAL, true);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        add();

        BaseAdapter adAdapter = AMoAdNativeViewManager.getInstance(this).createAdapter(SID, TAG, mAdapter, R.layout.template);
        setListAdapter(adAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("add");
        menu.add("update");
        menu.add("delete");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ("add".equals(item.getTitle())) {
            add();
        } else if ("update".equals(item.getTitle())) {
            update();
        } else if ("delete".equals(item.getTitle())) {
            delete();
        }
        return true;
    }

    void add() {
        int index = mAdapter.getCount();
        for (int i = 0; i < 10; i++) {
            mAdapter.add("item " + (i + index));
        }
    }

    void update() {
        mAdapter.clear();
        AMoAdNativeViewManager.getInstance(this).updateAd(SID, TAG);
        add();
    }

    void delete() {
        if (!mAdapter.isEmpty()) {
            mAdapter.remove(mAdapter.getItem(0));
        }
    }
}
