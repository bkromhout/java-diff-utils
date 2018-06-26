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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Describes the diff row in form [tag, oldLine, newLine) for showing the difference between two texts
 * @author <a href="dm.naumenko@gmail.com">Dmitry Naumenko</a>
 */
public class DiffRow {
    @Nonnull
    private Tag tag;
    @Nullable
    private String oldLine;
    @Nullable
    private String newLine;

    public DiffRow(@Nonnull Tag tag, @Nullable String oldLine, @Nullable String newLine) {
        this.tag = tag;
        this.oldLine = oldLine;
        this.newLine = newLine;
    }

    public enum Tag {
        INSERT, DELETE, CHANGE, EQUAL, SKIP
    }

    /**
     * @return the tag
     */
    @Nonnull
    public Tag getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /**
     * @return the oldLine
     */
    @Nullable
    public String getOldLine() {
        return oldLine;
    }

    /**
     * @param oldLine the oldLine to set
     */
    public void setOldLine(@Nullable String oldLine) {
        this.oldLine = oldLine;
    }

    /**
     * @return the newLine
     */
    @Nullable
    public String getNewLine() {
        return newLine;
    }

    /**
     * @param newLine the newLine to set
     */
    public void setNewLine(@Nullable String newLine) {
        this.newLine = newLine;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((newLine == null) ? 0 : newLine.hashCode());
        result = prime * result + ((oldLine == null) ? 0 : oldLine.hashCode());
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DiffRow other = (DiffRow) obj;
        if (newLine == null) {
            if (other.newLine != null)
                return false;
        } else if (!newLine.equals(other.newLine))
            return false;
        if (oldLine == null) {
            if (other.oldLine != null)
                return false;
        } else if (!oldLine.equals(other.oldLine))
            return false;
        if (tag == null) {
            return other.tag == null;
        } else return tag.equals(other.tag);
    }

    public String toString() {
        return "[" + this.tag + "," + this.oldLine + "," + this.newLine + "]";
    }
}
