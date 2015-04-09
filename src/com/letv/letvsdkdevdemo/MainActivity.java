package com.letv.letvsdkdevdemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.letv.android.sdk.main.BDVideoPartner;
import com.letv.android.sdk.main.BDVideoPartner.Callback;
import com.letv.android.sdk.main.IVideo;
import com.letv.android.sdk.main.User_Main;
import com.letv.android.sdk.utp.PlayUtils;

public class MainActivity extends Activity implements OnClickListener {

	/**
	 * 视频播放完成时的时间误差
	 */
	private final int VIDEO_TIME_OFFSET = 2 * 1000;
	private EditText mInitVolume, mInitlight;
	private Button mStartPlay;
	private RadioButton mSeektoRadio, mPauseRadio, mNothingRadio;
	private RadioButton mOriginalRadio, mSuperHighRadio, mLittlePkg;

	private int mVolumeValue, mLightValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mInitVolume = (EditText) findViewById(R.id.init_volume);
		mInitlight = (EditText) findViewById(R.id.init_light);
		mSeektoRadio = (RadioButton) findViewById(R.id.seekto_radio);
		mPauseRadio = (RadioButton) findViewById(R.id.pause_radio);
		mNothingRadio = (RadioButton) findViewById(R.id.nothing_radio);
		mOriginalRadio = (RadioButton) findViewById(R.id.original);
		mSuperHighRadio = (RadioButton) findViewById(R.id.super_high);
		mLittlePkg = (RadioButton) findViewById(R.id.little_pkg);
		mStartPlay = (Button) findViewById(R.id.start_play);
		mStartPlay.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (mStartPlay == v) {
			if (!TextUtils.isEmpty(mInitVolume.getText())) {
				mVolumeValue = Integer.parseInt(mInitVolume.getText()
						.toString());
				PlayUtils.initSoundVolume(mVolumeValue);
			}
			if (!TextUtils.isEmpty(mInitlight.getText())) {
				mLightValue = Integer.parseInt(mInitlight.getText().toString());
				PlayUtils.initBrightness(mLightValue);
			}

			if (mOriginalRadio.isChecked()) {
				// 原画 uu=7a4f55c18a&vu=769312c218
				PlayUtils
						.playVideo(this, "c34f821becb64978216a8765ccfff24e",
								"7a4f55c18a", "769312c218", "test", cb, true,
								0, "", "");
			} else if (mSuperHighRadio.isChecked()) {
				// 超清
				PlayUtils
						.playVideo(this, "c34f821becb64978216a8765ccfff24e",
								"a72978133a", "f3a7beaf4f", "test", cb, true,
								0, "", "");
			} else if (mLittlePkg.isChecked()) {
				//小包   1babdf7f0e  e9f0f8ccaf  9c94700923  4911a37527
				PlayUtils
						.playVideo(this, "c34f821becb64978216a8765ccfff24e",
								"a04808d307", "4911a37527", "test", cb, true,
								0, "", "");
			}

		}
	}

	private Callback cb = new Callback() {

		@Override
		public void onEvent(final int event, final String name, IVideo video) {
			if (null != video) {
				if (event == BDVideoPartner.EVENT_PLAY_START) {
//					Toast.makeText(MainActivity.this, "播放开始",
//							Toast.LENGTH_SHORT).show();
					if (mPauseRadio.isChecked()) {
						User_Main.getInstance().pause();
						mPauseRadio.setChecked(false);
						if (mNothingRadio != null) {
							mNothingRadio.setChecked(true);
						}
					} else if (mSeektoRadio.isChecked()) {
						User_Main.getInstance().seekTo(10 * 1000);
						// mSeektoRadio.setChecked(false);
						if (mNothingRadio != null) {
							mNothingRadio.setChecked(true);
						}
					}

				} else if (event == BDVideoPartner.EVENT_PLAY_STOP) {
//					Toast.makeText(MainActivity.this, "播放停止",
//							Toast.LENGTH_SHORT).show();
				} else if (event == BDVideoPartner.EVENT_PLAY_PAUSE) {
//					Toast.makeText(MainActivity.this, "播放暂停",
//							Toast.LENGTH_SHORT).show();
				} else if (event == BDVideoPartner.EVENT_PLAY_ERROR) {
//					Toast.makeText(MainActivity.this, "播放出错",
//							Toast.LENGTH_SHORT).show();
				} else if (event == BDVideoPartner.EVENT_PLAY_EXIT) {
					if (video != null) {
						
						Log.e("", "curTime:" + video.mCurrentTime + ","
								+ video.mTotalTime);
					}

				}
			}
		}

	};
}
