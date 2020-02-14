package national.soft.impresiones.util;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;

public class GeneraLogs extends AsyncTask<String, Void, Void> {

	public static void commitToFile(String procedimiento , String message){
		try {
			String root = Environment.getExternalStorageDirectory().toString();
			File myDir=new File(root);
			myDir.mkdir();
			if(myDir.isDirectory()){
				File logFile = new File(myDir, "logSunmi.txt");
				if(!logFile.exists()) {
				     logFile.createNewFile();			    
				}
				 FileWriter f = new FileWriter(logFile,true);
			     f.write("==============================================\nLog: "+FormatoFechas.obtenerhora() +"\n"+ procedimiento +"\n"+ message+"\r\n");
				 f.flush();
				 f.close();
			}
			
        } catch (Exception e) {
            Log.e("Error al guardar", e.getMessage());
        }
        
    }

	@Override
	protected Void doInBackground(String... params) {
		commitToFile(params[0], params[1]);
		return null;
	}
}
