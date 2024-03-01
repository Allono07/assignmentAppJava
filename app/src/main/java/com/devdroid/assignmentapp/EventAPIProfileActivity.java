package com.devdroid.assignmentapp;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class EventAPIProfileActivity {

    public void makeApiCall() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("https://api2.branch.io/v2/event/custom");


                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setDoOutput(true);


                    String requestBody = "{\n" +
                            "  \"name\": \"profile_clicked\",\n" +
                            "  \"customer_event_alias\": \"my custom alias\",\n" +
                            "  \"user_data\": {\n" +
                            "    \"advertising_ids\": {\n" +
                            "      \"oaid\": \"02ab41d3-7886-4f29-a606-fba4372e9fdc\"\n" +
                            "    },\n" +
                            "    \"os\": \"Android\",\n" +
                            "    \"os_version\": \"25\",\n" +
                            "    \"environment\": \"FULL_APP\",\n" +
                            "    \"aaid\": \"abcdabcd-0123-0123-00f0-000000000000\",\n" +
                            "    \"android_id\": \"a12300000000\",\n" +
                            "    \"limit_ad_tracking\": false,\n" +
                            "    \"developer_identity\": \"user123\",\n" +
                            "    \"country\": \"US\",\n" +
                            "    \"language\": \"en\",\n" +
                            "    \"ip\": \"192.168.1.1\",\n" +
                            "    \"local_ip\": \"192.168.1.2\",\n" +
                            "    \"brand\": \"LGE\",\n" +
                            "    \"app_version\": \"1.0.0\",\n" +
                            "    \"model\": \"Nexus 5X\",\n" +
                            "    \"screen_dpi\": 420,\n" +
                            "    \"screen_height\": 1794,\n" +
                            "    \"screen_width\": 1080\n" +
                            "  },\n" +
                            "  \"event_data\": {\n" +
                            "    \"custom_param_1\": \"Parameter 1\",\n" +
                            "    \"custom_param_2\": \"Parameter 2\",\n" +
                            "    \"custom_param_3\": \"Parameter 3\"\n" +
                            "  },\n" +
                            "  \"custom_data\": {\n" +
                            "    \"foo\": \"bar\"\n" +
                            "  },\n" +
                            "  \"metadata\": {},\n" +
                            "  \"branch_key\": \"key_live_ftpwWzSyD6GNxqgtoelAxkoiCxm9Ap5V\"\n" +
                            "}";


                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes(requestBody);
                    wr.flush();
                    wr.close();

                    // Get response
                    int responseCode = connection.getResponseCode();
                    System.out.println("Response Code: " + responseCode);
                    Log.d("event","profile view  navigated");
                    Log.d("RequestBody", requestBody);

                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
