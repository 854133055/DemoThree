package com.mlmOK.demothree.MD5;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author mml
 * @since 2018/6/6.
 */
public class BucketingCalculateTest {

    @Test
    public void testFormateDate2(){
        BucketingCalculate calculate = new BucketingCalculate();
        System.out.println(calculate.formateDate2(Arrays.asList("1车2座")));
    }

    @Test
    public void test4Log(){
        BucketingCalculate calculate = new BucketingCalculate();
        BClass bClass = new BClass();
        System.out.println(calculate.getLog());
        System.out.println(bClass.getALog());
    }
}