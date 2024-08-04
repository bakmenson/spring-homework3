### Домашнее задание к семинару №8

1. - [x] В LoggingAspect добавить логирование типов и значений аргументов. 
Например (пример вывода): TimesheetService.findById(Long = 3)
Эту информацию можно достать из joinPoint.getArgs()

2. Создать аспект, который аспектирует методы, помеченные аннотацией Recover, и делает следующее:
    - [x] 2.1 Если в процессе исполнения метода был exception (любой),
то его нужно залогировать ("Recovering TimesheetService#findById after Exception[RuntimeException.class, "exception message"]")
и вернуть default-значение наружу Default-значение: для примитивов значение по умолчанию, для ссылочных типов - null.
Для void-методов возвращать не нужно.

3. - [ ] **** В аннотацию Recover добавить атрибут Class<?>[] noRecoverFor, в которое можно записать список классов исключений,
которые НЕ нужно отлавливать.