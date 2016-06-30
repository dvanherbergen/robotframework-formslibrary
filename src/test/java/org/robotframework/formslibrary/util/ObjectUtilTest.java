package org.robotframework.formslibrary.util;

import java.awt.Button;
import java.awt.Component;

import org.junit.Test;

import junit.framework.Assert;

public class ObjectUtilTest {

    @Test
    public void canInvokeMethod() {

        Component c = new Button("mylabel");
        String result = ObjectUtil.getString(c, "getLabel()");
        Assert.assertEquals("mylabel", result);

    }

}
