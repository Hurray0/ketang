/**
 * @Author hurray
 * @Part Dbscan
 * @Note DBscan算法实现
 * @Encoding UTF-8
 * @Date 2014-12-03 11:27:24
 * @Copyright Hurray@BUPT
 * @MainPage http://www.ihurray.com
 *
 */

package cluster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


/**
 * @Author hurray
 * @Class Dbscan
 */
public class Dbscan {

    public static final int MinPts = 20;   //密度
    public static final double Eps = 40;    //区域半径

    public boolean distanceOK(double data1[], double data2[]) {
        double distance = 0;
        int k;
        for (k = 0; k < data1.length; k++) {
            distance += (data1[k] - data2[k]) * (data1[k] - data2[k]);
        }
        if (distance <= (Eps * Eps)) {
            return true;
        } else {
            return false;
        }
    }

    public void MarkAllPoint(double data[][], int mark[]) {
        int i, j, k, sum;
        //i是所有的点
        for (i = 0; i < data.length; i++) {
            //j是另一个点
            for (j = 0, sum = 0; j < data.length; j++) {

                if (distanceOK(data[i], data[j])) {
                    sum++;
                }
            }
            if (sum == 0) {
                mark[i] = 3;
            } else if (sum >= MinPts) {
                mark[i] = 1;
            } else {
                mark[i] = 2;
            }
//            System.out.println("标记" + i + "个点为属性" + mark[i]);
        }
    }

    public void DBscan(double data[][], int dataBelong[], int mark[], int visit[]) {
        //先标记所有的点到mark[]，区分1为核心点，2为边缘点，3为噪声点
        MarkAllPoint(data, mark);

        for (int i = 0; i < mark.length; i++) {
            if (mark[i] != 1 || visit[i] == 1) {
                continue;
            } else {
                visit[i] = 1;
                ACluster(data, dataBelong, mark, visit, i);
            }
        }
    }

    public void ACluster(double data[][], int dataBelong[], int mark[], int visit[], int thisID) {
        for (int i = 0; i < mark.length; i++) {
            //在距离范围内，包含到簇中
            if (distanceOK(data[thisID], data[i])) {
                if (dataBelong[thisID] == -1) {
                    dataBelong[thisID] = thisID;
                }
                dataBelong[i] = dataBelong[thisID];
            }
        }

        for (int i = 0; i < mark.length; i++) {
            if (mark[i] != 1 || dataBelong[i] != dataBelong[thisID] || visit[i] == 1) {
                continue;
            } else {
                visit[i] = 1;
                ACluster(data, dataBelong, mark, visit, i);
            }
        }
    }

    public void showMarks(int mark[]) {
        for (int i = 0; i < mark.length; i++) {
            System.out.println(mark[i]);
        }
    }

    public void showBelong(int dataBelong[]) {
        for (int i = 0; i < dataBelong.length; i++) {
            System.out.println(i + "." + dataBelong[i]);
        }
    }

    public void showClusterDotNum(int dataBelong[]) {
        int num[] = new int[5000];
        int time = 0;
        for (int i = 0; i < dataBelong.length; i++) {
            int temp = dataBelong[i];
            if (temp != -1) {
                num[temp]++;
            }
        }
        for (int i = 0; i < num.length; i++) {
            if (num[i] == 0) {
                continue;
            } else {
                time++;
                System.out.println("第" + time + "个簇含" + num[i] + "个元素");
            }
        }
    }

    public Dbscan() {
        jxl.Workbook readwb = null;

        try {

            //构建Workbook对象, 只读Workbook对象   
            //直接从本地文件创建Workbook   
            InputStream instream = new FileInputStream("./实验二参考数据.xls");
            FileOutputStream out = new FileOutputStream(new File("output.txt"));
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);
            readwb = Workbook.getWorkbook(instream);

            //Sheet的下标是从0开始   
            //获取第一张Sheet表   
            Sheet readsheet = readwb.getSheet(0);

            double[][] data = new double[5000][10];
            int[] dataBelong = new int[5000];
            int[] mark = new int[5000];//1为核心点，2为边缘点，3为噪声点
            int[] visit = new int[5000];

            for (int i = 0; i < dataBelong.length; i++) {
                dataBelong[i] = -1;
            }

            //获取指定单元格的对象引用   
            for (int i = 2; i < 5002; i++) {

                for (int j = 2; j < 12; j++) {

                    Cell cell = readsheet.getCell(j, i);

                    data[i - 2][j - 2] = Double.parseDouble(cell.getContents().toString());
//                    System.out.print(cell.getContents() + " ");
//                    out.write((cell.getContents()+" ").getBytes());

                }

//                System.out.println();
//                out.write(("\n").getBytes());
            }

            System.out.println("excel数据已经读取！");
            DBscan(data, dataBelong, mark, visit);
//            showMarks(mark);
            showBelong(dataBelong);
            showClusterDotNum(dataBelong);

            //程序正式开始
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            readwb.close();

        }
    }

    public static void main(String[] args) {
        new Dbscan();
    }
}
