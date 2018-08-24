package com.gek.and.moduli.async;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.gek.and.moduli.AppConstants;
import com.gek.and.moduli.R;
import com.gek.and.moduli.activity.MainActivity;
import com.gek.and.moduli.db.service.GenerationResult;
import com.gek.and.moduli.model.Module;
import com.gek.and.moduli.util.FileUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class PDFGeneratorAsyncTask extends AsyncTask<Object, Void, Boolean>{

	private Activity parentActivity;
	
	@Override
	protected Boolean doInBackground(Object... params) {
		try {
			GenerationResult genRes = (GenerationResult)params[0];
			parentActivity = (Activity)params[1];
			
			InputStream is = parentActivity.getAssets().open("registration_template.xml");

			String templateContent = FileUtil.readContent(is);
			is.close();
			String registrationContent = generateRegistration(templateContent, genRes);
//			String test = "<html><head/><body style='font-family: arial'><b>hello oppossums!</b></body></html>";
			ByteArrayInputStream bis = new ByteArrayInputStream(registrationContent.getBytes());
//			ByteArrayInputStream bis = new ByteArrayInputStream(test.getBytes());
			
			Document document = new Document();
			File pdfFile = new File(parentActivity.getExternalFilesDir(null), AppConstants.PDF_NAME);
			FileOutputStream fos = new FileOutputStream(pdfFile.getAbsolutePath());

			PdfWriter writer = PdfWriter.getInstance(document, fos);

			document.open();

			XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
			XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
			fontProvider.register("/system/fonts/Arial.ttf");
			helper.parseXHtml(writer, document, bis, null, fontProvider);

			document.close();
			writer.close();
			fos.close();
			
			return Boolean.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		} 
	}
	
    @Override
    protected void onPostExecute(Boolean result) {
    	MainActivity mainActivity = (MainActivity)parentActivity;
    	if (result) {
    		mainActivity.onPdfGenerationOk();
    	}
    	else {
    		mainActivity.onPdfGenerationNotOk();
    	}
    }

	private String generateRegistration(String templateContent, GenerationResult genRes) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(parentActivity.getApplicationContext());
		
		String lastName = sp.getString("setting_pdf_lastName", "");
		String firstName = sp.getString("setting_pdf_firstName", "");
		String classString = sp.getString("setting_pdf_class", "");
		String catalog = sp.getString("setting_pdf_catalog", "");
		String t1 = templateContent.replace("$$Familienname$$", lastName.toUpperCase());
		String t2 = t1.replace("$$Vorname$$", firstName);
		String t3 = t2.replace("$$Klasse$$", classString);
		String t4 = t3.replace("$$Katalognummer$$", catalog);
		
		String t5 = generateTableData(genRes.getModulesWinter(), "W", t4);
		String t6 = generateTableData(genRes.getModulesSummer(), "S", t5);
		
		return t6;
	}

	private String generateTableData(List<Module> modules, String seasonMarker, String template) {
		int x = 1;
		String suffix = "$$";
		String s = template;
		
		for (Module module : modules) {
			String prefix = "$$" + seasonMarker + Integer.valueOf(x).toString();
			
			s = s.replace(prefix + "K" + suffix, module.getNumber());
			s = s.replace(prefix + "T" + suffix, module.getTitle());
			s = s.replace(prefix + "L" + suffix, module.getLeader());
			s = s.replace(prefix + "Z" + suffix, module.getWeekDay().getShort() + " " + module.getBlock().getShort());
			
			x++;
		}
		
		while (x < 4) {
			String prefix = "$$" + seasonMarker + Integer.valueOf(x).toString();
			
			s = s.replace(prefix + "K" + suffix, "&nbsp;");
			s = s.replace(prefix + "T" + suffix, "&nbsp;");
			s = s.replace(prefix + "L" + suffix, "&nbsp;");
			s = s.replace(prefix + "Z" + suffix, "&nbsp;");
			
			x++;
		}
		
		return s;
	}

}
