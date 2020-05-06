package Collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
public class UnmodifiableCollectionDemo {
    public static void main(String[] args) {
    	ArrayList<String> modifiable = new ArrayList<String>();
    	modifiable.add("1111");
    	Collection<String> unmodifiable = Collections.unmodifiableCollection(modifiable);
    	try {
    		unmodifiable.add("2222"); //will throw error
    	}catch(UnsupportedOperationException e) {
    		System.out.println("UnsupportedOperationException-----");
    	}
    	for(String s : unmodifiable){
    		System.out.println(s);
    	}
    }
} 
