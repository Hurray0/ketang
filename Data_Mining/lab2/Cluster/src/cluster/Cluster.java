/**
 * @Author hurray
 * @Part
 * @Note
 * @Encoding UTF-8
 * @Date 2014-12-03 10:41:34
 * @Copyright Hurray@BUPT
 * @MainPage http://www.ihurray.com
 *
 */
package cluster;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Math.abs;
import java.util.Random;
import java.util.Scanner;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;

/**
 * @Author hurray
 * @Class Cluster
 */
public class Cluster {

    public void makeSeed(int numOfCluster, int seed[]) {
        Random random = new Random();
        boolean vist[] = new boolean[5000];
        for (int i = 0; i < numOfCluster; i++) {
            int id = abs(random.nextInt() % 5000);
//            System.out.println("选用了："+id);
            while (vist[id]) {
                id = random.nextInt() % 5000;
            }
            vist[id] = true;
            seed[i] = id;
        }
    }

    public double distance(double data1[], double data2[]) {
        double sum = 0;
        for (int i = 0; i < data1.length; i++) {
            sum += (data1[i] - data2[i]) * (data1[i] - data2[i]);
        }
//        System.out.println("距离："+sum);
        return sum;
    }

    public int near(double data[][], int num, int seed[], int len_seed) {
        int i;
        int min_seed_id;
        double min_value, value;
        //计算距离所有seed点的距离，求最小的一个
        for (i = 0, min_value = distance(data[num], data[seed[0]]), min_seed_id = 0; i < len_seed; i++) {
            value = distance(data[num], data[seed[i]]);
//            System.out.println("num="+num+"现在value="+min_value+"，之后value="+value);
            if (value < min_value) {
                min_value = value;
                min_seed_id = i;
            }
        }
//        System.out.println("选用了："+min_seed_id);
        return min_seed_id;
    }

    public void markAllNearSeed(double data[][], int seed[], int len_seed, int dataNearSeedId[]) {
        for (int i = 0; i < data.length; i++) {
            dataNearSeedId[i] = near(data, i, seed, len_seed);
        }
    }

    public void showNearSeedId(int dataNearSeedId[], int len_seed) {
        int seedTime[] = new int[len_seed];
        for (int i = 0; i < len_seed; i++) {
            seedTime[i] = 0;
        }
        for (int i = 0; i < dataNearSeedId.length; i++) {
            seedTime[dataNearSeedId[i]]++;
//            System.out.println(dataNearSeedId[i]);
        }
        for (int i = 0; i < len_seed; i++) {
            System.out.println("第" + i + "簇有" + seedTime[i] + "个点！");
        }
    }

    public boolean move(double data[][], int seed[], int len_seed, int seed_id, int dataNearSeedId[]) {
        boolean TF = false;
        //只处理seed[seed_id]
        double originSeed[] = new double[10];
        double tempSum[] = new double[10];
        //记录原始种子点坐标
        for (int i = 0; i < data[0].length; i++) {
            originSeed[i] = data[seed[seed_id]][i];
            tempSum[i] = 0;
        }

        //开始移动
        int numOfSameSeed = 0;
        for (int i = 0; i < dataNearSeedId.length; i++) {
            if (dataNearSeedId[i] != seed_id) {
                continue;
            } else {
                numOfSameSeed++;
                for (int j = 0; j < data[i].length; j++) {
                    tempSum[j] += data[i][j];
                }
            }
        }

        for (int i = 0; i < data[0].length; i++) {
            data[seed[seed_id]][i] = tempSum[i] / numOfSameSeed;
            if (abs(data[seed[seed_id]][i] - originSeed[i]) > 0) {
                TF = true;
            }
        }
        return TF;
    }

    public boolean moveAllSeed(double data[][], int seed[], int len_seed, int dataNearSeedId[]) {

        boolean TF = false, temp = false;
        for (int i = 0; i < len_seed; i++) {
            temp = move(data, seed, len_seed, i, dataNearSeedId);
            if (TF == false) {
                TF = temp;
            }
        }
        return TF;
    }

    public boolean moveLoop(double data[][], int seed[], int len_seed, int dataNearSeedId[]) {
        boolean TF = true;
        int time = 0;
        do {
            System.out.println("第" + (++time) + "次聚类");
            TF = moveAllSeed(data, seed, len_seed, dataNearSeedId);
            markAllNearSeed(data, seed, len_seed, dataNearSeedId);
        } while (TF);
        return true;
    }

    public Cluster() {
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
            int[] dataNearSeedId = new int[5000];
            int[] seed = new int[5000];

            //获取指定单元格的对象引用   
            for (int i = 2; i < 5001; i++) {

                for (int j = 2; j < 11; j++) {

                    Cell cell = readsheet.getCell(j, i);

                    data[i - 2][j - 2] = Double.parseDouble(cell.getContents().toString());
//                    System.out.print(cell.getContents() + " ");
//                    out.write((cell.getContents()+" ").getBytes());

                }

//                System.out.println();
//                out.write(("\n").getBytes());
            }

            System.out.println("excel数据已经读取！");

            System.out.println("请输入聚类数目：");
            int numOfCluster = Integer.parseInt(br.readLine());

            // 获取一定数目的种子点
            makeSeed(numOfCluster, seed);
            //至此已经找到种子点，分别为data[seed[i]][]
            markAllNearSeed(data, seed, numOfCluster, dataNearSeedId);
            //showNearSeedId(dataNearSeedId);//展示一次聚类的结果

            moveLoop(data, seed, numOfCluster, dataNearSeedId);
            showNearSeedId(dataNearSeedId, numOfCluster);//展示一次聚类的结果
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            readwb.close();

        }
    }

    public static void main(String[] args) {
        new Cluster();
    }
}
