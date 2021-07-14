package com.app.dmm;

import com.app.dmm.core.utils.oConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
public class DmmApplication {

    public static void main(String[] args) throws UnknownHostException {
        //SpringApplication.run(DmmApplication.class, args);
        ConfigurableApplicationContext application = SpringApplication.run(DmmApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = oConvertUtils.getString(env.getProperty("server.servlet.context-path"));
        log.info("\n------------------------------------------------------------------\n\t" +
                "Application started  successfully ! Access URLs:\n\t" +
                "Local        : \thttp://localhost:" + port + path + "/\n\t" +
                "External     : \thttp://" + ip + ":" + port + path + "/\n\t" +
                "druid        : \thttp://" + ip + ":" + port + path + "/druid/sql.html\n\t" +
                "Swagger 文档 : \thttp://" + ip + ":" + port + path + "/swagger-ui.html\n" +
                "------------------------------------------------------------------");
    }
    //允许所有跨域请求
}
