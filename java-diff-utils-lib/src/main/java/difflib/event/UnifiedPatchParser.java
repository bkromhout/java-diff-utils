package difflib.event;

import difflib.DiffUtils;
import difflib.Patch;
import difflib.Utils;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UnifiedPatchParser {

    public void parse(File unifiedPatch, @Nonnull PatchHandler<String> handler) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(unifiedPatch), Utils.UTF_8))) {
            String line = reader.readLine();
            do {
                List<String> patch = new ArrayList<>();
                while (!line.startsWith("---")) {
                    // in prelude: skip all lines
                    line = reader.readLine();
                }
                assert line.startsWith("---");
                patch.add(line);
                final String originalFileName = line.substring(4);

                line = reader.readLine();
                assert line.startsWith("+++");
                patch.add(line);
                final String revisedFileName = line.substring(4);

                line = reader.readLine();
                do {
                    patch.add(line);
                    line = reader.readLine();
                } while (
                    // TODO this implementation expects that we have comment between 2 diffs.
                        line != null && (line.startsWith("@@ ") || line.startsWith(" ") || line.startsWith("+")
                                || line.startsWith("-")));

                Patch<String> parsed = DiffUtils.parseUnifiedDiff(patch);
                handler.handle(originalFileName, revisedFileName, parsed);
            } while (line != null);
        }
    }
}
