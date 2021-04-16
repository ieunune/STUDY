package ew;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpClient{

	/// Fields
	private static final Boolean isLive = false;

	/// Constructor
	public FtpClient(){
		System.out.println("CREATE SFTP SERVICE APACHE");
		getFTP();
	}

	/// Method
	public static void getFTP(){

		// FTP 통신 후 받아올 파일 리스트
		ArrayList<String> arrList = new ArrayList<String>();
		
		String sftpIP = "";
		if(isLive) sftpIP = "203.233.72.11";
		else sftpIP = "203.233.72.57";

		int sftpPort = 22;
		String sftpID = "EXIMBAY001";
		String sftpPW = "M5%Aur4e";
		String serverFilePath = "/kiccftp/EXIMBAY001";
		
		FTPClient ftp = new FTPClient();
		FTPFile[] ftpFiles = null;
		FileOutputStream fos = null;
		
		System.out.println( " IP : " + sftpIP + ", PORT : " + sftpPort + ", sftpID : " + sftpID + ", sftpPW : " + sftpPW);

		try {
			ftp.setControlEncoding("EUC_KR");
			ftp.setConnectTimeout(30 * 1000); // 10s
			ftp.connect(sftpIP);

			System.out.println(" [CONNECT] REPLY CODE : " + ftp.getReplyCode());
			
			boolean isLogin = ftp.login( sftpID, sftpPW );

			System.out.println(" [LOGIN] REPLY CODE : " + ftp.getReplyCode());

			if ( isLogin ){

				ftp.enterLocalPassiveMode();

				ftp.cwd( serverFilePath );
				System.out.println( " printWorkingDirectory :  " + ftp.printWorkingDirectory() );

				ftpFiles = ftp.listFiles();

				System.out.println( " ftpFiles length : " + ftpFiles.length );
			} else {
				System.out.println(" 로그인 실패 ");
				ftp.disconnect();
				// return null;
			}

			// 파일 디렉토리 생성
			File localFile = new File("./log/");
			if( !localFile.isDirectory() ) {
				localFile.mkdirs();
			}

			if( ftpFiles == null || ftpFiles.length == 0){
				System.out.println(" FTP FILES 파일 없음 ");
			} else {

				for( int i = 0 ; i < ftpFiles.length; i ++){

					File downloadFile = new File( localFile.getAbsolutePath() + "/" + ftpFiles[i].getName() );
					fos = new FileOutputStream( downloadFile );

					if( ftp.retrieveFile(ftpFiles[i].getName(), fos) ) {
						
						System.out.println(" 파일 생성 : " + ftpFiles[i].getName());
						arrList.add( localFile.getAbsolutePath() + "/" + ftpFiles[i].getName() );

					} else {
						System.out.println(" 리스트에 안들어간 : " + ftpFiles[i].getName());
					}
				}

			}

			if( ftp != null && ftp.isConnected() )
			ftp.disconnect();
			if( fos != null )
			fos.close();

		} catch (Exception e){
			e.printStackTrace();
		}

		// return arrList;
	}
}