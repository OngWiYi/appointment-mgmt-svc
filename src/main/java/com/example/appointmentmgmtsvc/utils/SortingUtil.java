/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.utils;

import com.example.appointmentmgmtsvc.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

public class SortingUtil {
    @Value("{$sorting.order}")
    private static String defaultOrder;
    @Value("{$sorting.column}")
    private static String defaultSort;

    private SortingUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static SortParams validateSortParams(String sort, String order, List<String> allowedSortColumn) {
        if (sort == null || sort.isEmpty()) {
            sort = defaultSort;
        }
        if (order == null || order.isEmpty()) {
            order = defaultOrder;
        }

        if (allowedSortColumn != null && !allowedSortColumn.isEmpty() && (!allowedSortColumn.contains(sort))) {
            throw new BadRequestException(String.format("Invalid sort entity. Only %s accepted.", allowedSortColumn));
        }

        if (!order.equalsIgnoreCase(Direction.ASC.name()) && !order.equalsIgnoreCase(Direction.DESC.name())) {
            throw new BadRequestException("Invalid sort order. Only 'asc' or 'desc' are allowed.");
        }

        return new SortParams(sort, order);
    }

    public static class SortParams {
        private final String sort;
        private final String order;

        public SortParams(String sort, String order) {
            this.sort = sort;
            this.order = order;
        }

        public String getSort() {
            return sort;
        }

        public String getOrder() {
            return order;
        }
    }
}