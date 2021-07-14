package com.app.dmm.core.utils.License;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.stream.Collectors;

public class ServerInfoUtils {

    public static void main(String[] args) throws Exception {
        Map<String,Object> serverInfoMap = getServerInfos();
        System.out.println(serverInfoMap);
    }

    public static Map<String,Object> getServerInfos() throws Exception {
        Map<String,Object> serverInfoMap = new HashMap<>();
        // 操作系统类型
        String osName = System.getProperty("os.name").toLowerCase();
        // 根据不同操作系统类型 , 不同的数据获取方法
        if (osName.startsWith("windows")) {
            //读取ip 、mac、 CPU 、主板
            serverInfoMap = WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            serverInfoMap = LinuxServerInfos();
        }else{//其他服务器类型
            serverInfoMap = LinuxServerInfos();
        }
        return serverInfoMap;
    }

    /**
     * 用于获取客户Windows服务器的基本信息
     * @author
     * @date
     * @since
     */
    public static Map<String,Object> WindowsServerInfos() throws Exception {
        //读取ip 、mac、 CPU 、主板
        Map<String, Object> serverInfoMap = new HashMap<>();
        //操作系统类型
        String osName = System.getProperty("os.name").toLowerCase();
        serverInfoMap.put("OS", osName);
        //1. 获取所有网络接口
        List<String> ipResult = null;
        //2. 获取所有网络接口的Mac地址
        List<String> macResult = null;

        List<InetAddress> inetAddresses = getLocalAllInetAddress();
        if (inetAddresses != null && inetAddresses.size() > 0) {
            ipResult = inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
            //2. 获取所有网络接口的Mac地址
            macResult = inetAddresses.stream().map(inetAddr -> getMacByInetAddress(inetAddr)).distinct().collect(Collectors.toList());

        }
        serverInfoMap.put("IP", ipResult.get(0));
        serverInfoMap.put("MAC", macResult.get(0));
        //3. 使用WMIC获取CPU序列号
        String CPUserialNumber = getCPUSerial();
        serverInfoMap.put("CPU", CPUserialNumber);
        //4. 使用WMIC获取主板序列号
        String BOSserialNumber = getMainBoardSerial();
        serverInfoMap.put("BOS", BOSserialNumber);

        return serverInfoMap;
    }

    /**
     * 获取当前服务器所有符合条件的InetAddress
     * @author
     * @date
     * @since
     * @return
     */
    protected static List<InetAddress> getLocalAllInetAddress() throws Exception {

        List<InetAddress> result = new ArrayList<InetAddress>(4);
        // 遍历所有的网络接口
        for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface iface = networkInterfaces.nextElement();
            // 在所有的接口下再遍历IP
            for (Enumeration<InetAddress> inetAddresses = iface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
                InetAddress inetAddr = inetAddresses.nextElement();

                //排除LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress类型的IP地址
                if(!inetAddr.isLoopbackAddress() /*&& !inetAddr.isSiteLocalAddress()*/
                        && !inetAddr.isLinkLocalAddress() && !inetAddr.isMulticastAddress()){
                    result.add(inetAddr);
                }
            }
        }

        return result;
    }

    /**
     * 获取某个网络接口的Mac地址
     * @author
     * @date
     * @since
     * @param
     * @return
     */
    protected static String getMacByInetAddress(InetAddress inetAddr){
        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
            StringBuffer stringBuffer = new StringBuffer();

            for(int i=0;i<mac.length;i++){
                if(i != 0) {
                    stringBuffer.append("-");
                }

                //将十六进制byte转化为字符串
                String temp = Integer.toHexString(mac[i] & 0xff);
                if(temp.length() == 1){
                    stringBuffer.append("0" + temp);
                }else{
                    stringBuffer.append(temp);
                }
            }

            return stringBuffer.toString().toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected static String getCPUSerial() throws Exception {
        //序列号
        String serialNumber = "";

        //使用WMIC获取CPU序列号
        Process process = Runtime.getRuntime().exec("wmic cpu get processorid");
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());

        if(scanner.hasNext()){
            scanner.next();
        }

        if(scanner.hasNext()){
            serialNumber = scanner.next().trim();
        }

        scanner.close();
        return serialNumber;
    }

    protected static String getMainBoardSerial() throws Exception {
        //序列号
        String serialNumber = "";

        //使用WMIC获取主板序列号
        Process process = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());

        if(scanner.hasNext()){
            scanner.next();
        }

        if(scanner.hasNext()){
            serialNumber = scanner.next().trim();
        }

        scanner.close();
        return serialNumber;
    }

    protected static String getCPUSerial_Linux() throws Exception {
        //序列号
        String serialNumber = "";

        //使用dmidecode命令获取CPU序列号
        String[] shell = {"/bin/bash","-c","dmidecode -t processor | grep 'ID' | awk -F ':' '{print $2}' | head -n 1"};
        Process process = Runtime.getRuntime().exec(shell);
        process.getOutputStream().close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line = reader.readLine().trim();
        if(StringUtils.isNotBlank(line)){
            serialNumber = line;
        }

        reader.close();
        return serialNumber;
    }

    protected static String getMainBoardSerial_Linux() throws Exception {
        //序列号
        String serialNumber = "";

        //使用dmidecode命令获取主板序列号
        String[] shell = {"/bin/bash","-c","dmidecode | grep 'Serial Number' | awk -F ':' '{print $2}' | head -n 1"};
        Process process = Runtime.getRuntime().exec(shell);
        process.getOutputStream().close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line = reader.readLine().trim();
        if(StringUtils.isNotBlank(line)){
            serialNumber = line;
        }

        reader.close();
        return serialNumber;
    }

    /**
     * 用于获取客户Linux服务器的基本信息
     *
     * @author
     * @date
     * @since
     */
    public static Map<String,Object> LinuxServerInfos() throws Exception {
        //读取ip 、mac、 CPU 、主板
        Map<String, Object> serverInfoMap = new HashMap<>();
        //操作系统类型
        String osName = System.getProperty("os.name").toLowerCase();
        serverInfoMap.put("OS", osName);
        // 1. 获取所有网络接口
        List<String> ipResult = null;
        // 2. 获取所有网络接口的Mac地址
        List<String> macResult = null;

        List<InetAddress> inetAddresses = getLocalAllInetAddress();
        if (inetAddresses != null && inetAddresses.size() > 0) {
            ipResult = inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
            //2. 获取所有网络接口的Mac地址
            macResult = inetAddresses.stream().map(inetAddr -> getMacByInetAddress(inetAddr)).distinct().collect(Collectors.toList());

        }
        serverInfoMap.put("IP", ipResult.get(0));
        serverInfoMap.put("MAC", macResult.get(0));
        // 3. 使用WMIC获取CPU序列号
        String CPUserialNumber = getCPUSerial_Linux();
        serverInfoMap.put("CPU", CPUserialNumber);
        // 4. 使用WMIC获取主板序列号
        String BOSserialNumber = getMainBoardSerial_Linux();
        serverInfoMap.put("BOS", BOSserialNumber);

        return serverInfoMap;
    }

}
