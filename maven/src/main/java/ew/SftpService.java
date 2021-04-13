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
        BufferedInputStream bis = null; 
        InputStreamReader isr = null; 
        OutputStreamWriter osw = null; 
        OutputStream outStream = null; 
        
        try { 
            channelSftp.cd("/kiccftp/EXIMBAY001"); 
            bis = new BufferedInputStream(channelSftp.get("EXIMBAY001-CRANS.210322"));
            isr = new InputStreamReader(channelSftp.get("EXIMBAY001-CRANS.210322"),"UTF-8");
        } catch (SftpException e) {
            e.printStackTrace(); 
        } catch ( Exception e){
            e.printStackTrace();
        }
        
        byte[] buffer = new byte[1024];
        String fileName = "TEST";
        try {


            File newFile = new File("." + "/TEST.txt");

            // Download file
            Writer os = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile),"UTF-8"));
            // BufferedOutputStream bos = new BufferedOutputStream(bis);
            os.write("가나다라마바사");
            os.close();

            // int readCount = 0;
            // while((readCount = isr.read())>0){
            //     bos.write(readCount);
            // }
            // bis.close();
            // bos.close();
            // int readCount;
            // while ((readCount = bis.read(buffer)) > 0) {
            //     bos.write(buffer, 0, readCount);
            // }
            // bis.close();
            // bos.close();

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