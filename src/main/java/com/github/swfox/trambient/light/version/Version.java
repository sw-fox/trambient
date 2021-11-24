package com.github.swfox.trambient.light.version;

import com.github.swfox.trambient.Main;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Version {
    private final static Logger log = LoggerFactory.getLogger(Version.class);
    private final String version;
    private final String latestVersion;

    public Version() {
        version = readFile("version.txt");
        latestVersion = readLatestVersion("https://api.github.com/repos/sw-fox/trambient/releases/latest");
        log.info("latest Version is <{}>", latestVersion);
    }

    // print input stream
    private static String readFile(String fileName) {
        StringBuilder builder = new StringBuilder();
        ClassLoader classLoader = Main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static String readLatestVersion(String urlToRead) {
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder builder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                builder.append(inputLine);
            }
            in.close();
            Gson g = new Gson();

            GithubStatusDto tag = g.fromJson(builder.toString(), GithubStatusDto.class);
            return tag.getTagName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getVersion() {
        return version;
    }
    public String getLatestVersion() {
        return latestVersion;
    }
    public boolean isUdateAvailable() {
        return new ComparableVersion(version).isOlderThan(latestVersion);
    }
}
