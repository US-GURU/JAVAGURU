package Serialization;

import java.io.*; 

class SerializationAndDeserialization implements java.io.Serializable 
{ 
	private static final long serialVersionUID = 1L;
	public int a; 
	public String b; 

	public SerializationAndDeserialization(int a, String b) 
	{ 
		this.a = a; 
		this.b = b; 
	} 

} 

class SerializationAndDeserializationExample 
{ 
	public static void main(String[] args) 
	{ 
		SerializationAndDeserialization object = new SerializationAndDeserialization(100, "Welcome"); 
		String filename = "file.txt"; 
		
		try
		{ 
			FileOutputStream file = new FileOutputStream(filename); 
			ObjectOutputStream out = new ObjectOutputStream(file); 
			
			out.writeObject(object); 
			
			out.close(); 
			file.close(); 
			
			System.out.println("Object has been serialized"); 

		} 
		
		catch(IOException ex) 
		{ 
			System.out.println("IOException is caught"); 
		} 


		SerializationAndDeserialization object1 = null; 

		try
		{ 
			FileInputStream file = new FileInputStream(filename); 
			ObjectInputStream in = new ObjectInputStream(file); 
			
			object1 = (SerializationAndDeserialization)in.readObject(); 
			
			in.close(); 
			file.close(); 
			
			System.out.println("Object has been deserialized "); 
			System.out.println("a = " + object1.a); 
			System.out.println("b = " + object1.b); 
		} 
		
		catch(IOException ex) 
		{ 
			System.out.println("IOException is caught"); 
		} 
		
		catch(ClassNotFoundException ex) 
		{ 
			System.out.println("ClassNotFoundException is caught"); 
		} 

	} 
} 
