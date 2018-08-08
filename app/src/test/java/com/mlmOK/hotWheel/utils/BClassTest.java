package com.mlmOK.hotWheel.utils;

import org.junit.Test;

/**
 * @author mml
 * @since 2018/6/11.
 */
public class BClassTest {

    @Test
    public void test1(){
        BClass b = new BClass();
        b.is("miniprogramType=0&path=common%2fpages%2factivity%2faprilFools%2findex%2findex&id=gh_7ffb9c5d761f");
    }

    @Test
    public void test2(){
        BClass b = new BClass();
        b.has();
        int MAX_SIZE_THUMBNAIL_BYTE = 1 << 15;
        System.out.println(MAX_SIZE_THUMBNAIL_BYTE);
    }

}