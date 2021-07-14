package com.app.dmm.core.utils.License;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
public class LicenseCheckModel implements Serializable {

    private static final long serialVersionUID = 8600137500316662317L;

    /**
     * 厂商信息
     */
    private String manufacturer;
    /**
     * 签发时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date makeDate;

    /**
     * 截止时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date dueDate;

    /**
     * 可被允许的IP地址
     */
    //private List ipAddress;
    private String ipAddress;

    /**
     * 可被允许的MAC地址
     */
    //private List macAddress;
    private String macAddress;

    /**
     * 可被允许的CPU序列号
     */
    private String cpuSerial;

    /**
     * 可被允许的主板序列号
     */
    private String mainBoardSerial;

    /**
     * 试用版  T   正式版  O  永久  E
     */
    private String certType;

    public LicenseCheckModel(String certType, int i, Map<String,Object> map) {

        this.manufacturer = "信科有限公司";
        this.ipAddress = map.get("IP").toString();
        this.macAddress = map.get("MAC").toString();
        this.cpuSerial = map.get("CPU").toString();
        this.mainBoardSerial = map.get("BOS").toString();
        this.certType = certType;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String currentDate  = sdf.format(now);
        if(!certType.equals("E")) {
            Calendar calc =Calendar.getInstance();
            calc.setTime(now);
            if(i == 1) {
                calc.add(Calendar.MONTH, 2);
                Date mDate = calc.getTime();
                this.makeDate = now;
                this.dueDate = mDate;
            }else if(i == 2){
                calc.add(Calendar.MONTH, 6);
                Date mDate = calc.getTime();
                this.makeDate = now;
                this.dueDate = mDate;
            }
        }
    }

}
