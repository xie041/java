package algorithm;

public class DivideAndConquer{

  public static int devide(int[] coin,int start,int end){
     //start从0开始
     int sum1=0,sum2=0;//用来求和，前半段和后半段
     int position = 0;//假币的位置
     
     //如果只有2个
     if(coin.length==2){
       if(coin[0]<coin[1]){
          position = 1;
          return position;
       }else{
          position = 2;
          return position;
       }
     }

     //超过2个，又分奇偶情况
     return position; 
  } 
  
  public static void main(String[] args){
      int[] coin = {2,1};
      int position = devide(coin,0,1);
      System.out.println(position);
  }
}
