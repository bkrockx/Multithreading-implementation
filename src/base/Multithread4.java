package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class testThread2 implements Runnable {
		
		private Thread t;
		private String threadName;
		private File src;
		private File dest;
		boolean suspended = false;
		
		testThread2(String name,File source,File destination){
			threadName = name;
			src = source;
			dest = destination;
			System.out.println("Creating " +  threadName );
		}

		@Override
		public void run() {
			System.out.println("Running " +  threadName );
			try{
	        	copyFolder(src,dest);
	           }catch(IOException e){
	        	e.printStackTrace();
	                System.exit(0);
	           }
		}
		
		public static void copyFolder(File src, File dest)
		    	throws IOException{
		 
		    	if(src.isDirectory()){
		    		if(!dest.exists()){
		    		   dest.mkdir();
		    		   System.out.println("Directory copied from " 
		                              + src + "  to " + dest);
		    		}
		 
		    		String files[] = src.list();
		 
		    		for (String file : files) {
		    		   File srcFile = new File(src, file);
		    		   File destFile = new File(dest, file);
		    		   copyFolder(srcFile,destFile);
		    		}
		 
		    	}else{
		    			InputStream in = new FileInputStream(src);
		    	        OutputStream out = new FileOutputStream(dest); 
		    	        byte[] buffer = new byte[1024];
		    	        int length;
		    	        while ((length = in.read(buffer)) > 0){
		    	    	   out.write(buffer, 0, length);
		    	        }
		 
		    	        in.close();
		    	        out.close();
		    	        System.out.println("File copied from " + src + " to " + dest);
		    	}
		    }

		public void start(){
			System.out.println("Starting " +  threadName );
			if(t==null){
				 t = new Thread (this, threadName);
				 t.start();
			}
		}
		
	}

	public class Multithread4 {
		   public static void main(String args[]) throws InterruptedException {
			   
			   File srcFolder = new File("C:\\Users\\bablu.kumar\\Desktop\\f1");
			   
			   File[] files = srcFolder.listFiles();
			   System.out.println("files listed");
			   
			   testThread2[] t=new testThread2[files.length];
			   
			   boolean[] copy = new boolean[files.length];
			   
			   ExecutorService pool = Executors.newFixedThreadPool(files.length);
			   
			   int i = 0;
			   for(File f:files){
				   String t1 = "t"+i;
				   System.out.println(t1);
				   File dest1 = new File("C:\\Users\\bablu.kumar\\Desktop\\f2"+"\\"+f.getName());
				   Runnable task = new testThread2(t1,(f.getAbsoluteFile()),new File("C:\\Users\\bablu.kumar\\Desktop\\f2"+"\\"+f.getName()));
				   pool.execute(task);
				   i++;
			   }
			   
			   pool.shutdown();
			   try {
				   pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				} 
			   catch (InterruptedException e) {
				   e.printStackTrace();
				}
			  
			   System.out.println("copying completed");
			   
		   }  
		   
	}
