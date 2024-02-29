package com.skeleton.util;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.function.Function;
import java.util.function.Supplier;

public class PredicateBuilder implements Predicate {

    private Predicate predicate;

    public <A> PredicateBuilder where(A nullable, Function<A, Predicate> predicateGetter) {

        if (nullable != null) {
            if (predicate == null) {
                predicate = predicateGetter.apply(nullable);
            } else {
                predicate = ExpressionUtils.and(predicate, predicateGetter.apply(nullable));
            }
        }
        return this;
    }

    public <A> PredicateBuilder and(A nullable, Function<A, Predicate> predicateGetter) {

        if (nullable != null) {
            if (predicate == null) {
                predicate = predicateGetter.apply(nullable);
            } else {
                predicate = ExpressionUtils.and(predicate, predicateGetter.apply(nullable));
            }
        }
        return this;
    }

    public PredicateBuilder and(Supplier<BooleanExpression> expressionSupplier) {

        BooleanExpression booleanExpression = expressionSupplier.get();

        if (booleanExpression != null) {
            if (predicate == null) {
                predicate = booleanExpression;
            } else {
                predicate = ExpressionUtils.and(predicate, booleanExpression);
            }
        }
        return this;
    }

    public PredicateBuilder and(PredicateBuilder predicateBuilder) {

        if (predicate == null) {
            predicate = predicateBuilder;
        } else {
            predicate = ExpressionUtils.and(predicate, predicateBuilder);
        }
        return this;
    }

    public PredicateBuilder or(Supplier<BooleanExpression> expressionSupplier) {

        BooleanExpression booleanExpression = expressionSupplier.get();

        if (booleanExpression != null) {
            if (predicate == null) {
                predicate = booleanExpression;
            } else {
                predicate = ExpressionUtils.or(predicate, booleanExpression);
            }
        }
        return this;
    }

    public PredicateBuilder or(BooleanExpression booleanExpression) {

        if (booleanExpression != null) {
            if (predicate == null) {
                predicate = booleanExpression;
            } else {
                predicate = ExpressionUtils.or(predicate, booleanExpression);
            }
        }
        return this;
    }

    public PredicateBuilder or(PredicateBuilder predicateBuilder) {

        if (predicate == null) {
            predicate = predicateBuilder;
        } else {
            predicate = ExpressionUtils.or(predicate, predicateBuilder);
        }
        return this;
    }

    public <A> PredicateBuilder or(A nullable, Function<A, Predicate> predicateGetter) {

        if (nullable != null) {
            if (predicate == null) {
                predicate = predicateGetter.apply(nullable);
            } else {
                predicate = ExpressionUtils.or(predicate, predicateGetter.apply(nullable));
            }
        }
        return this;
    }

    public PredicateBuilder exists(JPAQuery jpaQuery) {

        Predicate existsPredicate = Expressions.predicate(Ops.EXISTS, jpaQuery);

        if (predicate == null) {
            predicate = existsPredicate;
        } else {
            predicate = ExpressionUtils.and(predicate, existsPredicate);
        }
        return this;
    }

    @Override
    public PredicateBuilder not() {
        if (predicate != null) {
            predicate = predicate.not();
        }
        return this;
    }

    @Override
    public <R,C> R accept(Visitor<R,C> v, C context) {
        if (predicate != null) {
            return predicate.accept(v, context);
        } else {
            return null;
        }
    }

    @Override
    public Class<? extends Boolean> getType() {
        return Boolean.class;
    }

    // private final List<Predicate> predicates = new ArrayList<>();

    // public PredicateBuilder where(Predicate predicate) {
    // 	PredicateBuilder builder = new PredicateBuilder();
    // 	builder.predicates.add(predicate);
    // 	return builder;
    // }

    // public <A> PredicateBuilder where(A nullable, Function<A, Predicate> predicateGetter) {
    // 	// PredicateBuilder builder = new PredicateBuilder();
    // 	if (nullable != null)
    // 		this.predicates.add(predicateGetter.apply(nullable));
    // 	return this;
    // }
    //
    // // public PredicateBuilder and(Predicate predicate) {
    // // 	this.predicates.add(predicate);
    // // 	return this;
    // // }
    //
    // public <A> PredicateBuilder and(A nullable, Function<A, Predicate> predicateGetter) {
    // 	if (nullable != null)
    // 		this.predicates.add(predicateGetter.apply(nullable));
    // 	return this;
    // }
    //
    // public Predicate[] build() {
    // 	return predicates.toArray(new Predicate[predicates.size()]);
    // }
}

