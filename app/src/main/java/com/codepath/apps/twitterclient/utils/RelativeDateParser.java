package com.codepath.apps.twitterclient.utils;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.text.ParseException;

/**
 * Created by yutu on 8/12/15.
 */
public class RelativeDateParser {
    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");

    public static String reFormatTime(String rawDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat);
        SimpleDateFormat out = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String output;
        try {
            output = out.format(sf.parse(rawDate));

        } catch (ParseException e) {
            return "";
        }

        return output;
    }
    public static String getRelativeTime(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = reformat(DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString());


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    // Dirty work here
    private static String reformat(String rd) {
        rd = rd.replaceAll("second ago", "s");
        rd = rd.replaceAll("seconds ago", "s");
        rd = rd.replaceAll("minute ago", "m");
        rd = rd.replaceAll("minutes ago", "m");
        rd = rd.replaceAll("hours ago", "h");
        rd = rd.replaceAll("hour ago", "h");
        rd = rd.replaceAll("day ago", "d");
        rd = rd.replaceAll("days ago", "d");
        rd = rd.replaceAll("month ago", "mth");
        rd = rd.replaceAll("months ago", "mth");
        return rd;
    }
}
