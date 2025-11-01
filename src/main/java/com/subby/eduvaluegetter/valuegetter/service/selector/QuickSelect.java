package com.subby.eduvaluegetter.valuegetter.service.selector;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class QuickSelect implements IValueSelector {
    @Override
    public int select(List<Integer> list, int n) {
        return quickSelect(list, 0, list.size() - 1, n);
    }

    private static int quickSelect(List<Integer> list, int left, int right, int n) {
        if (left == right) {
            return list.get(left);
        }

        int pivotIdx = partition(list, left, right);

        if (pivotIdx == n - 1) {
            return list.get(pivotIdx);
        }

        if (pivotIdx > n - 1) {
            return quickSelect(list, left, pivotIdx - 1, n);
        } else {
            return quickSelect(list, pivotIdx + 1, right, n);
        }
    }

    private static int partition(List<Integer> list, int left, int right) {
        int pivotIdx = new Random().nextInt(right - left + 1) + left;

        int pivot = list.get(pivotIdx);
        swap(list, pivotIdx, right);

        int i = left;
        for (int j = left; j < right; j++) {
            if (list.get(j) <= pivot) {
                swap(list, i, j);
                i++;
            }
        }

        swap(list, i, right);

        return i;
    }

    private static void swap(List<Integer> list, int left, int right) {
        int buff = list.get(left);

        list.set(left, list.get(right));
        list.set(right, buff);
    }
}
