package recommender.graphBased;

import common.Utils;

import java.io.*;
import java.util.HashMap;

public class FromFile {

	private static String data = "torino";
	private static String data_folder="/";

	public static void SetData(String dataset){
		data=dataset;
	}

	private static String GetPath(String file){
		return data_folder+file;
	}

	public static HashMap<String, String[]> getPlacesNew() throws IOException

	{
		InputStream in = Utils.class.getResourceAsStream(GetPath("businesses_"+data+"_filtered.csv"));
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String st;
		String[] all;
		HashMap<String, String[]> dict = new HashMap<String, String[]>();
		br.readLine();
		while ((st = br.readLine()) != null) {
			all = st.split(",");
			String[] cats = all[6].replaceAll("'", "").trim().split("\\s*;\\s*");
			dict.put(all[1].replaceAll("'", "").trim(), cats);
		}

		br.close();
		return dict;

	}
}
