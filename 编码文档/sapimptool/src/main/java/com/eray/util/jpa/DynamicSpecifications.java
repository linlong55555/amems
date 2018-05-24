package com.eray.util.jpa;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.eray.util.jpa.Collections3;
import com.google.common.collect.Lists;

/**
 * 修改的动态封装查询条件类
 * 
 * @author zh
 * @version 1.0 2013-03-20
 */

public class DynamicSpecifications
{
    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> clazz)
    {
        return new Specification<T>()
        {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder)
            {
                if (Collections3.isNotEmpty(filters))
                {
                    List<Predicate> inor = Lists.newArrayList();
                    List<Predicate> predicates = Lists.newArrayList();
                    for (SearchFilter filter : filters)
                    {
                        // nested path translate, 如Task的名为"user.name"的filedName,
                        // 转换为Task.user.name属性
                        String[] names = StringUtils.split(filter.fieldName, ".");
                        Path expression = root.get(names[0]);
                        for (int i = 1; i < names.length; i++)
                        {
                            expression = expression.get(names[i]);
                        }
                        // logic operator
                        switch (filter.operator)
                        {
                        case EQ:
                            predicates.add(builder.equal(expression, filter.value));
                            break;
                        case LIKE:
                            predicates.add(builder.like(expression, "%" + filter.value + "%"));
                            break;
                        case GT:
                            predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                            break;
                        case LT:
                            predicates.add(builder.lessThan(expression, (Comparable) filter.value));
                            break;
                        case GTE:
                            predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                            break;
                        case LTE:
                            predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                            break;
                        case IN:
                            Object[] parts = (Object[]) filter.value;
                            In cb = builder.in(expression);
                            for (Object part : parts)
                            {
                                cb = cb.value(part);
                            }
                            predicates.add(cb);
                            break;
                        case NEQ:
                            predicates.add(builder.notEqual(expression, filter.value));
                            break;
                        case INOR:
                            Object[] objs = (Object[]) filter.value;
                            In in = builder.in(expression);
                            for (Object obj : objs)
                            {
                                in = in.value(obj);
                            }
                            inor.add(in);
                            break;
                        }
                    }

                    // 将所有条件用 and 联合起来
                    if (predicates.size() > 0)
                    {
                        Predicate p = builder.and(predicates.toArray(new Predicate[predicates.size()]));
                        if (inor.size() > 0)
                        {
                            Predicate temp = builder.or(inor.toArray(new In[inor.size()]));
                            p = builder.and(p, temp);
                        }
                        return p;
                    } else if (inor.size() > 0)
                    {
                        return builder.or(inor.toArray(new In[inor.size()]));
                    }
                }
                return builder.conjunction();
            }
        };
    }
}
