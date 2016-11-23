package difflib.event;

import difflib.Patch;

/**
 * Interface to handle list of patch about specific file.
 */
public interface PatchHandler<T> {
    void handle(String originalPath, String revisedPath, Patch<T> patch);
}
