/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the
 * use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 *	1. The origin of this software must not be misrepresented; you must
 *	   not claim that you wrote the original software. If you use this
 *	   software in a product, an acknowledgment in the product
 *	   documentation would be appreciated but is not required.
 *
 *	2. Altered source versions must be plainly marked as such, and must
 *	   not be misrepresented as being the original software.
 *
 *	3. This notice may not be removed or altered from any source
 *	   distribution.
 */

package com.spidey01.sxe.core.graphics;

import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.testing.TestResources;
import com.spidey01.sxe.core.testing.UnitTest;

import org.junit.*;

import com.spidey01.sxe.core.gl.*;

public class VertexBufferTest extends UnitTest {
    private static final String TAG = "VertexBufferTest";

    public static final float[] sData = new float[]{ 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f };

    @BeforeClass
    public static void beforeClass() {
        UnitTest.setup();
    }


    @Test
    public void fromArray() {
        VertexBuffer b = new VertexBuffer(sData);
        check(b);
    }

    
    @Test
    public void fromCapacity() {
        VertexBuffer b = new VertexBuffer(sData.length);
        for (float f : sData) {
            b.buffer.put(f);
        }
        Assert.assertTrue("Remaining should be equal to limit now.", 
                          b.buffer.remaining() < b.buffer.limit());
        b.buffer.flip();
        check(b);
    }


    public void check(VertexBuffer b) {
        Assert.assertEquals("....", 7, b.length);
        int expected = 6;
        Assert.assertEquals(expected, b.buffer.capacity());
        Assert.assertEquals(expected, b.buffer.limit());
        Assert.assertEquals(0, b.buffer.position());
        Assert.assertEquals(expected, b.buffer.remaining());

        for (int i=0; i < sData.length; ++i) {
            Assert.assertEquals("Elements must match.", sData[i], b.buffer.get(i), 0.01f);
        }
    }

}

/*
w/VertexBufferTest( tid=10, date=2014-9-7, time=21:41:46 ): b.length => 7
w/VertexBufferTest( tid=10, date=2014-9-7, time=21:41:46 ): b.buffer.capacity() => 6
w/VertexBufferTest( tid=10, date=2014-9-7, time=21:41:46 ): b.buffer.limit() => 6
w/VertexBufferTest( tid=10, date=2014-9-7, time=21:41:46 ): b.buffer.position() => 0
w/VertexBufferTest( tid=10, date=2014-9-7, time=21:41:46 ): b.buffer.remaining() => 6
*/
