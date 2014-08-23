package com.wansan.template.test;

import com.wansan.template.core.Utils;
import org.junit.Test;

/**
 * Created by Administrator on 2014/7/23.
 */
public class OtherTest
{
    @Test
    public void testme(){
        String name = "abcdefg.jpg";
        String nameWithoutEx = name.substring(0,name.indexOf("."));
        String ext = name.substring(name.indexOf("."));
        String newFile = Utils.getNewFilename();
        System.out.print(newFile);
    }
}
