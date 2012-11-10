package algorithm;

/**
 * 递推算法
 * @author xie041
 */
public class Recursive {

	/**
	 * <pre>
	 * 13世纪，斐波那契《算盘书》
	 * 一对两个月大的兔子以后每个月都会生一对小兔子，而一对新生的小兔子出生后二个月后才可生小兔子，那么一年后有多少小兔子？
	 * 
	 * int Fibonacci(n){
	 *   int t1,t2;
	 *   if(n==1 || n==2){
	 *     return 1;
	 *   }else{
	 *     t1 = Fibonacci(n-1);
	 *     t2 = Fibonacci(n-2);
	 *     return t1 + t2;
	 *   }
	 * }
	 * </pre>
	 * @param n
	 * @return
	 */
	public static int fibonacci(int n){
		int t1,t2;
		if(n==1 || n==2){
			return 1;
		}else{
			t1 = fibonacci(n-1);
			t2 = fibonacci(n-2);
			return t1 + t2;
		}
	}
	
	/**
	 * <pre>
	 * n! = n*(n-1)*(n-2)* ..... *2*1
	 * 
	 * 求解：n! = n*(n-1)!
	 * </pre>
	 * @param n
	 * @return
	 */
	public static int factorial(int n){
		if(n <= 1){
			return 1;
		}else{
			return n * factorial(n-1);
		}
	}
	
	public static void main(String[] args) {
		int n = fibonacci(12);
		System.out.println(n);
		System.out.println(factorial(6));
		System.out.println(factorial(0));
	}


	/**
	 * <pre>
	 * 需求：将目录下面的某个文件重命名-根据目录-并移动位置
	 * @return
	 * </pre>
	 */
	public static void main (String[] args) {
    		refreshFileList("G:\\java\\ajax");
	}

    	public static void refreshFileList(String path){
    		File dir = new File(path);
    		File[] files = dir.listFiles();
    		if(files == null) return;
    		for(int i=0;i<files.length;i++){
	    		if(files[i].isDirectory()){
	    			refreshFileList(files[i].getAbsolutePath());
	    		}else{
				
				//修改avi文件的名字
				File f1 = files[i];
				if(f1.getName().contains(".avi")){
					//avi文件，则重命名为目录一样的名字
					String p = f1.getParent();//父目录
					//new File().getParent()
					String docName = p.substring(p.lastIndexOf("\\")+1,p.length()-4);//文件名

					String fname = "G:\\java\\ajax\\"+docName+".avi";//指定新文件的位置
					File newFile = new File(fname);

					boolean bln = f1.renameTo(newFile);
					
					System.out.println(fname);
				}
    			}


    		}
    	}
}
