# Введение
Для анализа работы разных имплементаций GC в Java использовалось приложение в hw04-gk при разных объемах выделенной памяти.
Замеры производились при 32Gb, 8Gb, 1Gb и 256Mb оперативной памяти. Параметры машины, на которой запускались тесты: 
Intel(R) Core(TM) i7-9700 CPU @ 3.00GHz; 48.0 GB RAM  В приложении организована утечка памяти. Также в хипе
при каждом обходе цикла создается определенное количество объектов, готовых для сбора мусора. Можно контролировать размер пачки
новых данных а также сдвиг, который будет задавать уровень утечки, а также количество объектов, предназначенных для сборки мусора.
Логи запуска приложений а также скриншоты VisualVM находятся в корневой дирректории проекта.
По результатам экспериментов были получены следующие результаты:
# Результаты
### G1GC
Память | Количество вызовов | Интервал запуска вначале | Интервал запуска вконце | Время сборки вначале | Время сборки вконце | Размер storage перед OOM error
--- | --- | --- | --- | --- | --- | ---
32Gb| 162 Young<br>3 Old | ~10s Young<br>~30s Old| ~4s Young<br>~6s Old| ~100ms Young<br>~8500ms Old| ~60ms Young<br>~6800ms Old | 798_381_090
8Gb| 141 Young<br>34 Old | 1-10s Young<br>~3,5s Old| ~2s Young<br>~2s Old| 10-100ms Young<br>~2100ms Old| 10-20ms Young<br>~1800ms Old | 236_557_360
1Gb| 223 Young<br>119 Old | 300-1500ms Young<br>~500ms Old| ~250ms Young<br>~250ms Old| 5-25ms Young<br>~250ms Old| ~250ms Young<br>~250ms Old | 29_587_579
256Mb| 173 Young<br>76 Old | ~300ms Young<br>~150ms Old| ~70ms Young<br>~70ms Old| 5-10ms Young<br>~70ms Old| ~2ms Young<br>~70ms Old | 7_140_000

### CMS
Память | Количество вызовов | Интервал запуска вначале | Интервал запуска вконце | Время сборки вначале | Время сборки вконце | Размер storage перед OOM error
--- | --- | --- | --- | --- | --- | ---
32Gb| 850 ParNew<br>5 CMS | ~1,5s ParNew<br>CMS - очень редко| ~1300ms ParNew<br>CMS - очень редко| ~80ms ParNew<br>~30000ms CMS| ~50ms ParNew<br>~60000ms CMS | 798_381_090
8Gb| 191 ParNew<br>93 CMS | ~1,5s ParNew<br>CMS - очень редко| ParNew - очень редко<br>7/30s CMS| ~60ms ParNew<br>7/30s CMS| ~30ms ParNew<br>7/30s CMS | 236_557_360
1Gb| 167 ParNew<br>137 CMS | ~900ms ParNew<br>~7s CMS| ~900ms ParNew<br>~1s CMS| ~30ms ParNew<br>~2s CMS| ~900ms ParNew<br>~1s CMS | 25_825_000
256Mb| 34 ParNew<br>19 CMS | ~200ms ParNew<br>~1,5s CMS| ~600ms ParNew<br>~400ms CMS| ~10ms ParNew<br>~400s CMS| ~0ms ParNew<br>~400ms CMS | 6_153_400

### Parallel
Память | Количество вызовов | Интервал запуска вначале | Интервал запуска вконце | Время сборки вначале | Время сборки вконце | Размер storage перед OOM error
--- | --- | --- | --- | --- | ---|---
32Gb| 80 Scavenge<br>108 MarkSweep | ~18s Scavenge<br>MarkSweep - очень редко| Scavenge - очень редко<br>30s - MarkSweep| ~1s Scavenge<br>~30s MarkSweep| ~3s Scavenge<br>~30s MarkSweep | 777_945_000
8Gb| 51 Scavenge<br>306 MarkSweep | ~6s Scavenge<br>MarkSweep - очень редко| Scavenge - очень редко<br>8s - MarkSweep| ~300ms Scavenge<br>~6s MarkSweep| ~1s Scavenge<br>~8s MarkSweep | 214_316_841
1Gb| 45 Scavenge<br>136 MarkSweep | ~600ms Scavenge<br>MarkSweep - очень редко| Scavenge - очень редко<br>1s - MarkSweep| ~30ms Scavenge<br>~1s MarkSweep| ~80ms Scavenge<br>~1s MarkSweep | 27_135_000
256Mb| 45 Scavenge<br>25 MarkSweep | ~150ms Scavenge<br>MarkSweep - очень редко| Scavenge - очень редко<br>300ms - MarkSweep| ~10ms Scavenge<br>~250ms MarkSweep| ~30ms Scavenge<br>~250ms MarkSweep | 6_153_400

### Serial
Память | Количество вызовов | Интервал запуска вначале | Интервал запуска вконце | Время сборки вначале | Время сборки вконце | Размер storage перед OOM error
--- | --- | --- | --- | --- | ---|---
32Gb| 40 Copy<br>43 MarkSweepCompact | ~20s Copy<br>MarkSweepCompact - очень редко| Copy - очень редко<br>25s - MarkSweepCompact| ~1s Copy<br>~15s MarkSweep| ~1s Copy<br>~17s MarkSweepCompact | 798_381_090
8Gb| 43 Copy<br>384 MarkSweepCompact | ~6s Copy<br>MarkSweepCompact - очень редко| Copy - очень редко<br>5s - MarkSweepCompact| ~300ms Copy<br>~5s MarkSweep| ~300ms Copy<br>~5s MarkSweepCompact | 229_881_216
1Gb| 34 Copy<br>244 MarkSweepCompact | ~800ms Copy<br>MarkSweepCompact - очень редко| Copy - очень редко<br>~650ms - MarkSweepCompact| ~36ms Copy<br>~650s MarkSweep| ~36ms Copy<br>~650ms MarkSweepCompact | 28_507_143
256Mb| 35 Copy<br>19 MarkSweepCompact | ~200ms Copy<br>MarkSweepCompact - очень редко| Copy - очень редко<br>~300ms - MarkSweepCompact| ~10ms Copy<br>~150s MarkSweep| ~10ms Copy<br>~150ms MarkSweepCompact | 6_153_400
# Выводы
Из таблиц выше и приведенных отчетов видно, что в случае Parallel и Serial GC по мере того, как заполняется память один 
вид сборки мусора полностью замещается другим. Также видно, что эти 2 GC начинают занимать практически все время работы 
системы. Сама же система продолжает работать чуть дольше, чем в случае CMS и G1GC. Однако задержки порядка десятков секунд
 вряд ли приемлемы для системы.Что касается G1GC - этот сборщик хорошо показывает себя при большом объеме хипа. 
 При малых же объемах хипа эффективность проявляет CMS (меньше вызовов GC, меньше нагрузка на cpu). При том оба сборщика
 работают с обоими видами сборки. Нет такого, что один тип начинает доминировать над другим. С другой стороны с точки зрения
 эффективности сборки, G1GC позволяет совершить больший объем полезной работы при всех значениях выделенной для хипа памяти.
 При 32GB итоговый размер storage перед OOM error совпадает с CMS и Serial сборщиками, однако при всех остальных значениях 
 G1GC позволяет выполнить больше итераций тестировочной программы. При молых значениях памяти (256Mb) CMS, Serial и Parallel
 сборщики показывают одинаковое значение полезной работы, однако время выполняемой работы относительно того же G1GC
 оказывается намного больше. В результате экспериментов видно, что при данной конфигурации железа G1GC сборщик оказывается
 более эффективным при всех диапазонах выделенной памяти.
