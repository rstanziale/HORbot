package common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.HashMap;

public class RequestHTTP {

    // URL Request for Myrror API
    private static String url = "http://localhost:5000/api/profile/";

    // Myrror developer Token
    private String myrrorToken;

    public RequestHTTP() {
        this.myrrorToken = new PropertyUtilities().getProperty("myrrorToken");
    }

    public int getUserMyrrorData(String username) {
        URL url;
        HttpURLConnection con = null;
        int responseCode = -1;

        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("l", "10");
            parameters.put("f", "Affects");

            url = new URL(this.url + username + this.getParamsString(parameters));
            con = (HttpURLConnection) url.openConnection();

            con.setRequestProperty("x-access-token", this.myrrorToken);

            // TODO: create beans from resolve

            responseCode = con.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }

        return responseCode;
    }

    private String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        result.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}
