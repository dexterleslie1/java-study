package com.future.study.jdkapi.buffer;

import org.junit.Assert;
import org.junit.Test;

import java.nio.*;

/**
 * 测试java.nio.ByteBuffer
 * @author Dexterleslie
 * @date 2018年08月04日
 * @time 15:22
 */
public class ByteBufferTest {
    /**
     * 测试put、get和position关系
     */
    @Test
    public void test_position_relation_between_put_and_get(){
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        buffer.position(3);
        buffer.put((byte)1);
        int position=buffer.position();
        Assert.assertEquals(4,position);
        buffer.put((byte)2);
        position=buffer.position();
        Assert.assertEquals(5,position);

        // 获取index=6数据
        buffer.get();
        position=buffer.position();
        Assert.assertEquals(6,position);
        buffer.get();
        position=buffer.position();
        Assert.assertEquals(7,position);
    }

    /**
     * put替换指定位置内容测试
     */
    @Test
    public void put_specify_position(){
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        String helloString="hello";
        byte []helloBytes=helloString.getBytes();
        buffer.put(helloBytes,0,helloBytes.length);

        // 替换'H'为'W'
        buffer.put(0,(byte)'M');
        // buffer最后加入'W'
        buffer.put((byte)'W');
        int length=buffer.position();
        buffer.flip();
        byte []helloBytes1=new byte[length];
        buffer.get(helloBytes1);
        String helloString1=new String(helloBytes1);
        Assert.assertEquals("MelloW",helloString1);
    }

    /**
     * 测试remaining=limit-position
     */
    @Test
    public void test_remaining(){
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        buffer.position(1);
        buffer.limit(3);
        int remaining=buffer.remaining();
        Assert.assertEquals(2,remaining);
    }

    /**
     * 测试设置position
     */
    @Test
    public void test_set_position(){
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        buffer.position(2);
        buffer.put((byte)1);
        int position=buffer.position();
        Assert.assertEquals(3,position);
        byte byteTemp=buffer.get(2);
        Assert.assertEquals(1,byteTemp);
    }

    /**
     * 测试flip函数，相当于
     * buffer.limit(buffer.position()).position(0)
     */
    @Test
    public void test_flip(){
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        buffer.position(3);
        for(int i=1;i<=100;i++){
            buffer.put((byte)i);
        }
        buffer.flip();
        int limit=buffer.limit();
        Assert.assertEquals(103,limit);
        int position=buffer.position();
        Assert.assertEquals(0,position);
    }

    /**
     * 测试rewind函数，相当于
     * buffer.position(0)
     */
    @Test
    public void test_rewind() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.position(3);
        for (int i = 1; i <= 100; i++) {
            buffer.put((byte) i);
        }
        buffer.rewind();
        int limit = buffer.limit();
        Assert.assertEquals(1024, limit);
        int position = buffer.position();
        Assert.assertEquals(0, position);
    }

    /**
     * 测试compact函数，复制position到limit之间数据到index=0位置开始
     * position=limit-position，limit=capacity
     */
    @Test
    public void test_compact(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.position(3);
        for (int i = 1; i <= 100; i++) {
            buffer.put((byte) i);
        }
        int expectedPosition=buffer.limit()-buffer.position();
        buffer.compact();
        int position=buffer.position();
        Assert.assertEquals(expectedPosition,position);
        int limit=buffer.limit();
        int capacity=buffer.capacity();
        Assert.assertEquals(capacity,limit);
    }

    /**
     *
     */
    @Test
    public void test_mark_and_reset(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.position(3);
        buffer.mark();
        buffer.position(100);
        // 返回之前markposition位置
        buffer.reset();
        int position=buffer.position();
        Assert.assertEquals(3,position);
    }

    /**
     * 测试wrap函数
     */
    @Test
    public void test_wrap(){
        byte []originBytes=new byte[100];
        for(int i=1;i<=100;i++){
            originBytes[i-1]=(byte)i;
        }
        int offset=3;
        int length=57;
        ByteBuffer buffer=ByteBuffer.wrap(originBytes,offset,length);
        int capacity=buffer.capacity();
        // buffer.capacity()==originBytes.length
        Assert.assertEquals(originBytes.length,capacity);
        // position等于调用wrap函数offset参数
        int position=buffer.position();
        Assert.assertEquals(offset,position);
        // limit等于调用wrap函数offset+length
        int limit=buffer.limit();
        Assert.assertEquals(offset+length,limit);

        // 测试修改buffer再和originBytes对比，得出结论修改buffer同时也修改originBytes
        position=buffer.position();
        buffer.put((byte)0);
        byte bufferValue=buffer.get(position);
        byte originBytesValue=originBytes[position];
        Assert.assertEquals(bufferValue,originBytesValue);

        // 测试修改originBytes是否也会同时修改buffer数据
        originBytes[43]=(byte)0;
        bufferValue=buffer.get(43);
        Assert.assertEquals(originBytes[43],bufferValue);
    }
}
