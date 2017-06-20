package com.alexvasilkov.foldablelayout.sample.activities;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.foldablelayout.FoldableListLayout;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.items.PaintingsAdapter;

import java.io.IOException;

public class FoldableListActivity extends BaseActivity implements MediaPlayer.OnPreparedListener {

	private MediaPlayer mMediaPlayer;
	private boolean     mShouldContinuePlayingWhenReturned;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foldable_list);
		getSupportActionBar().hide();

		FoldableListLayout foldableListLayout = Views.find(this, R.id.foldable_list);
		foldableListLayout.setAdapter(new PaintingsAdapter(this, false));
		foldableListLayout.setScrollCallback(new FoldableListLayout.ScrollCallback() {
			@Override
			public void startedScrolling() {
				/*
				try {
					play();
				} catch (IOException e) {
					e.printStackTrace();
				}*/
			}

			@Override
			public void stoppedScrollingAndFirstIsVisible() {
				stop();
			}
		});
	}

	private void setup() throws IOException {
		AssetFileDescriptor afd = getAssets().openFd("song.mp3");
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setLooping(true);
		mMediaPlayer.prepareAsync();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mMediaPlayer != null) {
			mShouldContinuePlayingWhenReturned = mMediaPlayer.isPlaying();
		} else {
			mShouldContinuePlayingWhenReturned = false;
		}
		pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mMediaPlayer == null) {
			return;
		}
		if (!mShouldContinuePlayingWhenReturned) {
			return;
		}
		try {
			play();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void play() throws IOException {
		if (mMediaPlayer == null) {
			setup();
		}
		if (mMediaPlayer.isPlaying()) {
			return;
		}
		mMediaPlayer.start();
	}

	private void pause() {
		if (mMediaPlayer != null) {
			mMediaPlayer.pause();
		}
	}

	private void stop() {
		if (mMediaPlayer != null) {
			mMediaPlayer.pause();
			mMediaPlayer.seekTo(0);
		}
	}

	@Override
	public void onPrepared(final MediaPlayer mediaPlayer) {
		mediaPlayer.start();
	}
}
