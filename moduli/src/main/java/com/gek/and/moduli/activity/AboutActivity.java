package com.gek.and.moduli.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.Page;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

import com.gek.and.moduli.R;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class AboutActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		setTitle(R.string.action_about);

		WebView webView = (WebView) findViewById(R.id.webView_about);
		Button buttonOk = (Button) findViewById(R.id.buttonAboutOk);
		buttonOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				makePdf();
				finish();
			}
		});

		String customHtml = "<html><body><h3>Kursbuch 2015/2016</h3><br/><div><b>Idee: </b>Sandra Kahrer</div><br/><div><b>Programmierung: </b>Gerald Kahrer</div><br/><div><b>Grafik, Datenerfassung: </b>Jakob Kahrer</div><br/></body></html>";
		webView.loadData(customHtml, "text/html", "UTF-8");
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	    switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
        	makePdf();
            return true;
        default:
            return super.onMenuItemSelected(featureId, item);
	    }
	}

	private void makePdf() {
		View contentView = findViewById(R.id.webView_about);
		PdfDocument pdf = new PdfDocument();
		PageInfo pageInfo = new PageInfo.Builder(300, 300, 1).create();
		Page page = pdf.startPage(pageInfo);
		contentView.draw(page.getCanvas());
		pdf.finishPage(page);
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			pdf.writeTo(os);
			pdf.close();
			os.close();
		} catch (IOException e) {
			throw new RuntimeException("Error generating file", e);
		}

		Intent mShareIntent = new Intent();
		mShareIntent.setAction(Intent.ACTION_SEND);
		mShareIntent.setType("application/pdf");
		// Assuming it may go via eMail:
                mShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
			"Here is a PDF from PdfSend");
		mShareIntent.putExtra(
			getClass().getPackage().getName() + "." + "SendPDF", 
			os.toByteArray());
		startActivity(mShareIntent);
	}
}
