package ew;

public class MaskingAlgorithm {

    /// Fields

    /// Constructor

    /// Method
    public static void main(String[] args) {

        // case 1. 주민등록번호
        String data = "9607201111111";
        // case 1-1. 주민등록번호 길이 측정.
        System.out.println("주민등록번호의 길이 : " + data.length());
        // case 1-2. 주민등록번호 앞자리의 길이 만큼 뺀 값(7)만큼 데이터 오른쪽을 Masking 처리
        String dataTest = getcashreceiptMaskingReplace(data, "right", data.length()-6);
        // case 1-3. 마스킹 처리된 데이터 확인
        System.out.println("마스킹 처리된 data : " + dataTest);

        // case 2. 전화번호
        String tele = "01012345678";
        // case 2-1. 전화번호 길이 측정
        System.out.println("전화번호의 길이 : " + tele.length());
        // case 2-2. 전화번호 마스킹 처리를 위해 파라미터 "tele" 전달
        String teleTest = getcashreceiptMaskingReplace(tele, "tele", 0);
        System.out.println("마스킹 처리된 tele : " + teleTest);

        // case 3. N 자릿수 형식
        String len = "1234567890";
        // case 3-1. N자릿수 형식의 길이 측정
        System.out.println("N 자릿수 데이터 형식의 길이 : " + len.length());     
        // case 3-2. 주민등록번호나, 전화번호의 형식이 아닐 경우 처리를 위해
        //           파라미터 "other"로 처리
        String lenTest = getcashreceiptMaskingReplace(len, "other", 0);       
        System.out.println("마스킹 처리된 N 자릿수 : " + lenTest);
  
    }

    /**
     * 
     * @param data 처리할 데이터
     * @param location 처리할 파라미터 (tele, other, right, left)
     * @param unit right, left 처리시 처리할 자릿수 전달하기 위한 파라미터
     * @return
     */
    public static String getcashreceiptMaskingReplace(String data, String location, int unit) {
        StringBuffer replaceData = new StringBuffer(); // 결과값
        int len = 0; // 문자열길이
        len = data.length();
        String regex = ""; // 타입에 맞는 마스킹 처리
        
        StringBuffer masking = new StringBuffer();
        if ( "tele".equals(location) ) {
            data = data.replace(" ", "");
            len = data.length();
            
            replaceData.append(data.substring(0, 3));
            for ( int i = 0; i < 4; i++ ) {
                masking.append("*");
            }
            replaceData.append(masking);
            replaceData.append(data.substring(7, len));
            
        }  else if ( "other".equals(location) ) {
            
        	 data = data.replace(" ", "");
             len = data.length();
             
             replaceData.append(data.substring(0, 3));
             for ( int i = 0; i < 6; i++ ) {
                 masking.append("*");
             }
             replaceData.append(masking);
             replaceData.append(data.substring(7, len));
        } else if ( "right".equals(location) ) {
            
            for ( int i = 0; i < unit; i++ ) {
                masking.append("*");
            }
            replaceData.append(data.replace(data.substring(data.length() - unit, data.length()), ""));
            replaceData.append(masking);
        } else if ( "left".equals(location) ) {
            for ( int i = 0; i < unit; i++ ) {
                masking.append("*");
            }
            replaceData.append(data.replace(data.substring(0, unit), ""));
            replaceData.append(masking);
        }
        
        
        return replaceData.toString();
    }

}
