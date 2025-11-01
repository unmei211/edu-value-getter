package com.subby.eduvaluegetter.valuegetter.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.subby.eduvaluegetter.valuegetter.service.selector.IValueSelector;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class ValueGetterService {
    private final ResourceLoader resourceLoader;
    private final IValueSelector selector;

    ValueGetterService(
            ResourceLoader resourceLoader,
            IValueSelector valueSelector
    ) {
        this.resourceLoader = resourceLoader;
        this.selector = valueSelector;
    }

    public int getMinValueByStep(final String path, final Integer step) throws IOException {
        final List<Integer> unsorted = new LinkedList<>();

        readXlsxFile(
                path,
                ResourceUtils.FILE_URL_PREFIX,
                Integer::parseInt,
                unsorted::add
        );

        var arrayList = new ArrayList<>(unsorted);
        return selector.select(arrayList, step);
    }

    private <T> void readXlsxFile(
            final String path,
            final String resourceKind,
            final Function<String, T> mapper,
            final Consumer<T> eachConsumer
    ) throws IOException {
        final String resourcePath = resourceKind +
                path;
        var resource = resourceLoader.getResource(resourcePath);

        try (InputStream stream = resource.getInputStream()) {
            EasyExcel.read(stream, new ReadListener<Map<Integer, String>>() {
                @Override
                public void invoke(Map<Integer, String> row, AnalysisContext analysisContext) {
                    eachConsumer.accept(mapper.apply(row.get(0)));
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                }
            }).sheet().doRead();
        }
    }
}
