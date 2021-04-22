package ew;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel; 
import com.jcraft.jsch.ChannelSftp; 
import com.jcraft.jsch.JSch; 
import com.jcraft.jsch.JSchException; 
import com.jcraft.jsch.Session; 
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class SftpClient {
    
    /// Fields
    private Session session = null; 
    private Channel channel = null; 
    private ChannelSftp channelSftp = null;

    private static final String host = ""; 
    private static final String userName = "";
    private static final String password = "";
    private static final int port = 22;

    /// Constructor
    public SftpClient(){
        
        // SFTP 연결
        connect();

        // SFTP 파일 다운로드
        fileDownload();

        // 연결 끊기
        disconnection();
    }

    /// Method
    public void connect(){

        JSch jsch = new JSch();

        try{
            // Jsch 세션 생성
            session = jsch.getSession(userName, host, port);
            session.setPassword(password);

            // 세션과 관련된 정보 설정
            Properties config = new Properties();
            // 호스트정보 검사 하지 않음.
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
        String date = "210330";
        String path = "";
        
        try { 

            // 경로 이동
            channelSftp.cd(path); 

            // 해당 경로 파일 리스트 받아오기
            Vector<ChannelSftp.LsEntry> fileList = channelSftp.ls(path);

            // LsEntry 건수 만큼 처리
            for(LsEntry entry : fileList){
                
                // 경로에서 받은 파일명 변수 처리
                String fileName = entry.getFilename();                

                // [. , ..] 경로 파일명이 아니면서, 파일 명에 -가 포함되어있고 요청하는 date로 끝나는 파일명 있는가?
                if(!fileName.equals(".") && !fileName.equals("..") && fileName.contains("-") && fileName.endsWith(date)){
                    System.out.println(fileName);

                    is = channelSftp.get(fileName);

                    File localFile = new File("./log/"+fileName);
                    out = new FileOutputStream(localFile);
                    int readCount = 0;
                    while( (readCount = is.read()) > 0 ){
                        out.write(readCount);
                    }
                    is.close();
                    out.close();
                }
            }

        } catch (SftpException se) {
            se.printStackTrace(); 
        } catch ( Exception e){
            e.printStackTrace();
        }
        
    }

    public void disconnection() { 
        channelSftp.quit(); 
        channel.disconnect();
        session.disconnect();
    }
}