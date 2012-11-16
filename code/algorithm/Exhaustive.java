package algorithm;

import java.util.Scanner;


/**
 * 穷举法
 * @author xie041
 */
public class Exhaustive {
	
	/**
	 * 1500年前《孙子算经》：今有鸡兔同笼，上有三十五头，下有九十四足，问鸡兔各几何？
	 * @param head
	 * @param foot
	 */
	public static int sun(int head,int foot){
		int i,j;//i rabbit  j chicken
		int flag = 0;
		for(i=0;i<=head;i++){
			j = head - i;
			if((i*4 + j*2)==foot){
				System.out.println("兔子："+i + "只    鸡："+j+"只\n");
				flag = 1;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 总共55个碗，一个人一个菜碗，二个人一个饭碗，三个人一个汤碗，求总共有多少人？
	 * @param bowl
	 */
	public static void person(int bowl){
		int p;//  17<p<55 这是粗略估算，属于编程优化
		for(p=15;p<55;p++){
			if((p+p/2+p/3)==bowl){
				System.out.println("总共有："+p+"人   分解：饭碗->"+p+"   菜碗->"+ p/2 +"   汤碗->"+p/3);
				break;
			}
		}
	}
	
	public static void main(String[] args) {
//		sun(35, 94);
//		person(55);
		StringBuffer bf = new StringBuffer("总共有两题：\n\n");
		bf.append("1. 1500年前《孙子算经》：今有鸡兔同笼，上有三十五头，下有九十四足，问鸡兔各几何？\n");
		bf.append("2. 总共55个碗，一个人一个菜碗，二个人一个饭碗，三个人一个汤碗，求总共有多少人？\n\n");
		bf.append("请输入题号:\n");
		System.out.println(bf.toString());
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();//题号
		int head ,foot,bowl;
		if(n == 1){
			System.out.println("请输入头数目：");
			head = scan.nextInt();
			System.out.println("请输入足数目：");
			foot = scan.nextInt();
			int flag = sun(head, foot);
			if(flag == 0){
				System.out.println("您的输入有误，无法进行求解");
			}
		}else if(n == 2){
			System.out.println("请输入总碗数目：");
			bowl = scan.nextInt();
			person(bowl);
		}
	}
}
