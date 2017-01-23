/**
 * Copyright (c) 2016 Zerocracy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zerocracy.radars.github;

import com.jcabi.github.Comment;
import com.jcabi.github.Issue;
import com.jcabi.github.mock.MkGithub;
import com.jcabi.log.Logger;
import java.io.IOException;
import javax.json.JsonObject;

/**
 * Safe GitHub comment.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 */
final class SafeComment implements Comment {

    /**
     * Original comment.
     */
    private final transient Comment origin;

    /**
     * Ctor.
     * @param cmt Original comment
     */
    SafeComment(final Comment cmt) {
        this.origin = cmt;
    }

    @Override
    public boolean equals(final Object obj) {
        return this.origin.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.origin.hashCode();
    }

    @Override
    public Issue issue() {
        return this.origin.issue();
    }

    @Override
    public int number() {
        return this.origin.number();
    }

    @Override
    public void remove() throws IOException {
        this.origin.remove();
    }

    @Override
    public int compareTo(final Comment cmt) {
        return this.origin.compareTo(cmt);
    }

    @Override
    public void patch(final JsonObject json) throws IOException {
        this.origin.patch(json);
    }

    @Override
    public JsonObject json() throws IOException {
        JsonObject json;
        try {
            json = this.origin.json();
        } catch (final AssertionError ex) {
            json = new MkGithub().randomRepo()
                .issues().create("", "")
                .comments().post("").json();
            Logger.warn(this, "Failed to fetch comment: %[exception]s", ex);
        }
        return json;
    }
}