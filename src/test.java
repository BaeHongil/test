import java.util.HashMap;
import java.util.Set;

public class test {
	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("bb", "22");
		map.put("aa", "11");
		
		Set set = map.entrySet();
		
		System.out.println(set);
	}
}
