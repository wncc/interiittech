package com.codecooks.template;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import com.codecooks.template.R;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoActivity extends Activity implements SurfaceHolder.Callback, OnInfoListener, OnErrorListener
{

	private Button initBtn = null;
	private Button startBtn = null;
	private Button stopBtn = null;
	private Button playBtn = null;
	private Button stopPlayBtn = null;
	private TextView recordingMsg = null;
	private VideoView videoView = null;
	private SurfaceHolder holder = null;
	private Camera camera = null;
	private static final String TAG = "RecordingVideo";
	Calendar c = Calendar.getInstance();
	MediaRecorder recorder = null;
	private String outputFilename;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_video);
		
		initBtn = (Button) findViewById(R.id.activity_record_video_initBtn);
		startBtn = (Button) findViewById(R.id.activity_record_video_startBtn);
		stopBtn = (Button) findViewById(R.id.activity_record_video_stopBtn);
		playBtn = (Button) findViewById(R.id.activity_record_video_startPlayBtn);
		stopPlayBtn = (Button) findViewById(R.id.activity_record_video_stopPlayBtn);
		recordingMsg = (TextView) findViewById(R.id.activity_record_video_recordingMsg);
		videoView = (VideoView) findViewById(R.id.activity_record_video_videoView);
	}
	
	public void doClick(View view)
	{
		switch(view.getId())
		{
		case R.id.activity_record_video_initBtn:
			initRecorder();
			break;
		case R.id.activity_record_video_startBtn:
			startRecorder();
			break;
		case R.id.activity_record_video_stopBtn:
			stopRecorder();
			break;
		case R.id.activity_record_video_startPlayBtn:
			startPlayRecorder();
			break;
		case R.id.activity_record_video_stopPlayBtn:
			stopPlayRecorder();
			break;
			
		}
	}

	private void stopPlayRecorder() {
		// TODO Auto-generated method stub
		videoView.stopPlayback();
	}

	private void startPlayRecorder() {
		// TODO Auto-generated method stub
		MediaController mc = new MediaController(this);
		videoView.setMediaController(mc);
		videoView.setVideoPath(outputFilename);
		videoView.start();
		stopPlayBtn.setEnabled(true);
	}

	private void stopRecorder() {
		// TODO Auto-generated method stub
		if(recorder!=null)
		{
			recorder.setOnErrorListener(null);
			recorder.setOnInfoListener(null);
			try
			{
				recorder.stop();
			}
			catch(IllegalStateException e)
			{
				// In case recorder has already stpped
				Log.v(TAG,"Got illegal state exception in stopRecording");
			}
			releaseRecorder();
			recordingMsg.setText("");
			releaseCamera();
			startBtn.setEnabled(false);
			stopBtn.setEnabled(false);
			playBtn.setEnabled(true);
		}
	}

	private void releaseCamera() {
		// TODO Auto-generated method stub
		if(camera!=null)
		{
			try
			{
				camera.reconnect();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			camera.release();
			camera = null;
		}
	}

	private void releaseRecorder() {
		// TODO Auto-generated method stub
		if(recorder!=null)
		{
			recorder.release();
			recorder = null;
		}
	}

	private void startRecorder() {
		// TODO Auto-generated method stub
		recorder.setOnInfoListener(this);
		recorder.setOnErrorListener(this);
		recorder.start();
		recordingMsg.setText("RECORDING");
		startBtn.setEnabled(false);
		stopBtn.setEnabled(true);
	}

	private void initRecorder() {
		// TODO Auto-generated method stub
		if(recorder!=null)
			return;
		Context context = getApplicationContext(); 

		outputFilename = context.getFilesDir().getAbsolutePath()+"/"+c.get(Calendar.YEAR)+"_"+c.get(Calendar.MONTH)+"_"+c.get(Calendar.DATE)+"_"+c.get(Calendar.MILLISECOND)+"_"+c.get(Calendar.SECOND)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.HOUR_OF_DAY)+".mp4";
		//outputFilename = Environment.getExternalStorageDirectory()+"/"+c.get(Calendar.YEAR)+"_"+c.get(Calendar.MONTH)+"_"+c.get(Calendar.DATE)+"_"+c.get(Calendar.MILLISECOND)+"_"+c.get(Calendar.SECOND)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.HOUR_OF_DAY)+".mp4";
		
		File outFile = new File(outputFilename);
		if(outFile.exists())
		{
			Random rand = new Random();
			outputFilename = outputFilename.substring(0, outputFilename.lastIndexOf(".mp4"))+rand.nextInt(10000)+".mp4";
			outFile = new File(outputFilename);
			if(outFile.exists())
				outFile.delete();
		}
		
		try
		{
		 camera.stopPreview();
		 camera.unlock();
		 recorder = new MediaRecorder();
		 recorder.setCamera(camera);
		 
		 recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
		 recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		 recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		 recorder.setVideoSize(176, 144);
		 recorder.setVideoFrameRate(15);
		 recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		 recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
		 recorder.setMaxDuration(60000);
		 recorder.setPreviewDisplay(holder.getSurface());
		 recorder.setOutputFile(outputFilename);
		 recorder.prepare();
		 
		 Log.v(TAG,"MediaRecorder initialized");
		 initBtn.setEnabled(false);
		 startBtn.setEnabled(true);
		 }
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		Log.v(TAG, "initial surface Created");
		try
		{
			camera.setPreviewDisplay(arg0);
			camera.startPreview();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			Log.v(TAG,"Could not start preview");
		}
		initBtn.setEnabled(true);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(MediaRecorder arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInfo(MediaRecorder arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onResume() {
		Log.v(TAG,"in onResume()");
		super.onResume();
		initBtn.setEnabled(false);
		startBtn.setEnabled(false);
		stopBtn.setEnabled(false);
		playBtn.setEnabled(false);
		stopPlayBtn.setEnabled(false);
		if(!initCamera())
			finish();
		
	}

	private boolean initCamera() {
		// TODO Auto-generated method stub
		try
		{
		camera = Camera.open();
		//Camera.Parameters camParams = camera.getParameters();
		camera.lock();
		holder = videoView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		catch(RuntimeException re)
		{
			Log.v(TAG,"Could not initialize the Camera");
			re.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	
}
