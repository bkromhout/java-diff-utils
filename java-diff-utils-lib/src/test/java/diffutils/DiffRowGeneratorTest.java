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
