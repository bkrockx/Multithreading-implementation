package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class testThread1 implements Runnable {
		
		private Thread t;
		private String threadName;
		private File src;
		private File dest;
		boolean suspended = false;
		
		testThread1(String name,File source,File destination){
			threadName = name;
			src = source;
			dest = destination;
			System.out.println("Creating " +  threadName );
		}

		@Override
		public void run() {
			System.out.println("Running " +  threadName );
			FileChannel inputChannel = null;
			FileChannel outputChannel = null;
			try{
				inputChannel = new FileInputStream(src).getChannel();
				outputChannel = new FileOutputStream(dest).getChannel();
				outputChannel.transferFrom(inputChannel,0,inputChannel.size());
				System.out.println(threadName+" "+"executed");
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				try {
					inputChannel.close();
					outputChannel.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
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

	public class Multithread3 {
		   public static void main(String args[]) throws InterruptedException {
			   
			   File srcFolder = new File("C:\\Users\\bablu.kumar\\Desktop\\f1");
			   
			   File[] files = srcFolder.listFiles();
			   System.out.println("files listed");
			   
			   testThread1[] t=new testThread1[files.length];
			   
			   boolean[] copy = new boolean[files.length];
			   
			   ExecutorService pool = Executors.newFixedThreadPool(files.length);
			   
			   int i = 0;
			   for(File f:files){
				   String t1 = "t"+i;
				   System.out.println(t1);
				   File dest1 = new File("C:\\Users\\bablu.kumar\\Desktop\\f2"+"\\"+f.getName());
				   Runnable task = new testThread1(t1,(f.getAbsoluteFile()),new File("C:\\Users\\bablu.kumar\\Desktop\\f2"+"\\"+f.getName()));
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
