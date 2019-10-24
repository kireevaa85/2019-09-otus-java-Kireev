package ru.otus.hw03gc;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class GcSample {

    public static void main(String... args) throws Exception {
        final Map<String, GcStatistic> gcStatisticMap = new HashMap<>();

        switchOnMonitoring(gcStatisticMap);

        List<Long> list = new ArrayList<>();
        while (true) {
            Thread.sleep(1);
            list.addAll(LongStream.range(1, 100).boxed().collect(Collectors.toList()));
            list.removeIf(aLong -> aLong > 50);
        }
    }

    private static void switchOnMonitoring(Map<String, GcStatistic> gcStatisticMap) {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            gcStatisticMap.put(gcbean.getName(), new GcStatistic());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                    long duration = info.getGcInfo().getDuration();

                    GcStatistic gcStatistic = gcStatisticMap.get(gcbean.getName());
                    gcStatistic.setCount(gcStatistic.getCount() + 1);
                    gcStatistic.setDuration(gcStatistic.getDuration() + duration);

                    System.out.println();
                    gcStatisticMap.forEach((key, value) -> System.out.println(key + "- count: "
                            + value.getCount() + ", duration: "
                            + value.getDuration() + " ms"));

                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

}
