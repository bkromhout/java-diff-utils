package difflib.event;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import difflib.Delta;
import difflib.Patch;

public class UnifiedPatchParserTest {

    @Test
    public void test() throws FileNotFoundException, IOException {
        final AtomicInteger visited = new AtomicInteger(0);
        PatchHandler<String> handler = new PatchHandler<String>() {
            @Override
            public void handle(String originalPath, String revisedPath,
                    Patch<String> patch) {
                visited.incrementAndGet();

                Delta<String> delta = patch.getDeltas().get(0);
                assertThat(delta.getOriginal().getPosition(), is(0));
                if (originalPath.startsWith("test/a.txt")) {
                    assertThat(revisedPath, startsWith("test2/a.txt"));
                    assertThat(patch.getDeltas().size(), is(1));
                    assertThat(delta.getOriginal().getLines(), hasItem("hello, world"));
                    assertThat(delta.getRevised().getLines(), hasItem("hello"));
                } else {
                    assertThat(originalPath, startsWith("test/b.txt"));
                    assertThat(revisedPath,  startsWith("test2/b.txt"));
                    assertThat(patch.getDeltas().size(), is(1));
                    assertThat(delta.getOriginal().getLines(), hasItems("hello! 1", "hello! 2"));
                    assertThat(delta.getRevised().getLines(), hasItem("hello"));
                }
            }
        };
        new UnifiedPatchParser().parse(new File("src/test/resources/event",  "2files.diff"), handler);
        assertThat(visited.intValue(), is(2));
    }
}
