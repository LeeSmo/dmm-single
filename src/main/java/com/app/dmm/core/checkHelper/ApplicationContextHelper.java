package com.app.dmm.core.checkHelper;


import com.app.dmm.core.utils.License.LicenseCheckModel;
import com.app.dmm.core.utils.License.LicenseUtils;
import com.app.dmm.core.utils.License.ServerInfoUtils;
import com.app.dmm.core.utils.License.SystemFileCreateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            context = applicationContext;
            // ===== 项目初始化
            ApplicationHome home = new ApplicationHome(getClass());
            File jarFile = home.getSource();
            log.info("......"+jarFile.getParentFile());
            /*if(serverInfoMap.get("OS").toString().indexOf("linux") != -1)  {

            }*/
            boolean exist = initSystem(jarFile.getParentFile());
            if(exist){
                String path = jarFile.getParentFile()+"/keystore/keyCode.info";
                boolean activate = LicenseUtils.checkLic(getActivationCode(path));
                if(activate){
                    log.info("系统授权成功......");
                }else{
                    log.info("系统授权失败......");
                    System.exit(-1);
                }

            }else {
                Map<String,Object> serverInfoMap = ServerInfoUtils.getServerInfos();
                LicenseCheckModel checkModel  = new LicenseCheckModel("T",1,serverInfoMap);
                String activationCode = LicenseUtils.initLicense(checkModel);
                String path = jarFile.getParentFile() +"/keystore/";
                boolean issue = SystemFileCreateUtils.writeInitDataHub(activationCode,path,"keyCode");
                if(issue) {
                    log.info("\n----------------------------------------------------------\n\t" +
                            "系统授权归属所有权 ：" +checkModel.getManufacturer() +"\n\t"+
                            "系统授权信息 ：\n\t" +
                            "操作系统 ：" + serverInfoMap.get("OS") +"\n\t"+
                            "安装MAC ：" + serverInfoMap.get("MAC") +"\n\t"+
                            "BOS ：" + serverInfoMap.get("BOS") +"\n"+
                            "----------------------------------------------------------");
                }else {
                    log.info("系统授权安装失败......");
                    System.exit(-1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // ===== 当检测失败时, 停止项目启动
            System.exit(-1);
        }
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }


    public static boolean initSystem(File file) {

       // String newPath = file+"\\keystore\\keyCode.info";
        String newPath = file+"/keystore/keyCode.info";
        //String newPath = file+"\\keystore\\keyCode.lic";
        //System.out.println(newPath);
        File dir = new File(newPath);

        if (dir.exists()) {
            log.info("file exists");
            //System.out.println("file exists");
            return true;
        } else {
            log.info("file not exists, create it ...");
            //System.out.println("file not exists, create it ...");
            /*try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
            return false;
        }
    }

    /**
     * 获取激活码
     * @param fileName 激活码文件路径
     *
     */
    public static String getActivationCode(String fileName) throws IOException {

        String content = null;
        try{
            /*Resource resource = new ClassPathResource(fileName);
            InputStream inputStream = resource.getInputStream();
            StringBuilder out = new StringBuilder();
            byte[] b = new byte[4096];
            // 读取流
            for (int n; (n = inputStream.read(b)) != -1; ) {
                out.append(new String(b, 0, n));
            }
            content = out.toString();*/

            StringBuffer sb = new StringBuffer();
            File file = new File(fileName);
            Reader reader = new InputStreamReader(new FileInputStream(file));
            int ch=0;
            while ((ch = Objects.requireNonNull(reader).read()) != -1) {
                sb.append((char) ch);
            }
            reader.close();
            content = sb.toString();

        }catch (Exception e) {
            log.info("读取KEY文件失败......");
            e.printStackTrace();
            return  null;
        }
        return content;
    }

}
