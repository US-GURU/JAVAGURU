package Lambda;

@FunctionalInterface
interface Interf { 
	public void methodOne();
}
	
	class LambdaExample { 
		public static void main(String[] args) {
			Interf i = () ->  System.out.println("MethodOne Execution");
			i.methodOne();  } 
		}

