package difflib.myers;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/**
 * Specifies when two compared elements in the Myers algorithm are equal.
 * 
 * @param T The type of the compared elements in the 'lines'.
 */
public interface Equalizer<T> {

    /**
     * Indicates if two elements are equal according to the diff mechanism.
     * @param original The original element. Must not be {@code null}.
     * @param revised The revised element. Must not be {@code null}.
     * @return Returns true if the elements are equal.
     */
    @CheckReturnValue
    public boolean equals(@Nullable T original, @Nullable T revised);

    /**
     * Indicates if elements must be skipped.
     * @param original
     * @return
     */
    @CheckReturnValue
    public boolean skip(@Nullable T original);
}
