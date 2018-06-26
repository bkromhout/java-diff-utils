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
package difflib;

import difflib.DiffRow.Tag;
import difflib.myers.Equalizer;
import difflib.myers.MyersDiff;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class for generating DiffRows for side-by-sidy view. You can customize the way of generating. For example, show
 * inline diffs on not, ignoring white spaces or/and blank lines and so on. All parameters for generating are optional.
 * If you do not specify them, the class will use the default values.
 * <p>
 * These values are: showInlineDiffs = false; ignoreWhiteSpaces = true; ignoreBlankLines = true; ...
 * <p>
 * For instantiating the DiffRowGenerator you should use the its builder. Like in example
 * <p>
 * <code> DiffRowGenerator generator = new DiffRowGenerator.Builder().showInlineDiffs(true).
 * ignoreWhiteSpaces(true).columnWidth(100).build(); </code>
 * @author <a href="dm.naumenko@gmail.com">Dmitry Naumenko</a>
 */
public class DiffRowGenerator {
    private static final String NEW_LINE = "\n";
    private static final Pattern WS_PATTERN = Pattern.compile("\\s+");

    private static final String DEFAULT_TAG_DELETE = "del";
    private static final String DEFAULT_TAG_INSERT = "ins";
    private static final String DEFAULT_TAG_CHANGE = "span";
    private static final String DEFAULT_CSSCLASS_DELETE = null;
    private static final String DEFAULT_CSSCLASS_INSERT = null;
    private static final String DEFAULT_CSSCLASS_CHANGE = "change";
    private static final DiffAlgorithm<String> DEFAULT_DIFFALGORITHM = new MyersDiff<String>(new Equalizer<String>() {
        public boolean equals(String original, String revised) {
            return Objects.equals(original, revised);
        }

        @Override
        public boolean skip(String original) {
            return false;
        }
    });

    private final boolean showInlineDiffs;
    private final boolean ignoreWhiteSpaces;
    private final String inlineOriginDeleteTag;
    private final String inlineRevisedInsertTag;
    private final String inlineOriginChangeTag;
    private final String inlineRevisedChangeTag;
    private final String inlineOriginDeleteCssClass;
    private final String inlineRevisedInsertCssClass;
    private final String inlineOriginChangeCssClass;
    private final String inlineRevisedChangeCssClass;
    private final int columnWidth;
    @Nullable
    private final String defaultString;
    private final DiffAlgorithm<String> diffAlgorithm;

    /**
     * This class used for building the DiffRowGenerator.
     * @author dmitry
     */
    public static class Builder {
        private boolean showInlineDiffs = false;
        private boolean ignoreWhiteSpaces = false;
        private String inlineOriginDeleteTag = DEFAULT_TAG_DELETE;
        private String inlineOriginChangeTag = DEFAULT_TAG_CHANGE;
        private String inlineRevisedInsertTag = DEFAULT_TAG_INSERT;
        private String inlineRevisedChangeTag = DEFAULT_TAG_CHANGE;
        private String inlineOriginDeleteCssClass = DEFAULT_CSSCLASS_DELETE;
        private String inlineRevisedInsertCssClass = DEFAULT_CSSCLASS_INSERT;
        private String inlineOriginChangeCssClass = DEFAULT_CSSCLASS_CHANGE;
        private String inlineRevisedChangeCssClass = DEFAULT_CSSCLASS_CHANGE;
        private int columnWidth = -1;
        @Nullable
        private String defaultString = "";
        private DiffAlgorithm<String> diffAlgorithm = DEFAULT_DIFFALGORITHM;

        /**
         * Show inline diffs in generating diff rows or not.
         * @param val the value to set. Default: false.
         * @return builder with configured showInlineDiff parameter
         */
        public Builder showInlineDiffs(boolean val) {
            showInlineDiffs = val;
            return this;
        }

        /**
         * Ignore white spaces in generating diff rows or not.
         * @param val the value to set. Default: true.
         * @return builder with configured ignoreWhiteSpaces parameter
         */
        public Builder ignoreWhiteSpaces(boolean val) {
            ignoreWhiteSpaces = val;
            return this;
        }

        /**
         * Set the tag used for displaying changes in the original text.
         * @param tag the tag to set. Without angle brackets. Default: {@value #DEFAULT_TAG_DELETE}.
         * @return builder with configured inlineOriginDeleteTag parameter
         * @deprecated Use {@link #inlineOriginDeleteTag(String)}
         */
        @Deprecated
        public Builder InlineOldTag(String tag) {
            inlineOriginDeleteTag = tag;
            return this;
        }

        /**
         * Set the tag used for displaying delete data in the original text.
         * @param tag the tag to set. Without angle brackets. Default: {@value #DEFAULT_TAG_DELETE}.
         * @return builder with configured inlineOriginDeleteTag parameter
         */
        public Builder inlineOriginDeleteTag(String tag) {
            inlineOriginDeleteTag = tag;
            return this;
        }

        /**
         * Set the tag used for displaying changes in the revised text.
         * @param tag the tag to set. Without angle brackets. Default: {@value #DEFAULT_TAG_INSERT}.
         * @return builder with configured inlineRevisedInsertTag parameter
         * @deprecated Use {@link #inlineRevisedInsertTag(String)}
         */
        public Builder InlineNewTag(String tag) {
            inlineRevisedInsertTag = tag;
            return this;
        }

        /**
         * Set the tag used for displaying changes in the revised text.
         * @param tag the tag to set. Without angle brackets. Default: {@value #DEFAULT_TAG_INSERT}.
         * @return builder with configured inlineRevisedInsertTag parameter
         */
        public Builder inlineRevisedInsertTag(String tag) {
            inlineRevisedInsertTag = tag;
            return this;
        }

        /**
         * Set the css class used for displaying changes in the original text.
         * @param cssClass the tag to set. Without any quotes, just word. Default: {@value #DEFAULT_CSSCLASS_DELETE}.
         * @return builder with configured inlineOriginDeleteCssClass parameter
         * @deprecated Use {@link #inlineOriginDeleteCssClass(String)}
         */
        public Builder InlineOldCssClass(String cssClass) {
            inlineOriginDeleteCssClass = cssClass;
            return this;
        }

        /**
         * Set the css class used for displaying delete data in the original text.
         * @param cssClass the tag to set. Without any quotes, just word. Default: {@value #DEFAULT_CSSCLASS_DELETE}.
         * @return builder with configured inlineOriginDeleteCssClass parameter
         */
        public Builder inlineOriginDeleteCssClass(String cssClass) {
            inlineOriginDeleteCssClass = cssClass;
            return this;
        }

        /**
         * Set the css class used for displaying changes in the revised text.
         * @param cssClass the tag to set. Without any quotes, just word. Default: {@value #DEFAULT_CSSCLASS_INSERT}.
         * @return builder with configured inlineRevisedInsertCssClass parameter
         * @deprecated Use {@link #inlineRevisedInsertCssClass(String)}
         */
        public Builder InlineNewCssClass(String cssClass) {
            inlineRevisedInsertCssClass = cssClass;
            return this;
        }

        /**
         * Set the css class used for displaying insert data in the revised text.
         * @param cssClass the tag to set. Without any quotes, just word. Default: {@value #DEFAULT_CSSCLASS_INSERT}.
         * @return builder with configured inlineRevisedInsertCssClass parameter
         */
        public Builder inlineRevisedInsertCssClass(String cssClass) {
            inlineRevisedInsertCssClass = cssClass;
            return this;
        }

        /**
         * Set the column with of generated lines of original and revised texts.
         * @param width the width to set. Making it < 0 disable line breaking.
         * @return builder with configured columnWidth parameter
         */
        public Builder columnWidth(int width) {
            columnWidth = width;
            return this;
        }

        @Nonnull
        public Builder defaultString(@Nullable String defaultString) {
            this.defaultString = defaultString;
            return this;
        }

        /**
         * Set the custom equalizer to use while comparing the lines of the revisions.
         * @param stringEqualizer to use (custom one)
         * @return builder with configured stringEqualizer
         */
        public Builder stringEqualizer(Equalizer<String> stringEqualizer) {
            this.diffAlgorithm = new MyersDiff<>(stringEqualizer);
            return this;
        }

        /**
         * Set the custom {@link DiffAlgorithm} to use while comparing the lines of the revisions.
         * @param diffAlgorithm to use (custom one)
         * @return builder with configured stringEqualizer
         */
        public Builder diffAlgorithm(DiffAlgorithm<String> diffAlgorithm) {
            this.diffAlgorithm = diffAlgorithm;
            return this;
        }

        /**
         * Build the DiffRowGenerator using the default Equalizer for rows. If some parameters are not set, the default
         * values are used.
         * @return the customized DiffRowGenerator
         */
        public DiffRowGenerator build() {
            return new DiffRowGenerator(this);
        }
    }

    private DiffRowGenerator(Builder builder) {
        showInlineDiffs = builder.showInlineDiffs;
        ignoreWhiteSpaces = builder.ignoreWhiteSpaces;

        inlineOriginDeleteTag = builder.inlineOriginDeleteTag;
        inlineOriginDeleteCssClass = builder.inlineOriginDeleteCssClass;

        inlineOriginChangeTag = builder.inlineOriginChangeTag;
        inlineOriginChangeCssClass = builder.inlineOriginChangeCssClass;

        inlineRevisedInsertTag = builder.inlineRevisedInsertTag;
        inlineRevisedInsertCssClass = builder.inlineRevisedInsertCssClass;

        inlineRevisedChangeTag = builder.inlineRevisedChangeTag;
        inlineRevisedChangeCssClass = builder.inlineRevisedChangeCssClass;

        columnWidth = builder.columnWidth;
        defaultString = builder.defaultString;
        diffAlgorithm = builder.diffAlgorithm;
    }

    /**
     * Get the DiffRows describing the difference between original and revised texts using the given patch. Useful for
     * displaying side-by-side diff.
     * @param original the original text
     * @param revised  the revised text
     * @return the DiffRows between original and revised texts
     */
    public List<DiffRow> generateDiffRows(List<String> original, List<String> revised) {
        if (ignoreWhiteSpaces) {
            replAllWs(original);
            replAllWs(revised);
        }
        return generateDiffRows(original, revised, DiffUtils.diff(original, revised, diffAlgorithm));
    }

    private void replAllWs(List<String> strList) {
        for (final ListIterator<String> i = strList.listIterator(); i.hasNext(); ) {
            final String s = i.next();
            if (s != null) i.set(WS_PATTERN.matcher(s.trim()).replaceAll(" "));
        }
    }

    /**
     * Generates the DiffRows describing the difference between original and revised texts using the given patch. Useful
     * for displaying side-by-side diff.
     * @param original the original text
     * @param revised  the revised text
     * @param patch    the given patch
     * @return the DiffRows between original and revised texts
     */
    public List<DiffRow> generateDiffRows(List<String> original, List<String> revised, Patch<String> patch) {
        // normalize the lines (expand tabs, escape html entities)
        original = Utils.normalize(original);
        revised = Utils.normalize(revised);

        // wrap to the column width
        if (columnWidth > 0) {
            original = Utils.wrapText(original, this.columnWidth);
            revised = Utils.wrapText(revised, this.columnWidth);
        }
        List<DiffRow> diffRows = new ArrayList<DiffRow>();
        int orgEndPos = 0;
        int revEndPos = 0;
        final List<Delta<String>> deltaList = patch.getDeltas();

        Equalizer<String> equalizer = diffAlgorithm.getEqualizer();

        for (int i = 0; i < deltaList.size(); i++) {
            Delta<String> delta = deltaList.get(i);
            Chunk<String> orig = delta.getOriginal();
            Chunk<String> rev = delta.getRevised();

            // We should normalize and wrap lines in deltas too.
            orig.setLines(Utils.normalize(orig.getLines()));
            rev.setLines(Utils.normalize(rev.getLines()));

            if (columnWidth > 0) {
                orig.setLines(Utils.wrapText(orig.getLines(), this.columnWidth));
                rev.setLines(Utils.wrapText(rev.getLines(), this.columnWidth));
            }
            // catch the equal prefix for each chunk
            copyEqualsLines(equalizer, diffRows, original, orgEndPos, orig.getPosition(), revised, revEndPos,
                    rev.getPosition());

            // Inserted DiffRow
            if (delta.getClass() == InsertDelta.class) {
                orgEndPos = orig.last() + 1;
                revEndPos = rev.last() + 1;
                for (String line : rev.getLines()) {
                    if (equalizer.skip(line)) {
                        diffRows.add(new DiffRow(Tag.SKIP, defaultString, line));
                    } else {
                        diffRows.add(new DiffRow(Tag.INSERT, defaultString, line));
                    }
                }
                continue;
            }

            // Deleted DiffRow
            if (delta.getClass() == DeleteDelta.class) {
                orgEndPos = orig.last() + 1;
                revEndPos = rev.last() + 1;
                for (String line : orig.getLines()) {
                    if (equalizer.skip(line)) {
                        diffRows.add(new DiffRow(Tag.SKIP, line, defaultString));
                    } else {
                        diffRows.add(new DiffRow(Tag.DELETE, line, defaultString));
                    }
                }
                continue;
            }

            if (showInlineDiffs) {
                addInlineDiffs(delta);
            }
            // the changed size is match
            if (orig.size() == rev.size()) {
                for (int j = 0; j < orig.size(); j++) {
                    addChangeDiffRow(equalizer, diffRows, orig.getLines().get(j), rev.getLines().get(j), defaultString);
                }
            } else if (orig.size() > rev.size()) {
                for (int j = 0; j < orig.size(); j++) {
                    final String orgLine = orig.getLines().get(j);
                    final String revLine = rev.getLines().size() > j ? rev.getLines().get(j) : defaultString;
                    addChangeDiffRow(equalizer, diffRows, orgLine, revLine, defaultString);
                }
            } else {
                for (int j = 0; j < rev.size(); j++) {
                    final String orgLine = orig.getLines().size() > j ? orig.getLines().get(j) : defaultString;
                    final String revLine = rev.getLines().get(j);
                    addChangeDiffRow(equalizer, diffRows, orgLine, revLine, defaultString);
                }
            }
            orgEndPos = orig.last() + 1;
            revEndPos = rev.last() + 1;
        }

        // Copy the final matching chunk if any.
        copyEqualsLines(equalizer, diffRows, original, orgEndPos, original.size(), revised, revEndPos, revised.size());
        return diffRows;
    }

    private static void addChangeDiffRow(Equalizer<String> equalizer, List<DiffRow> diffRows, String orgLine,
                                               String revLine, String defaultString) {
        boolean skipOrg = equalizer.skip(orgLine);
        boolean skipRev = equalizer.skip(revLine);
        if (skipOrg && skipRev) {
            diffRows.add(new DiffRow(Tag.SKIP, orgLine, revLine));
        } else if (skipOrg) {
            diffRows.add(new DiffRow(Tag.SKIP, orgLine, defaultString));
            diffRows.add(new DiffRow(Tag.CHANGE, defaultString, revLine));
        } else if (skipRev) {
            diffRows.add(new DiffRow(Tag.CHANGE, orgLine, defaultString));
            diffRows.add(new DiffRow(Tag.SKIP, defaultString, revLine));
        } else {
            diffRows.add(new DiffRow(Tag.CHANGE, orgLine, revLine));
        }
    }

    protected void copyEqualsLines(Equalizer<String> equalizer, List<DiffRow> diffRows, List<String> original,
                                   int originalStartPos, int originalEndPos, List<String> revised, int revisedStartPos,
                                   int revisedEndPos) {
        String[][] lines = new String[originalEndPos - originalStartPos][2];
        int idx = 0;
        for (String line : original.subList(originalStartPos, originalEndPos)) {
            lines[idx++][0] = line;
        }
        idx = 0;
        for (String line : revised.subList(revisedStartPos, revisedEndPos)) {
            lines[idx++][1] = line;
        }
        for (String[] line : lines) {
            String orgLine = line[0];
            String revLine = line[1];
            if (equalizer.skip(orgLine) && equalizer.skip(revLine)) {
                diffRows.add(new DiffRow(Tag.SKIP, orgLine, revLine));
            } else {
                diffRows.add(new DiffRow(Tag.EQUAL, orgLine, revLine));
            }
        }
    }

    /**
     * Add the inline diffs for given delta
     * @param delta the given delta
     */
    private void addInlineDiffs(Delta<String> delta) {
        List<String> orig = delta.getOriginal().getLines();
        List<String> rev = delta.getRevised().getLines();
        LinkedList<String> origList = charArrayToStringList(Utils.join(orig, NEW_LINE).toCharArray());
        LinkedList<String> revList = charArrayToStringList(Utils.join(rev, NEW_LINE).toCharArray());

        List<Delta<String>> inlineDeltas = DiffUtils.diff(origList, revList).getDeltas();
        Collections.reverse(inlineDeltas);
        for (Delta<String> inlineDelta : inlineDeltas) {
            Chunk<String> inlineOrig = inlineDelta.getOriginal();
            Chunk<String> inlineRev = inlineDelta.getRevised();
            if (inlineDelta.getClass().equals(DeleteDelta.class)) {
                origList = wrapInTag(origList, inlineOrig.getPosition(), inlineOrig.getPosition() + inlineOrig.size()
                        + 1, this.inlineOriginDeleteTag, this.inlineOriginDeleteCssClass);
            } else if (inlineDelta.getClass().equals(InsertDelta.class)) {
                revList = wrapInTag(revList, inlineRev.getPosition(), inlineRev.getPosition() + inlineRev.size() + 1,
                        this.inlineRevisedInsertTag, this.inlineRevisedInsertCssClass);
            } else if (inlineDelta.getClass().equals(ChangeDelta.class)) {
                origList = wrapInTag(origList, inlineOrig.getPosition(), inlineOrig.getPosition() + inlineOrig.size()
                        + 1, this.inlineOriginChangeTag, this.inlineOriginChangeCssClass);
                revList = wrapInTag(revList, inlineRev.getPosition(), inlineRev.getPosition() + inlineRev.size() + 1,
                        this.inlineRevisedChangeTag, this.inlineRevisedChangeCssClass);
            }
        }

        delta.getOriginal().setLines(addMissingLines(origList, orig.size()));
        delta.getRevised().setLines(addMissingLines(revList, rev.size()));
    }

    private List<String> addMissingLines(final List<String> lines, final int targetSize) {
        List<String> tempList = Arrays.asList(Utils.join(lines, "").split(NEW_LINE));
        if (tempList.size() < targetSize) {
            tempList = new ArrayList<>(tempList);
            while (tempList.size() < targetSize) {
                tempList.add("");
            }
        }
        return tempList;
    }

    private static LinkedList<String> charArrayToStringList(char[] cs) {
        LinkedList<String> result = new LinkedList<String>();
        for (Character character : cs) {
            result.add(character.toString());
        }
        return result;
    }

    private static final Pattern PATTERN_CRLF = Pattern.compile("([\\n\\r]+)");

    /**
     * Wrap the elements in the sequence with the given tag
     * @param startPosition the position from which tag should start. The counting start from a zero.
     * @param endPosition   the position before which tag should should be closed.
     * @param tag           the tag name without angle brackets, just a word
     * @param cssClass      the optional css class
     */
    public static LinkedList<String> wrapInTag(LinkedList<String> sequence, int startPosition, int endPosition,
                                               String tag, String cssClass) {
        LinkedList<String> result = (LinkedList<String>) sequence.clone();
        StringBuilder tagBuilder = new StringBuilder();
        tagBuilder.append("<");
        tagBuilder.append(tag);
        if (cssClass != null) {
            tagBuilder.append(" class=\"");
            tagBuilder.append(cssClass);
            tagBuilder.append("\"");
        }
        tagBuilder.append(">");
        final String startTag = tagBuilder.toString();

        tagBuilder.delete(0, tagBuilder.length());

        tagBuilder.append("</");
        tagBuilder.append(tag);
        tagBuilder.append(">");
        final String endTag = tagBuilder.toString();

        result.add(startPosition, startTag);
        result.add(endPosition, endTag);
        final String joinTag = new StringBuilder(Matcher.quoteReplacement(endTag)).append("$1")
                                                                                  .append(Matcher
                                                                                          .quoteReplacement(startTag))
                                                                                  .toString();

        for (int i = startPosition + 1; i < endPosition; ++i) {
            final String val = result.get(i);
            if (val.contains("\n") || val.contains("\r")) {
                result.set(i, PATTERN_CRLF.matcher(val).replaceAll(joinTag));
            }
        }
        return result;
    }

    /**
     * Wrap the given line with the given tag
     * @param line     the given line
     * @param tag      the tag name without angle brackets, just a word
     * @param cssClass the optional css class
     * @return the wrapped string
     */
    public static String wrapInTag(String line, String tag, String cssClass) {
        final StringBuilder tagBuilder = new StringBuilder();
        tagBuilder.append("<");
        tagBuilder.append(tag);
        if (cssClass != null) {
            tagBuilder.append(" class=\"");
            tagBuilder.append(cssClass);
            tagBuilder.append("\"");
        }
        tagBuilder.append(">");
        final String startTag = tagBuilder.toString();

        tagBuilder.delete(0, tagBuilder.length());

        tagBuilder.append("</");
        tagBuilder.append(tag);
        tagBuilder.append(">");
        final String endTag = tagBuilder.toString();

        final String joinTag = new StringBuilder(Matcher.quoteReplacement(endTag)).append("$1")
                                                                                  .append(Matcher
                                                                                          .quoteReplacement(startTag))
                                                                                  .toString();

        return startTag + PATTERN_CRLF.matcher(line).replaceAll(joinTag) + endTag;
    }
}
