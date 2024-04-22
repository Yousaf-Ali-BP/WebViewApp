package com.yousaf.webview;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

	ProgressBar myprogressBar;
	WebView mywebview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mywebview = findViewById(R.id.mywebview);
		myprogressBar = findViewById(R.id.myProgressBar);

		mywebview.loadUrl("https://www.google.com");
		mywebview.getSettings().setJavaScriptEnabled(true);
		mywebview.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newprogress) {
				myprogressBar.setProgress(newprogress);
				super.onProgressChanged(view, newprogress);

			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				setTitle(title);
			}
		});
		mywebview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				myprogressBar.setVisibility(View.VISIBLE);
				super.onPageStarted(view, url, favicon);
			}

			public void onPageFinished(WebView view, String url) {
				myprogressBar.setVisibility(View.GONE);
				super.onPageFinished(view, url);

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {

		if (item.getItemId() == R.id.menuBack) {
			if (mywebview.canGoBack()) {
				mywebview.goBack();
			} else {
				exitDialog();
			}
		}

		else if (item.getItemId() == R.id.menuForward) {
			if (mywebview.canGoForward()) {
				mywebview.goForward();
			} else {
				Toast.makeText(this, "Can't go further", Toast.LENGTH_SHORT).show();
			}
		}

		else if (item.getItemId() == R.id.menuHome) {
			mywebview.loadUrl("https://www.google.com");
		} else if (item.getItemId() == R.id.menuRefresh) {
			mywebview.reload();
		}

		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onBackPressed() {
		if (mywebview.canGoBack()) {
			mywebview.goBack();
		} else {
			exitDialog();
		}
	}

	private void exitDialog(){
		
		AlertDialog.Builder myexitDialog=new AlertDialog.Builder(MainActivity.this);
		myexitDialog.setTitle("Exiting App?");
		myexitDialog.setMessage("Do you want to exit this app?");
		myexitDialog.setPositiveButton("Exit App",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog,int which){
				finish();
			}
		});
		myexitDialog.setNegativeButton("Stay in App",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog,int which){
				dialog.dismiss();
			}
		});
		
		myexitDialog.create();
		myexitDialog.show();
	}
}