package org.shikhon.fireSMSserver;

import android.util.Log;

import com.google.firebase.database.ServerValue;

import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by borhan on 4/29/17.
 */

public class RegObject {
    public static final String PHONE_NUM_PATTERN = "01[56789]\\d{8}|\\+8801[56789]\\d{8}|008801[56789]\\d{8}";
    public static final String COUPON_PATTERN = "((c|C)\\-?)?(([0]*[0-9]{1,3})|1000)(?![\\w\\d])";
    public static final String STATUS_SEARCH_PATTERN = "[\\s\\,]{1}(S|s|HI|hi|Hi|hI|HD|hd|Hd|hD|O|o|g|G|T|t)[\\s\\,]{1}";
    public static final String STATUS_PATTERN = "(S|s|HI|hi|Hi|hI|HD|hd|Hd|hD|O|o|g|G|T|t)";
    private static final String TAG = "RegObject";


    String userName;
    String status;
    String institute;
    String phoneNum;
    String couponCode;
    String fullMessage;
    Map<String, String> timeStamp;

    public RegObject() {
    }

    public RegObject(String none) {
        userName = "";
        status = "";
        institute = "";
        phoneNum = "";
        couponCode = "";
        fullMessage = "";
    }

    public RegObject(String userName, String status, String institute, String phoneNum, String couponCode, String fullMessage) {
        this("");
        this.userName = userName;
        this.status = status;
        this.institute = institute;
        this.phoneNum = phoneNum;
        this.couponCode = couponCode;
        this.fullMessage = fullMessage;
        timeStamp = ServerValue.TIMESTAMP;

    }

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getFullMessage() {
        return fullMessage;
    }

    public void setFullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
    }

    public Map<String, String> getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Map<String, String> timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static RegObject fromRegSMS(String s){
        String userName = "";
        String status = "";
        String institute = "";
        String phoneNum = "";
        String couponCode = "";
        int phoneNumStarts = 0;
        int phoneNumEnds = 0;

        List<String> subStringList = Arrays.asList(s.split(","));
        try {
            userName = subStringList.get(0).substring(4).trim();
            status = subStringList.get(1).trim();
            institute = subStringList.get(2).trim();
            phoneNum = subStringList.get(3).trim();
            phoneNumStarts = s.indexOf(phoneNum);
            phoneNumEnds = phoneNumStarts+phoneNum.length();
            couponCode = subStringList.get(4).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "fromRegSMS: "+phoneNumStarts + " " + phoneNumEnds);
        // Lets see if couponCode is really a couponCode
        if (!Pattern.matches(PHONE_NUM_PATTERN, phoneNum)){
            Pattern phonepattern = Pattern.compile(PHONE_NUM_PATTERN);
            Matcher m = phonepattern.matcher(s);
            if (m.find()){
                phoneNum = m.group(0);
                phoneNumStarts = s.indexOf(phoneNum);
                phoneNumEnds = phoneNumStarts+phoneNum.length();
            }
        }

        if (!Pattern.matches(COUPON_PATTERN, couponCode)){
            Pattern couponPattern =  Pattern.compile(COUPON_PATTERN);
            Matcher mS = couponPattern.matcher(s.substring(0, phoneNumStarts));
            while (mS.find()){
                couponCode = mS.group();
            }
            Matcher mE = couponPattern.matcher(s.substring(phoneNumEnds));
            while (mE.find()){
                couponCode = mE.group();
            }
        }
        if (!Pattern.matches(STATUS_PATTERN, status)){
            Pattern statusSearch = Pattern.compile(STATUS_SEARCH_PATTERN);
            Matcher m = statusSearch.matcher(s);
            while (m.find()){
                String st = m.group();
                Matcher sm = Pattern.compile(STATUS_PATTERN).matcher(st);
                while (sm.find()){
                    status = sm.group();
                }
            }
        } else Log.d(TAG, "fromRegSMS: pattern did match");

        Log.d(TAG, "fromRegSMS: "+couponCode);

        return new RegObject(userName, status, institute, phoneNum, couponCode, s);
    }
}
