package com.example;
import org.apache.commons.lang3.StringUtils;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Main {

    private static final String HTML_TOP = "<html>\n" +
            "<head>\n" +
            "    <title>Clock</title>\n" +
            "    <meta http-equiv=\"refresh\" content=\"0.9\">\n" +
            "</head>\n" +
            "<body>\n";

    private static final String HTML_BODY = "<h1 align=\"center\">%timezone% Time</h1>\n" +
            "    <div align=\"center\"><span style=\"font-size:48\">hh:mm:ss</span></div>\n" +
            "<br>";

    private static final String HTML_FOOTER = "</body>\n" +
            "</html>\n" +
            "</html>\n";


    public static void main(String[] args) {

        ZoneId[] zones = {ZoneId.of("US/Eastern"),ZoneId.of("US/Central"),ZoneId.of("US/Mountain"),ZoneId.of("US/Pacific")};
        StringBuilder htmlPage = new StringBuilder();

        while (true) {
            htmlPage.append(HTML_TOP);

            for(int i = 0; i < zones.length; ++i){
                String htmlRow = HTML_BODY.replace("%timezone%",zones[i].toString());
                htmlRow = htmlRow.replace("hh:mm:ss",getZonedTime(zones[i]));
                htmlPage.append(htmlRow);
            }

            htmlPage.append(HTML_FOOTER);

            try (PrintWriter out = new PrintWriter(new FileWriter("clock.html"))) {
                out.println(htmlPage);
                out.flush();
            } catch (IOException e) {
                System.out.println("Error writing to clock.html: " + e.getMessage());
            }

            htmlPage.delete(0, htmlPage.capacity());

            try {
                Thread.sleep(900l);
            } catch (InterruptedException e) {
                System.out.println("could not sleep for 1 second: " + e.getMessage());
            }
        }
    }

    public static StringBuilder getZonedTime(ZoneId timeZone){
        String hour;
        String minute;
        String second;
        StringBuilder timeString = new StringBuilder();

        ZonedDateTime theZoneTime = ZonedDateTime.now(timeZone);

        hour = String.valueOf(theZoneTime.getHour());
        minute = String.valueOf(theZoneTime.getMinute());
        second = String.valueOf(theZoneTime.getSecond());

        minute = StringUtils.leftPad(minute, 2, "0");
        second = StringUtils.leftPad(second, 2, "0");

        timeString = timeString.append(hour + ":").append(minute + ":").append(second);
        return timeString;

    }
}
