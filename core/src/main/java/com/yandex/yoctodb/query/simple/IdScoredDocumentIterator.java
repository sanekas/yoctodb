/*
 * (C) YANDEX LLC, 2014-2016
 *
 * The Source Code called "YoctoDB" available at
 * https://github.com/yandex/yoctodb is subject to the terms of the
 * Mozilla Public License, v. 2.0 (hereinafter referred to as the "License").
 *
 * A copy of the License is also available at http://mozilla.org/MPL/2.0/.
 */

package com.yandex.yoctodb.query.simple;

import org.jetbrains.annotations.NotNull;
import com.yandex.yoctodb.immutable.Database;
import com.yandex.yoctodb.util.mutable.BitSet;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * {@link Iterator} implementation mapping document IDs to {@link
 * IdScoredDocument}s
 *
 * @author incubos
 */
final class IdScoredDocumentIterator
        implements Iterator<IdScoredDocument> {
    @NotNull
    private final Database database;
    @NotNull
    private final BitSet docs;
    private int currentDoc;

    IdScoredDocumentIterator(
            @NotNull
            final Database database,
            @NotNull
            final BitSet docs) {
        assert docs.getSize() == database.getDocumentCount();

        this.database = database;
        this.docs = docs;
        this.currentDoc = docs.nextSetBit(0);
    }

    @Override
    public boolean hasNext() {
        return currentDoc >= 0;
    }

    @Override
    public IdScoredDocument next() {
        if (!hasNext())
            throw new NoSuchElementException();

        final int id = currentDoc;
        currentDoc = docs.nextSetBit(currentDoc + 1);

        return new IdScoredDocument(database, id);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Removal is not supported");
    }
}
