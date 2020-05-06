package Lambda;

interface Interface { 
	public int square(int x);  
	}

class SqrUsingLambda { 
	public static void main(String[] args) { 
		
		Interface i = x -> x*x; 
		System.out.println("The Square of 5 is"+i.square(5)); 
		
		} 
	}