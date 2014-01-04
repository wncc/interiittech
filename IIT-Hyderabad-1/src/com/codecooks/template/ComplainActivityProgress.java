package com.codecooks.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPHTTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamAdapter;

import com.codecooks.template.R;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ComplainActivityProgress extends Activity
{
	private double filesize=0;
	TextView uploading_filename;
	ProgressBar upload_progress;
	String tfilename=null;
	String mfilename = null;
    static FileInputStream fis1 = null;
    static FileInputStream fis2 = null;
    String proxyHost = null;
    String proxyPort = null;
    String proxyUser = null;
    String proxyPass = null;
    FTPClient ftp = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		
		// FTP Connect & Upload File
		Intent startingIntent = getIntent();
		tfilename = startingIntent.getStringExtra("tfilename");
		mfilename = startingIntent.getStringExtra("mfilename");
		
		upload_progress = (ProgressBar) findViewById(R.id.activity_complain_upload_progressBar);
		upload_progress.setMax(100);
		String PREF_FILE = "verified";
		SharedPreferences settings = getSharedPreferences(PREF_FILE,0);
		proxyHost = settings.getString("proxyHost", null);
		proxyPort = settings.getString("proxyPort", null);
		proxyUser = settings.getString("proxyUser", null);
		proxyPass = settings.getString("proxyPass", null);
		thread final_upload = new thread();
		final_upload.start();
	}
	
	private class thread extends Thread
	{
		@Override
		public void run()
		{
			
	        int reply = 0;
	        try
	        {
		        if(proxyHost!=null && proxyPort!=null && proxyUser==null && proxyPass==null)
		        {
		        	ftp = new FTPHTTPClient(proxyHost, Integer.parseInt(proxyPort));
		        }
		        else
		        if(proxyHost!=null && proxyPort!=null && proxyUser!=null && proxyPass!=null)
			    {
			        ftp = new FTPHTTPClient(proxyHost, Integer.parseInt(proxyPort),proxyUser,proxyPass);
			    }	
		        else
		        {
		        	ftp = new FTPClient();
		        }
	        }
	        catch(Exception e)
	        {
	        	Toast.makeText(getApplicationContext(), "Error during upload", Toast.LENGTH_SHORT).show();
	        }
	        try
	        {
	            ftp.connect("83.125.22.186",21);
	            ftp.login("1341831", "iith1234");
	            System.out.println("Connected to : "+ "83.125.22.186");
	            System.out.println(ftp.getReplyString());
	            reply = ftp.getReplyCode();
	            if(!FTPReply.isPositiveCompletion(reply))
	            {
	                ftp.disconnect();
	                System.err.println("FTP Connection refused");
	            }
	            
	            CopyStreamAdapter streamListener = new CopyStreamAdapter() 
	            {
	               @Override
	                public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
	                   //this method will be called everytime some bytes are transferred
	                   int percent = (int)(totalBytesTransferred*100/filesize);
	                   // update your progress bar with this percentage
	                   //System.out.println(percent);
	                   upload_progress.setProgress(percent);
	                }
	            };
	            
	            ftp.enterLocalPassiveMode();
	            ftp.setControlKeepAliveTimeout(300);
	            ftp.setFileType(FTP.BINARY_FILE_TYPE);
	            ftp.changeWorkingDirectory("/upload");
	            
	            File f1 = new File(tfilename);
	            fis1 = new FileInputStream(f1);
	            System.out.println(f1.getName()+" -> "+f1.length() + " bytes");
	            
	            File f2 = new File(mfilename);
	            fis2 = new FileInputStream(f2);
	            System.out.println(f2.getName()+" -> "+f2.length() + " bytes");
	            
	            filesize = f1.length() + f2.length();
	            ftp.setCopyStreamListener(streamListener);
	            boolean done1 = ftp.storeFile(f1.getName(), fis1);
	            boolean done2 = ftp.storeFile(f2.getName(), fis2);
	            if(done1 && done2)
	            {
	            	upload_progress.setProgress(100);
	                System.out.println("File uploaded Successful");
	            }            
	        }
	        catch(Exception e)
	        {
	           e.printStackTrace();
	        }
	        finally 
	        {
	            try 
	            {
	                if (fis1 != null) 
	                {
	                    fis1.close();
	                }
	                if (fis2 != null) 
	                {
	                    fis2.close();
	                }
	                if (ftp.isConnected())
	                {
	                    try
	                    {
	                        ftp.logout();
	                        ftp.disconnect();
	                    }
	                    catch (IOException f)
	                    {
	                        // do nothing
	                    }
	                }
	                System.out.println("Done");
	            } 
	            catch (IOException e)
	            {
	                e.printStackTrace();
	            }
	        }

	}
	
	
}
}
