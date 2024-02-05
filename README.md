# java-kanban
#### Приложение для управления задачами

Предназначено для повышения эффективности совместной работы над задачами в коллективе. Рабочие задачи визуально представлены на доске Kanban, что позволяет участникам команды видеть состояние каждой задачи в любой момент времени.


Содержит **три типа задач**: *task, subtask и epic* и предоставляет **3 статуса** для каждого из типов: *new, in progress и done*.

#### Основная функциональность:
* Для каждого типа задач реализованы следующие типы задач:
    ** хранение данных в оперативной памяти, загрузка и сохранение данных в XML-файл
    ** получение списка задач
    ** удаление всех задач
    ** получение по идентификатору
    ** создание задачи
    ** обновление
    ** удаление по идентификатору
    ** сохранение истории просмотра
* Для типа epic возможно Получение списка подзадач
* API, эндпоинты которого соответствуют ключевым методам программы

#### Стек:
Java, Gson, JUnit, API
