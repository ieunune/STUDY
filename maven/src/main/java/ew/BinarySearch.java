import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BinarySearch{

    /// Fields

    /// Constructor
    public BinarySearch(){

    }

    /// Method
    public static void main(String args[]){

        // 데이터 정렬을 위해 ArrayList 생성
        List<String> strList = new ArrayList<String>();
        // 데이터 리스트
        String[] data = {"쥐","소","호랑이","토끼","용","뱀","말","양","원숭이","닭","개","돼지"};
        // 검색대상
        String target = "용";

        // String[] -> List
        for(String temp : data){
            strList.add(temp);
        }
        // 정렬 Default : ASC 오름차순
        Collections.sort(strList);

        // 리스트 디버그
        for(int i = 0 ; i < strList.size() ; i++){
            System.out.println(" [" + i + "] " + strList.get(i));
        }

        int dataLength = strList.size();
        int index = new BinarySearch().binarySearch(dataLength, strList, target);

        System.out.println(" 검색하신 [" + target + "] 은  리스트를 오름차순으로 정리하였을때 [" + index + "] 위치에 있습니다.");
        

    }

    /**
     * 2진 검색 기능을 수행하는 메소드
     * @param dataLength 데이터리스트의 길이
     * @param strList 데이터리스트
     * @param target 검색대상 문자열
     * @return
     */
    public int binarySearch(int dataLength, List<String> strList, String target){

        int begin = 0;
        int end = dataLength -1;

        // 데이터가 1건이라도 있을 때 수행할 수 있도록 <= 사용
        while (begin <= end) {

            // 중간 값을 찾기 위해 2로 나눈다.
            int mid = (begin + end) / 2;

            // 검색대상이 리스트의 가운데 값과 같은지 체크
            if(target.equals(strList.get(mid))) {
                return mid;
            } else {
                
                // 검색대상값이 비교대상과 비교하여 -인 경우(사전순으로 앞에있는경우)
                if(target.compareTo(strList.get(mid)) < 0){
                    end = mid - 1;
                } 
                // 검색대상값이 비교대상과 비교하여 +인 경우(사전순으로 뒤에있는경우)
                else {
                    begin = mid + 1;
                }

            }
        }

        return 0;
    }
}
