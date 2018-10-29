package com.zhw.blog.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.*;

/**
 * 功能说明: request请求处理工具类<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author <br>
 * 开发时间: 2015年7月21日<br>
 */
public class BeanUtil {

    /**
     * 静态取request，本地junit非容器运行时无法使用，服务未启动完成无法使用
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            if (attr != null) {
                return attr.getRequest();
            }
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 取完整根路径，防止重复contextpath
     *
     * @param request
     * @param page
     * @return
     */
    public static String getRootURL(HttpServletRequest request, String page) {
        StringBuffer url = request.getRequestURL();
        String rootUrl = url.substring(0, url.indexOf(request.getRequestURI()));
        if (StringUtils.isEmpty(page)) {
            return rootUrl;
        }
        if (!rootUrl.endsWith("/") && !page.startsWith("/")) {
            rootUrl = rootUrl + "/";
        }
        return rootUrl + page;
    }

    /**
     * 取重定向完整根路径，防止重复contextpath
     *
     * @param request
     * @param page
     * @return
     */
    public static String getRootRedirectURL(HttpServletRequest request, String page) {
        StringBuffer url = request.getRequestURL();
        String redirect = url.substring(0, url.indexOf(request.getRequestURI()));

        String host = request.getHeader("Real-Host");
        if (StringUtils.isNotBlank(host)) {
            redirect = redirect.replace(request.getHeader("Host"), host);
        }
        if (redirect.startsWith("http:")) {
            String referer = request.getHeader("referer");
            if (StringUtils.isNotBlank(referer) && referer.startsWith("https")) {
                redirect = redirect.replace("http:", "https:");
            }
        }
        if (StringUtils.isEmpty(page)) {
            return "redirect:" + redirect;
        }
        if (!redirect.endsWith("/") && !page.startsWith("/")) {
            redirect = redirect + "/";
        }
        return "redirect:" + redirect + page;
    }

    /**
     * 取IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * @param request
     * @return
     * @category 获取request参数
     */
    public static Map<String, String> request2Map(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String name = enums.nextElement();
            String value = request.getParameter(name);
            if (request.getParameterValues(name) != null) {
                value = StringUtils.join(request.getParameterValues(name), ",");
            }
            map.put(name, value);
        }
        return map;
    }

    /**
     * windows服务器获取客户端mac地址
     *
     * @return
     */
    public static String getMAC() {
        String mac = null;
        try {
            Process pro = Runtime.getRuntime().exec("cmd.exe /c ipconfig/all");

            InputStream is = pro.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String message = br.readLine();

            int index = -1;
            while (message != null) {
                if ((index = message.indexOf("Physical Address")) > 0) {
                    mac = message.substring(index + 36).trim();
                    break;
                }
                message = br.readLine();
            }
            System.out.println(mac);
            br.close();
            pro.destroy();
        } catch (IOException e) {
            System.out.println("Can't get mac address!");
            return null;
        }
        return mac;
    }


    public static String getMacAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            byte[] mac = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    mac = netInterface.getHardwareAddress();
                    if (mac != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        if (sb.length() > 0) {
                            return sb.toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static void main(String[] s) {

        System.out.println(getMacAddress());
    }


    /**
     * 从request里面获取json字符串并转换为javaList集合
     */
    public static <T> List<T> parseJsonToList(HttpServletRequest request, String key, Class<T> clazz) {
        String jsonText = request.getParameter(key);
        if (StringUtils.isEmpty(jsonText)) return null;
        jsonText = StringEscapeUtils.unescapeHtml(jsonText);
        return JSON.parseArray(jsonText).toJavaList(clazz);
    }

    /**
     * 从request里面获取json字符串并转换为javaObject对象
     */
    public static <T> T parseJsonToJavaBean(HttpServletRequest request, String key, Class<T> clazz) {
        String jsonText = request.getParameter(key);
        if (StringUtils.isEmpty(jsonText)) return null;
        jsonText = StringEscapeUtils.unescapeHtml(jsonText);
        return JSON.parseObject(jsonText).toJavaObject(clazz);
    }

    /**
     * Object转实体
     */
    public static <T> T parseObjectToJaveBean(Object obj, Class<T> clazz) {
        String json = JSON.toJSONString(obj);
        return obj == null ? null : JSON.parseObject(json).toJavaObject(clazz);
    }

    /**
     * List<Object>转List<实体>
     */
    public static <T> List<T> parseListToList(List<Object> list, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                result.add(parseObjectToJaveBean(obj, clazz));
            }
        }
        return result;
    }

    /**
     * List<T>转List<实体>
     */
    public static <T> List<T> parseListEntityToList(List<T> list, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                result.add(parseObjectToJaveBean(obj, clazz));
            }
        }
        return result;
    }

}
