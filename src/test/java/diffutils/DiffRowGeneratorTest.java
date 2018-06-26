/*
 * SPDX-License-Identifier: Apache-1.1
 *
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.
 * Copyright (c) 2010 Dmitry Naumenko (dm.naumenko@gmail.com)
 * Copyright (c) 2015-2016 Brenden Kromhout and contributors to java-diff-utils
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowledgement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgement may appear in the software itself,
 *    if and wherever such third-party acknowledgements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

package diffutils;

import difflib.DiffRow;
import difflib.DiffRowGenerator;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DiffRowGeneratorTest  extends TestCase {

    public void testGenerator_Default() {
        String first = "anything \n \nother";
        String second ="anything\n\nother";

        DiffRowGenerator generator = new DiffRowGenerator.Builder()
            .columnWidth(Integer.MAX_VALUE) // do not wrap
            .build();
        List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
        print(rows);

        assertEquals(3, rows.size());
    }

    public void testGenerator_InlineDiff() {
        String first = "anything \n \nother";
        String second ="anything\n\nother";

        DiffRowGenerator generator = new DiffRowGenerator.Builder()
            .showInlineDiffs(true)
            .columnWidth(Integer.MAX_VALUE) // do not wrap
            .build();
		List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
        print(rows);

        assertEquals(3, rows.size());
        assertTrue(rows.get(0).getOldLine().indexOf("<del>") > 0);
    }

    public void testGenerator_IgnoreWhitespaces() {
        String first = "anything \n \nother\nmore lines";
        String second ="anything\n\nother\nsome more lines";

        DiffRowGenerator generator = new DiffRowGenerator.Builder()
            .ignoreWhiteSpaces(true)
            .columnWidth(Integer.MAX_VALUE) // do not wrap
            .build();
        List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
        print(rows);

        assertEquals(4, rows.size());
        assertEquals(rows.get(0).getTag(), DiffRow.Tag.EQUAL);
        assertEquals(rows.get(1).getTag(), DiffRow.Tag.EQUAL);
        assertEquals(rows.get(2).getTag(), DiffRow.Tag.EQUAL);
        assertEquals(rows.get(3).getTag(), DiffRow.Tag.CHANGE);
    }

    public void testChangeToEmptyLine() {
        String first = "Test \n \no\n";
        String second ="Test\n\no\n";

        DiffRowGenerator generator = new DiffRowGenerator.Builder()
                .showInlineDiffs(true)
                .columnWidth(Integer.MAX_VALUE) // do not wrap
                .build();
        List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
        print(rows);

        assertEquals(3, rows.size());
        assertThat(rows.size(), is(3));
        assertThat(rows.get(0).getTag(), is(DiffRow.Tag.CHANGE));
        assertThat(rows.get(0).getOldLine().indexOf("<del>"), is(4));
        assertThat(rows.get(1).getTag(), is(DiffRow.Tag.CHANGE));
        assertThat(rows.get(1).getOldLine().indexOf("<del>"), is(0));
        assertThat(rows.get(2).getTag(), is(DiffRow.Tag.EQUAL));
    }

    public void testChangeToTwoEmptyLine() {
        String first = "One\n \nTwo\n \nThree\n";
        String second ="One\n\nTwo\n\nThree\n";

        DiffRowGenerator generator = new DiffRowGenerator.Builder()
                .showInlineDiffs(true)
                .columnWidth(Integer.MAX_VALUE) // do not wrap
                .build();
        List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
        print(rows);

        assertEquals(5, rows.size());
        assertThat(rows.get(0).getTag(), is(DiffRow.Tag.EQUAL));

        assertThat(rows.get(1).getTag(), is(DiffRow.Tag.CHANGE));
        assertThat(rows.get(1).getOldLine().indexOf("<del>"), is(0));

        assertThat(rows.get(2).getTag(), is(DiffRow.Tag.EQUAL));

        assertThat(rows.get(3).getTag(), is(DiffRow.Tag.CHANGE));
        assertThat(rows.get(3).getOldLine().indexOf("<del>"), is(0));

    }

    public void testDeleteLine() {
        String first ="Equal Line\nDeleted Line\nEqual Line 2\n";
        String second = "Equal Line\nEqual Line 2\n";

        DiffRowGenerator generator = new DiffRowGenerator.Builder()
                .showInlineDiffs(true)
                .columnWidth(Integer.MAX_VALUE) // do not wrap
                .build();
        List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
        print(rows);

        assertThat(rows.size(), is(3));
        assertThat(rows.get(0).getTag(), is(DiffRow.Tag.EQUAL));
        assertThat(rows.get(1).getTag(), is(DiffRow.Tag.DELETE));
        assertThat(rows.get(2).getTag(), is(DiffRow.Tag.EQUAL));
    }

    public void testInsertedLine() {
        String first = "Equal Line\nEqual Line 2\n";
        String second = "Equal Line\nDeleted Line\nEqual Line 2\n";

        DiffRowGenerator generator = new DiffRowGenerator.Builder()
                .showInlineDiffs(true)
                .columnWidth(Integer.MAX_VALUE) // do not wrap
                .build();
        List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
        print(rows);

        assertThat(rows.size(), is(3));
        assertThat(rows.get(0).getTag(), is(DiffRow.Tag.EQUAL));
        assertThat(rows.get(1).getTag(), is(DiffRow.Tag.INSERT));
        assertThat(rows.get(2).getTag(), is(DiffRow.Tag.EQUAL));
    }

    public void testChangedLine() {
        String first = "Equal Line\nLine to be changed\nEqual Line 2\n";
        String second = "Equal Line\nLine changed test\nEqual Line 2\n";

        DiffRowGenerator generator = new DiffRowGenerator.Builder()
                .showInlineDiffs(true)
                .columnWidth(Integer.MAX_VALUE) // do not wrap
                .build();
        List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
        print(rows);

        assertThat(rows.size(), is(3));
        assertThat(rows.get(0).getTag(), is(DiffRow.Tag.EQUAL));
        assertThat(rows.get(1).getTag(), is(DiffRow.Tag.CHANGE));
        assertThat(rows.get(2).getTag(), is(DiffRow.Tag.EQUAL));
    }

    private List<String> split(String content) {
        return Arrays.asList(content.split("\n"));
    }

    private void print(List<DiffRow> diffRows) {
        for (DiffRow row: diffRows) {
            System.out.println(row);
        }
    }
}
