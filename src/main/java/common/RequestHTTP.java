package common;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.HashMap;

/**
 * Define Request HTTP class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class RequestHTTP {

    // URL Request for Myrror API
    private static String profileURL = "http://localhost:5000/api/profile/";
    private static String loginURL = "http://localhost:5000/auth/login";

    // Myrror developer Token
    private String myrrorToken;

    /**
     * Constructor
     */
    public RequestHTTP() {
        this.myrrorToken = new PropertyUtilities().getProperty("myrrorToken");
    }

    /**
     * GET request HTTP for Myrror collection
     * @param username (String) Myrror username
     * @return (int) HTTP response code
     */
    public int getUserMyrrorData(String username) {
        URL url;
        HttpURLConnection con = null;
        int responseCode = -1;

        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("l", "10");
            parameters.put("f", "Affects");

            url = new URL(this.profileURL + "?" + username + this.getParamsGetString(parameters));
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

    public int userLogin(String email, String password) {
        URL url;
        HttpURLConnection con = null;
        int responseCode = -1;

        try {
            url = new URL(this.loginURL);

            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Accept-Charset", "UTF-8");

            Map<String, String> params = new HashMap<String, String>();
            params.put("email", email);
            params.put("password", password);

            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(this.getParamsGetString(params));
            out.flush();
            out.close();

            System.out.println(email);

            responseCode = con.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }

        return responseCode;

    }

    /**
     * Put GET requesta params on the URL
     * @param params (MAP<String, String>) Request param key/value
     * @return (String) Query params for URL
     * @throws UnsupportedEncodingException
     */
    private String getParamsGetString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

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
