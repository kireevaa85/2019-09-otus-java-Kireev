1) -Xms32m -Xmx32m -XX:+UseSerialGC
            Copy- count: 15, duration: 53 ms
MarkSweepCompact- count:  2, duration: 21 ms
				   Summ: 17,           74 ms

2) -Xms32m -Xmx32m -XX:+UseParallelGC
PS MarkSweep- count:  2, duration: 34 ms
 PS Scavenge- count: 17, duration: 51 ms
 			   Summ: 19,           85 ms

3)-Xms32m -Xmx32m -XX:+UseConcMarkSweepGC
             ParNew- count: 15, duration:    70 ms
ConcurrentMarkSweep- count: 11, duration: 10196 ms
				      Summ: 26,           10266 ms

4) -Xms32m -Xmx32m -XX:+UseG1GC
G1 Young Generation- count: 24, duration:  93 ms
G1   Old Generation- count:  2, duration:  21 ms
				      Summ: 26,           114 ms

Вывод: в данном кейсе отработал лучше Serial Collector,
как по количеству сборок, так и по времени он справился
быстрее.
В целом все наравне примерно, кроме CMS, видимо не зря он deprecated.