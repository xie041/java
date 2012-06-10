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
}
