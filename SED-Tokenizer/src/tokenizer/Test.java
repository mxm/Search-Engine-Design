package tokenizer;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(find("hallo"));

	}
	public static boolean find(String word){
		String[] list = {"hallo","das","ist","ein","test"};
		
		//We use Binary Search to find a word

		int a = 0; int b = list.length - 1;
		while (a <= b){
			System.out.println("a: "+a+", b:"+b);
			int elem = (b-a)/2;
			if(list[elem].compareTo(word) < 0){
				b = elem -1;
			}else if(list[elem].compareTo(word) > 0){
				a = elem +1 ;
			}else{
				return true;
			}

		}
		return false;	
	}
}
