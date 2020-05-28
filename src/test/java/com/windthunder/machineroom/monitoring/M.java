package com.windthunder.machineroom.monitoring;

import org.apache.http.util.TextUtils;

import java.io.*;
import java.net.Socket;

public class M {

    public static void main(String[] args) throws Exception {
        //创建客户端的Socket服务，指定目的主机和端口
        Socket socket = new Socket("192.168.1.17", 20002);//此处输入ip地址和端口
        OutputStream outputStream = socket.getOutputStream();

        //01 05 00 00 FF 00 8C 3A       开
        //01 05 00 00 00 00 CD CA       关
        String s = "01 05 00 00 00 00 CD CA";
        byte[] bytes = hexStrToBinaryStr(s);
        outputStream.write(bytes);

        //获取服务端返回的数据
        InputStreamReader in=new InputStreamReader(socket.getInputStream());
        /*InputStream in = socket.getInputStream();*/
        try {
            // 反复将反馈信息显示在控制台屏幕上
            while (true) {
                int b = in.read();
                if (b == -1) {
                    System.exit(0);
                }
                // 将数据显示在控制台屏幕上
                System.out.print(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 将16进制的字符串转化为byte数组（数据需要先去除空格）
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStrToBinaryStr(String hexString) {
        if (TextUtils.isEmpty(hexString)) {
            return null;
        }
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        int index = 0;
        byte[] bytes = new byte[len / 2];
        while (index < len) {
            String sub = hexString.substring(index, index + 2);
            bytes[index / 2] = (byte) Integer.parseInt(sub, 16);
            index += 2;
        }
        return bytes;
    }


    /**
     * 字节数组转16进制
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
