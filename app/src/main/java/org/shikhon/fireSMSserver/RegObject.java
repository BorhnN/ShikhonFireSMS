package org.shikhon.fireSMSserver;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by borhan on 4/29/17.
 */

public class RegObject {
    public static final String PHONE_NUM_PATTERN = "01[56789]\\d{8}|\\+8801[56789]\\d{8}|008801[56789]\\d{8}";



    String userName;
    String status;
    String institute;
    String phoneNum;
    String couponCode;

    public RegObject() {
    }

    public RegObject(String userName, String status, String institute, String phoneNum, String couponCode) {
        this();
        this.userName = userName;
        this.status = status;
        this.institute = institute;
        this.phoneNum = phoneNum;
        this.couponCode = couponCode;
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
    public static RegObject fromRegSMS(String s){
        String userName;
        String status;
        String institute;
        String phoneNum;
        String couponCode;
        List<String> subStringList = Arrays.asList(s.split(","));
        userName = subStringList.get(0).substring(4).trim();
        status = subStringList.get(1).trim();
        institute = subStringList.get(2).trim();
        phoneNum = subStringList.get(3).trim();
        couponCode = subStringList.get(4).trim();
        if (!Pattern.matches(PHONE_NUM_PATTERN, phoneNum)){
        }
        return new RegObject(userName, status, institute, phoneNum, couponCode);
    }
}
