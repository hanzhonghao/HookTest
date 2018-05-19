package utils;

public class check {

	public static boolean isNull(String s){
		if(s==null) return true;
		boolean r = true;
		for(int i=0;i<s.length();i++){
			if(s.substring(i, i+1).equals(" ")){
				continue;
			}
			else{
				r=false;
				break;
			}
		}
		return r;
	}
}
