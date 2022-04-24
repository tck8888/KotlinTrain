package com.tck.kotlintrain.test

import okhttp3.internal.and
import kotlin.math.pow

//adts_frame()
//adts_fixed_header()
//syncword                            : 0x0FFF (12 bits)
//ID                                  : 0 (1 bit)
//layer                               : 0 (2 bits)
//protection_absent                   : 1 (1 bit)
//profile                             : 1 [Low Complexity profile (LC)] (2 bits)
//sampling_frequency_index            : 3 [48000 Hz] (4 bits)
//private_bit                         : 0 (1 bit)
//channel_configuration               : 2 [2 - LF RF] (3 bits)
//original/copy                       : 0 (1 bit)
//home                                : 0 (1 bit)
//adts_variable_header()
//copyright_identification_bit        : 0 (1 bit)
//copyright_identification_start      : 0 (1 bit)
//frame_length                        : 348 (13 bits)
//adts_buffer_fullness                : 2047 (11 bits)
//number_of_raw_data_blocks_in_frame  : 0 (2 bits)
//adts_error_check()
//raw_data_block()

fun main() {
   // FF F1 4C 80 2B 9F FC
    // println(2.0.pow(2.0))
    val byteArray = ByteArray(7)
    //syncword                           12bits  1111 1111 1111
    //ID                                 1bits   0
    //layer                              2bits   00
    //protection_absent                  1bits   1
    //profile                            2bits   01
    //sampling_frequency_index           4bits   0011
    //private_bit                        1bits   0
    //channel_configuration              3bits   010
    //original/copy                      1bits   0
    //home                               1bits   0

    //copyright_identification_bit       1bits   0
    //copyright_identification_start     1bits   0
    //frame_length                       13bits  0000101011100
    //adts_buffer_fullness               11bits  11111111111
    //number_of_raw_data_blocks_in_frame 2bits   00
    byteArray[0] = 0xff.toByte()
    //
    byteArray[1] = 0xF.toByte()

    println(byteArray[0].and(0xff))

    val map=HashMap<String,String>()
    map["0xFF"] = "11111111"
    map["0xF1"] = "11110001"

    //FF F1
    //syncword                           12bits  1111 1111 1111
    //ID                                 1bits   0
    //layer                              2bits   00
    //protection_absent                  1bits   1

    //1  3    0 2
    //01 0011 0 010
    //profile                            2bits   01
    //sampling_frequency_index           4bits   0011
    //private_bit                        1bits   0
    //取1位
    //channel_configuration              3bits   010

    //0100 0000
    val a=1.shl(6).and(0xff)
    println(a)
    //00 0011 00
    val b =3.shl(2).and(0xff)
    println(b)
    //0000000 000
    val c=2.shr(2).and(0xff)
    println(c)
    println(a+b+c)

    map["0x4C"] = "01001100"

    //后面2位
    //channel_configuration              3bits   010
    //original/copy                      1bits   0
    //home                               1bits   0
    //copyright_identification_bit       1bits   0
    //copyright_identification_start     1bits   0
    //取前2位
    //frame_length                       13bits  0000101011100
    //10 0 0
    //

    //100 00000
    val m =2.shl(6).and(0xff)
    println(m)
    //100000 00
    val n =348.shr(11).and(0xff)
    println(n)
    println(m+n)
    map["0x80"] = "10000000"
    //取后面8位
    //frame_length                       13bits  0000101011100

    //adts_buffer_fullness                : 2047 (11 bits)
    //0010101110000
    val o =348.shl(2).and(0xff)
    println( o)
    val p =o.shr(3).and(0xff)
    //00101011

    println( p )
}