package com.mlmOK.demothree.MD5;




import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.mlmOK.demothree.MD5.AbBucket.BUCKET_A;
import static com.mlmOK.demothree.MD5.AbBucket.BUCKET_B;
import static com.mlmOK.demothree.MD5.AbBucket.BUCKET_C;
import static com.mlmOK.demothree.MD5.AbBucket.BUCKET_D;
import static com.mlmOK.demothree.MD5.AbBucket.BUCKET_E;
import static com.mlmOK.demothree.MD5.AbBucket.BUCKET_F;


/**
 * @author molei.li
 * @since 2018/3/29.
 */
public class HOMEBucketingTest {

    private double sum;//总数

    @Test
    public void testGetBucketing() throws Exception {
        Map<String, Integer> result = testFromFile();
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            System.out.println("桶名称：" + entry.getKey() + "个数：" + entry.getValue() + "百分比：" + entry.getValue() / sum);
        }
    }

    @Test
    public void testOneUid(){
        String uid = "863125033033912";
        BucketingCalculate calculate = new BucketingCalculate();
        System.out.println(calculate.bucketing(uid));
    }

    public Map<String, Integer> testFromFile() throws IOException {
        BucketingCalculate calculate = new BucketingCalculate();
        BucketingCalculate1 calculate1 = new BucketingCalculate1();
        Map<String, Integer> count = new HashMap<>();
        int lineCount = 0;
        try (InputStreamReader inputReader = new InputStreamReader(new FileInputStream("/Users/mml/Desktop/uidOut.txt"));
             BufferedReader reader = new BufferedReader(inputReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                line = line.trim();
                String result = calculate.bucketing(line);

                switch (result) {
                    case BUCKET_A:
                        int a = count.get(BUCKET_A) == null ? 0 : count.get(BUCKET_A);
                        count.put(BUCKET_A, a + 1);
                        break;
                    case BUCKET_B:
                        int b = count.get(BUCKET_B) == null ? 0 : count.get(BUCKET_B);
                        count.put(BUCKET_B, b + 1);
                        break;
                    case BUCKET_C:
                        int c = count.get(BUCKET_C) == null ? 0 : count.get(BUCKET_C);
                        count.put(BUCKET_C, c + 1);
                        break;
                    case BUCKET_D:
                        int d = count.get(BUCKET_D) == null ? 0 : count.get(BUCKET_D);
                        count.put(BUCKET_D, d + 1);
                        break;
                    case BUCKET_E:
                        int e = count.get(BUCKET_E) == null ? 0 : count.get(BUCKET_E);
                        count.put(BUCKET_E, e + 1);
                        break;
                    case BUCKET_F:
                        int f = count.get(BUCKET_F) == null ? 0 : count.get(BUCKET_F);
                        count.put(BUCKET_F, f + 1);
                        break;
                }
            }
        }
        System.out.println("总数：" + lineCount);
        sum = lineCount;
        return count;
    }

}