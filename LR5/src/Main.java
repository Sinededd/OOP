import Model.Order.Batch;
import Model.Order.Order;
import Model.Transport.TransportSpecs;
import Utilities.LogisticLoader.CsvDataLoader;
import Utilities.LogisticLoader.DataLoader;
import Utilities.LogisticLoader.JsonDataLoader;
import Utilities.LogisticLoader.XmlDataLoader;
import Utilities.LogisticStore.InMemoryDataStore;
import Utilities.OrderFilter.CompositeFilter;
import Utilities.OrderFilter.FilterByCost;
import Utilities.OrderFilter.FilterByTime;
import Utilities.OrderFilter.FilterByTransportType;
import Utilities.OrderSave.*;
import Utilities.OrderSort.*;
import Model.Transport.TransportFactory;


public static int readInt(String prompt) {
    while (true) {
        try {
            String input = IO.readln(prompt);
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            IO.println("Ошибка! Введите корректное число.");
        }
    }
}

public static void printOrder(Order order) {
    System.out.println("\n=== Информация о заказе ===");
    System.out.printf("Транспорт: %-15s | Тип: %-10s%n",
            order.getTransport().getName(), order.getTransport().getType());
    System.out.printf("Дистанция: %-15.2f км%n", order.getDistance());
    System.out.printf("Время в пути: %-15.2f ч.%n", order.getTime());
    System.out.printf("Стоимость доставки: %-10.2f%n", order.getTransport().getExpense(order.getDistance()));
    System.out.printf("ИТОГО (Партия + Доставка): %.2f%n", order.getTotalCost());
    System.out.println("---------------------------");
}

public static Batch BuildingBatchMenu(InMemoryDataStore inMemoryDataStore) {
    Batch batch = new Batch(inMemoryDataStore);

    while (true) {
        IO.println("\n--- Доступные товары ---");
        System.out.format("%-25s | %-15s | %-15s%n", "Название", "Масса единицы", "Цена за кг");
        System.out.println(String.join("", Collections.nCopies(60, "-")));
        inMemoryDataStore.getCargoCatalog().forEach((name, params) ->
                System.out.format("%-25s | %-15.2f | %-15.2f%n", name, params.unitMass(), params.costPerKg())
        );
        System.out.println(String.join("", Collections.nCopies(60, "-")));


        if (!batch.getCargoList().isEmpty()) {
            IO.println("\nТекущий заказ:");
            batch.getCargoList().forEach(cargo ->
                    IO.println(" - " + cargo.getName() + " | " + cargo.getQuantity() + " шт. | Стоимость: " + cargo.getCost())
            );
            IO.println("Общая стоимость заказа: " + batch.getCost());
            System.out.println(String.join("", Collections.nCopies(60, "-")));
        }

        String name = IO.readln("\nВведите название товара (или '0' для завершения): ").trim();

        if (name.equals("0")) {
            break;
        }

        if (!inMemoryDataStore.getCargoCatalog().containsKey(name)) {
            IO.println("Ошибка: Товар '" + name + "' не найден в каталоге.");
            continue;
        }

        int quantity = readInt("Введите количество для товара '" + name + "': ");

        if (quantity <= 0) {
            IO.println("Ошибка: Количество должно быть больше нуля.");
            continue;
        }

        try {
            batch.addCargo(name, quantity);
            IO.println("Товар успешно добавлен!");
        } catch (IllegalArgumentException e) {
            IO.println("Ошибка при добавлении: " + e.getMessage());
        }
    }

    IO.println("\nФормирование партии завершено!");
    return batch;
}



public static List<Order> createOrdersSession(InMemoryDataStore dataStore, TransportFactory transportFactory) {
    double distance = (double) readInt("Введите дистанцию (км): ");
    Batch batch = BuildingBatchMenu(dataStore);

    if (batch.getCargoList().isEmpty()) return new ArrayList<>();

    return selectTransportOptions(batch, distance, dataStore, transportFactory);
}


private static List<Order> selectTransportOptions(Batch batch, double distance,
                                                  InMemoryDataStore dataStore,
                                                  TransportFactory transportFactory) {
    List<Order> result = new ArrayList<>();

    IO.println("\n--- Выбор транспорта ---");
    IO.println("Введите название транспорта или '0' для вывода всех вариантов:");
    System.out.format("%-10s | %-10s | %-13s | %-10s\n",
            "Название", "Тип", "Расход (л/км)", "Скорость (км/ч)");
    System.out.println(String.join("", Collections.nCopies(60, "-")));

    dataStore.getTransportCatalog().forEach((fullName, specs) ->
            System.out.format("%-10s | %-10s | %-13.2f | %-10.2f%n",
                    fullName,
                    specs.type(),
                    specs.consumption(),
                    specs.speed()
            ));

    String choice = IO.readln("> ").trim();

    if (choice.equals("0")) {
        for (TransportSpecs specs : dataStore.getTransportCatalog().values()) {
            result.add(new Order(distance, batch, transportFactory.createTransport(specs.name())));
        }
    } else {
        TransportSpecs selected = dataStore.getTransportSpecs(choice);
        if (selected != null) {
            result.add(new Order(distance, batch, transportFactory.createTransport(selected.name())));
        } else {
            IO.println("Транспорт не найден.");
        }
    }
    return result;
}


public static void sortingMenu(List<Order> orders) {
    if (orders == null || orders.size() <= 1) return;

    while (true) {
        IO.println("\n--- Настройка сортировки ---");
        IO.println("1. По стоимости");
        IO.println("2. По времени");
        IO.println("3. По типу транспорта");
        IO.println("0. Завершить");

        int typeChoice = readInt("Выберите критерий: ");
        if (typeChoice == 0) break;

        Sortable<Order> sorter = switch (typeChoice) {
            case 1 -> new SortOrderByCost();
            case 2 -> new SortOrderByTime();
            case 3 -> new SortOrderByTransportType();
            default -> null;
        };

        if (sorter != null) {
            IO.println("Порядок:");
            IO.println("1. Прямой (А-Я, 0-9)");
            IO.println("2. Обратный (Я-А, 9-0)");
            int direction = readInt("> ");

            if (direction == 2) {
                sorter = new ReverseSorter<>(sorter);
            }

            sorter.sort(orders);

            IO.println("\nСортировка применена:");
            for (Order order : orders) {
                printOrder(order);
            }
        }
    }
}


public static DataLoader selectDataLoader() {
    IO.println("\n=== Выбор формата данных ===");
    IO.println("1. Загрузить из CSV (logistic.csv)");
    IO.println("2. Загрузить из JSON (logistic.json)");
    IO.println("3. Загрузить из XML (logistic.xml)");

    while (true) {
        int choice = readInt("Выберите вариант: ");
        switch (choice) {
            case 1:
                IO.println("Выбран формат CSV.");
                return new CsvDataLoader("logistic.csv");
            case 2:
                IO.println("Выбран формат JSON.");
                return new JsonDataLoader("logistic.json");
            case 3:
                IO.println("Выбран формат XML.");
                return new XmlDataLoader("logistic.xml");
            default:
                IO.println("Неверный выбор. Пожалуйста, введите 1 или 2.");
        }
    }
}

public static void exportMenu(List<Order> orders) {
    IO.println("\n=== Настройка экспорта ===");
    IO.println("1. CSV");
    IO.println("2. JSON");
    int formatChoice = readInt("Выберите формат: ");

    OrderSaver saver = (formatChoice == 2) ? new JsonOrderSaver() : new CsvOrderSaver();

    IO.println("Дополнительные опции (1 - Да, 0 - Нет):");
    int encrypt = readInt("Шифрование? ");
    int compress = readInt("Сжатие (ZIP)? ");

    if (encrypt == 1) {
        saver = new EncryptionDecorator(saver);
    }
    if (compress == 1) {
        saver = new CompressionDecorator(saver);
    }

    String fileName = "orders_export" + (formatChoice == 2 ? ".json" : ".csv");
    saver.save(orders, fileName);
    IO.println("Экспорт завершен!");
}


public static List<Order> filterMenu(List<Order> orders) {
    if (orders == null || orders.size() <= 1) return orders;

    CompositeFilter<Order> compositeFilter = new CompositeFilter<>();

    while (true) {
        IO.println("\n--- Настройка фильтрации ---");
        IO.println("1. По максимальной стоимости");
        IO.println("2. По максимальному времени");
        IO.println("3. По типу транспорта");
        IO.println("0. Применить фильтры");

        int choice = readInt("Выберите фильтр: ");

        if (choice == 0) break;

        switch (choice) {
            case 1 -> {
                double maxCost = readInt("Введите максимальную стоимость: ");
                compositeFilter.addFilter(new FilterByCost(maxCost));
            }
            case 2 -> {
                double maxTime = readInt("Введите максимальное время: ");
                compositeFilter.addFilter(new FilterByTime(maxTime));
            }
            case 3 -> {
                String type = IO.readln("Введите тип транспорта (Земля/Вода/Воздух): ");
                compositeFilter.addFilter(new FilterByTransportType(type));
            }
            default -> IO.println("Неверный выбор.");
        }
    }

    List<Order> filtered = compositeFilter.filter(orders);

    IO.println("\n=== Результат фильтрации ===");
    for (Order order : filtered) {
        printOrder(order);
    }

    return filtered;
}



void main(String[] args) {
    InMemoryDataStore dataStore = new InMemoryDataStore(selectDataLoader());
    TransportFactory transportFactory = new TransportFactory(dataStore);

    IO.println("=== Система управления логистикой ===");
    List<Order> orders = createOrdersSession(dataStore, transportFactory);
    if (orders.isEmpty()) {
        IO.println("Заказы не сформированы.");
        return;
    }

   IO.println("\n=== Исходный список вариантов ===");
    for(Order order : orders) {
        printOrder(order);
    }

    orders = filterMenu(orders);
    sortingMenu(orders);
    exportMenu(orders);

    IO.println("\n=== Работа с заказами завершена ===");
}