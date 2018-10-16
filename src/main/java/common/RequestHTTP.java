package common;

import beans.auth.Login;
import beans.facets.Ontology;
import com.fasterxml.jackson.databind.ObjectMapper;
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
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

    /**
     * Constructor
     */
    public RequestHTTP() { }

    /**
     * Get info Myrror login
     * @param email of user
     * @param password of user
     * @return (int) HTTP response code
     */
    public int userLogin(String email, String password) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JsonldModule(Collections::emptyList));

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

            responseCode = con.getResponseCode();
            Login login = objectMapper.readValue(con.getInputStream(), Login.class);

            this.getUserMyrrorData(login);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }

        return responseCode;
    }

    /**
     * GET request HTTP for Myrror collection
     * @param login (Login) Myrror login info
     * @return (int) HTTP response code
     */
    private int getUserMyrrorData(Login login) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JsonldModule(Collections::emptyList));

        URL url;
        HttpURLConnection con = null;
        int responseCode = -1;

        try {
            // TODO: check parameters
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("l", "10");

            url = new URL(this.profileURL + login.getUsername() + "?" + this.getParamsGetString(parameters));
            con = (HttpURLConnection) url.openConnection();

            con.setRequestProperty("x-access-token", login.getToken());

            Ontology ontology = objectMapper.readValue(con.getInputStream(), Ontology.class);
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
