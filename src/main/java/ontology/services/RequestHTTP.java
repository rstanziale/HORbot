package ontology.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;
import ontology.beans.auth.Login;
import ontology.beans.facets.Ontology;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Define Request HTTP class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class RequestHTTP {

    // ONTOLOGY GET FROM MYRROR
    private Ontology ontology;

    /**
     * Constructor
     */
    public RequestHTTP() { }

    /**
     * Get the ontology requested by user login
     * @return Ontology returned from Myrror after login request
     */
    public Ontology getOntology() {
        return ontology;
    }

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
            String loginURL = "http://90.147.102.243:5000/auth/login";
            url = new URL(loginURL);

            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Accept-Charset", "UTF-8");

            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);

            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(this.getParamsGetString(params));
            out.flush();
            out.close();

            Login login = objectMapper.readValue(con.getInputStream(), Login.class);
            responseCode = con.getResponseCode();

            this.getUserMyrrorData(login);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        return responseCode;
    }

    /**
     * GET request HTTP for Myrror collection
     * @param login (Login) Myrror login info
     */
    private void getUserMyrrorData(Login login) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JsonldModule(Collections::emptyList));

        URL url;
        HttpURLConnection con = null;

        try {
            long DAY_IN_MS = 1000 * 60 * 60 * 24;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fromDate = simpleDateFormat.format(new Date(System.currentTimeMillis() - (10 * DAY_IN_MS)));

            Map<String, String> parameters = new HashMap<>();
            parameters.put("l", "25");
            //parameters.put("fromDate", fromDate);

            // URL Request for Myrror API
            String profileURL = "http://90.147.102.243:5000/api/profile/";
            url = new URL(profileURL + login.getUsername() + "?" + this.getParamsGetString(parameters));
            con = (HttpURLConnection) url.openConnection();

            con.setRequestProperty("x-access-token", login.getToken());

            this.ontology = objectMapper.readValue(con.getInputStream(), Ontology.class);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    /**
     * Put GET requesta params on the URL
     * @param params Request param key/value
     * @return Query params for URL
     * @throws UnsupportedEncodingException for encode exception
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
