package apiutility;

import java.util.Date;
import java.util.Random;

public class RandomHelper {
    /// <summary>
    /// 生成一个指定范围的随机整数，该随机数范围包括最小值，但不包括最大值
    /// </summary>
    /// <param name="minNum">最小值</param>
    /// <param name="maxNum">最大值</param>
    public static int GetRandomInt(int minNum, int maxNum) {
        int result = new Random().nextInt(maxNum);
        if(minNum > result) {
            GetRandomInt(minNum,maxNum);
        }
        return result;
    }

    /// <summary>
    /// 生成一个0.0到1.0的随机小数
    /// </summary>
    public static double GetRandomDouble() {
        return new Random().nextDouble();
    }

    /// <summary>
    /// 对一个数组进行随机排序
    /// </summary>
    /// <typeparam name="T">数组的类型</typeparam>
    /// <param name="arr">需要随机排序的数组</param>
    public static <T> void GetRandomArray(T[] arr) {
        //对数组进行随机排序的算法:随机选择两个位置，将两个位置上的值交换

        //交换的次数,这里使用数组的长度作为交换次数
        int count = arr.length;

        //开始交换
        for (int i = 0; i < count; i++)
        {
            //生成两个随机数位置
            int randomNum1 = GetRandomInt(0, arr.length);
            int randomNum2 = GetRandomInt(0, arr.length);

            //定义临时变量
            T temp;

            //交换两个随机数位置的值
            temp = arr[randomNum1];
            arr[randomNum1] = arr[randomNum2];
            arr[randomNum2] = temp;
        }
    }

    // 一：随机生成不重复数字字符串
    public static String GenerateCheckCodeNum(int codeCount) {
        int rep = 0;
        String str = "";
        long num2 = new Date().getTime() + rep;
        rep++;
        Random random = new Random(((int)(((long)num2) & 0xffffffffL)) | ((int)(num2 >> rep)));
        for (int i = 0; i < codeCount; i++)
        {
            int num = random.nextInt();
            str = str + ((char)(0x30 + ((short)(num % 10))));
        }
        return str;
    }

    //方法二：随机生成字符串（数字和字母混和）
    public static String GenerateCheckCode(int codeCount) {
        int rep = 0;
        String str = "";
        long num2 = new Date().getTime() + rep;
        rep++;
        Random random = new Random(((int)(((long)num2) & 0xffffffffL)) | ((int)(num2 >> rep)));
        for (int i = 0; i < codeCount; i++)
        {
            char ch;
            int num = random.nextInt();
            if ((num % 2) == 0)
            {
                ch = (char)(0x30 + ((short)(num % 10)));
            }
            else
            {
                ch = (char)(0x41 + ((short)(num % 0x1a)));
            }
            str = str + ch;
        }
        return str;
    }

    /// <summary>
    /// 从字符串里随机得到，规定个数的字符串.
    /// </summary>
    /// <param name="allChar"></param>
    /// <param name="CodeCount"></param>
    /// <returns></returns>
    private String GetRandomCode(String allChar, int CodeCount) {
        //String allChar = "1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,i,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
        String[] allCharArray = allChar.split(",");
        String RandomCode = "";
        int temp = -1;
        Random rand = new Random();
        for (int i = 0; i < CodeCount; i++)
        {
            if (temp != -1)
            {
                rand = new Random(temp * i * ((int)new Date().getTime()));
            }

            int t = rand.nextInt(allCharArray.length - 1);

            while (temp == t)
            {
                t = rand.nextInt(allCharArray.length - 1);
            }

            temp = t;
            RandomCode += allCharArray[t];
        }
        return RandomCode;
    }
}
