package com.lyq.registry.zk.util;

import com.lyq.enums.RpcConfigEnum;
import com.lyq.utils.PropertiesFileUtil;

import java.net.URL;
import java.util.Properties;

public class PropertiesTest {
    public static void main(String[] args) {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        System.out.println("url = " + url);

        Properties properties = PropertiesFileUtil.readPropertiesFile(RpcConfigEnum.RPC_CONFIG_PATH.getPropertyValue());

        String property = properties.getProperty(RpcConfigEnum.ZK_ADDRESS.getPropertyValue());
        System.out.println("property = " + property);
    }
}
