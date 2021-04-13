package ew;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Properties;

import com.jcraft.jsch.Channel; 
import com.jcraft.jsch.ChannelSftp; 
import com.jcraft.jsch.JSch; 
import com.jcraft.jsch.JSchException; 
import com.jcraft.jsch.Session; 
import com.jcraft.jsch.SftpException;

public class SftpService {
    
    /// Fields
    private Session session = null; 
    private Channel channel = null; 
    private ChannelSftp channelSftp = null;

    private static final String host = "203.233.72.57";
    private static final String userName = "EXIMBAY001";
    private static final String password = "M5%Aur4e";
    private static final int port = 22;

    /// Constructor
    public SftpService(){
        
        // 초기화
        init();

        // SFTP 파일 다운로드
        fileDownload();

        // 연결 끊기
        disconnection();
    }

    /// Method
    public void init(){
        JSch jsch = new JSch();

        try{
            session = jsch.getSession(userName, host, port);
            session.setPassword(password);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();

        }catch(JSchException je){
            je.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        channelSftp = (ChannelSftp) channel;
    }

    public void fileDownload(){
        InputStream is = null; 
        FileOutputStream out = null;
        String date = "210322";
        
        // 파일 접근
        try { 
            channelSftp.cd("/kiccftp/EXIMBAY001"); 

            // 거래대사
            // is = channelSftp.get("EXIMBAY001-CRANS."+date);
            // 국세청 반송 대사
            is = channelSftp.get("EXIMBAY001-ERO."+date);
        } catch (SftpException e) {
            e.printStackTrace(); 
        } catch ( Exception e){
            e.printStackTrace();
        }
        
        // 읽은 데이터 파일 처리
        try {

            /// 파일 생성
            // 국세청 반송 대사
            File newFile = new File("." + "/log/국세청 반송 대사 "+date+".txt");
            // 현금영수증 대사
            // File newFile = new File("." + "/log/현금영수증 대사 "+date+".txt");

            out = new FileOutputStream(newFile);

            int readCount = 0;
            while((readCount = is.read())>0){
                out.write(readCount);
            }
            is.close();
            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnection() { 
        channelSftp.quit(); 
        channel.disconnect();
        session.disconnect();
    }
}