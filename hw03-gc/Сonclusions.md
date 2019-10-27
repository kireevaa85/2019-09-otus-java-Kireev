1) -Xms512m -Xmx512m -XX:+UseSerialGC
            Copy- count:  17, duration:  1963 ms
MarkSweepCompact- count:  87, duration: 63118 ms
	              Total: 104,           65081 ms (average latency = 625 ms)
  Total time(benchmark): 00:05:10

2) -Xms512m -Xmx512m -XX:+UseParallelGC
    PS MarkSweep- count:  5, duration:   5097 ms
     PS Scavenge- count: 15, duration:   1598 ms
 	              Total: 20,             6695 ms (average latency = 334 ms)
  Total time(benchmark): 00:01:54

3) -Xms512m -Xmx512m -XX:+UseConcMarkSweepGC 
Показатели сняты на 30-й минуте работы, отработала программа не до конца.
Но показателей достаточно понять - что данный gc явный аутсайдер.
             ParNew- count: 35, duration: 11042 ms
ConcurrentMarkSweep- count: 39, duration: 62657 ms
				      Summ: 74,           73699 ms (average latency = 995 ms)
	 Total time(benchmark): БОЛЕЕ 00:30:00 - не стал дожидаться до конца, достаточно.

4) -Xms512m -Xmx512m -XX:+UseG1GC
G1 Young Generation- count: 46, duration:  647 ms
G1   Old Generation- count:  7, duration: 1876 ms
				      Summ: 53,           2523 ms (average latency = 47 ms)
     Total time(benchmark): 00:06:29

Вывод для данного кейса:
 - CMS явный аутсайдер как по benchmark, так и по latency, видимо не зря он deprecated.
 - Далее идет на выход Serial Collector, т.к. benchmark 5 минут(почти как у G1), 
но при этом latency 625 ms на порядок больше чем у того же G1(47 ms).
 - Остались Parallel Collector и G1, тут смотря что нам важно. 
 Если нам важна скорость работы данного кейса - то явный фаворит Parallel Collector.
 Если нам важно минимизировать latency, то наоборот - явный фаворит G1.