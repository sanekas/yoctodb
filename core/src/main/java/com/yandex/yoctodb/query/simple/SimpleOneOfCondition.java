/*
 * (C) YANDEX LLC, 2014-2015
 *
 * The Source Code called "YoctoDB" available at
 * https://bitbucket.org/yandex/yoctodb is subject to the terms of the
 * Mozilla Public License, v. 2.0 (hereinafter referred to as the "License").
 *
 * A copy of the License is also available at http://mozilla.org/MPL/2.0/.
 */

package com.yandex.yoctodb.query.simple;

import com.yandex.yoctodb.immutable.FilterableIndexProvider;
import com.yandex.yoctodb.query.Condition;
import com.yandex.yoctodb.query.TermCondition;
import com.yandex.yoctodb.util.mutable.BitSet;
import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * {@code OR} condition
 *
 * @author incubos
 */
@Immutable
public final class SimpleOneOfCondition implements Condition {
    @NotNull
    private final Collection<TermCondition> clauses;

    public SimpleOneOfCondition(
            @NotNull
            final Collection<TermCondition> conditions) {
        assert conditions.size() >= 2;

        this.clauses = new ArrayList<TermCondition>(conditions);
    }

    @Override
    public boolean set(
            @NotNull
            final FilterableIndexProvider indexProvider,
            @NotNull
            final BitSet to) {
        boolean notEmpty = false;
        for (Condition clause : clauses)
            if (clause.set(indexProvider, to))
                notEmpty = true;

        return notEmpty;
    }
}