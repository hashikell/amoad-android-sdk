package com.amoad.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.amoad.AMoAdLogger;
import com.amoad.AMoAdLoggerListener;
import com.amoad.AMoAdNativeFailureListener;
import com.amoad.AMoAdNativeViewManager;

public class MainActivity extends Activity {
    private static final String TAG = "ad-01";
    // TODO 管理画面から発行されるネイティブ(APP)のアイコン画像テキスト型のsidを設定してください
    private static final String SID = "管理画面から発行されるネイティブ(APP)のアイコン画像テキスト型のsidを設定してください";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AMoAdLogger.getInstance().setEnabled(true);
        AMoAdLogger.getInstance().addAMoAdLoggerListener(new AMoAdLoggerListener() {
            @Override
            public void onLog(int level, String tag, String msg, Throwable throwable) {
                // ログ確認
            }
        });

        AMoAdNativeViewManager.getInstance(this).prepareAd(SID, TAG, true);

        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        View adView = AMoAdNativeViewManager.getInstance(this).createView(SID, TAG, R.layout.template, new AMoAdNativeFailureListener() {
            @Override
            public void onFailure(String sid, String tag, View templateView) {
                // 広告の取得失敗
            }
        });
        container.addView(adView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("update");
        menu.add("clear");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ("update".equals(item.getTitle())) {
            update();
        } else if ("clear".equals(item.getTitle())) {
            clear();
        }
        return true;
    }

    void update() {
        AMoAdNativeViewManager.getInstance(this).updateAd(SID, TAG);
    }

    void clear() {
        AMoAdNativeViewManager.getInstance(this).clearAd(SID, TAG);
    }
}