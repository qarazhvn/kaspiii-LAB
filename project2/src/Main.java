import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Добавление заказов в корзину ===");
        cart.addOrder("Lenovo", "Электроника", 250000.0);
        cart.addOrder("Logitech", "Электроника", 5000.0);
        cart.addOrder("Java Programming", "Книги", 3500.0);
        cart.addOrder("Sony", "Электроника", 15000.0);
        cart.addOrder("Spring Framework", "Книги", 4500.0);
        cart.addOrder("Razer", "Электроника", 12000.0);
        cart.addOrder("Python", "Книги", 3000.0);
        
        System.out.println("\n" + cart);
        System.out.println();

        demonstrateSearchByPrice(cart, scanner);

        demonstrateGroupByCategory(cart);

        interactiveMenu(cart, scanner);

        scanner.close();
    }

    private static void demonstrateSearchByPrice(Cart cart, Scanner scanner) {
        System.out.println("=== Поиск заказов по стоимости ===");
        System.out.print("Введите минимальную стоимость: ");
        
        try {
            double minPrice = scanner.nextDouble();
            List<Order> expensiveOrders = cart.findOrdersAbovePrice(minPrice);
            
            System.out.println("\nЗаказы с ценой выше " + minPrice + ":");
            if (expensiveOrders.isEmpty()) {
                System.out.println("Нет заказов с такой стоимостью");
            } else {
                expensiveOrders.forEach(System.out::println);
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Ошибка ввода. Используется значение по умолчанию: 10000");
            cart.findOrdersAbovePrice(10000.0).forEach(System.out::println);
            System.out.println();
            scanner.nextLine();
        }
    }

    private static void demonstrateGroupByCategory(Cart cart) {
        System.out.println("=== Группировка заказов по категориям ===");
        Map<String, Long> categoryCount = cart.groupByCategory();
        
        System.out.println("Количество заказов по категориям:");
        categoryCount.forEach((category, count) -> 
            System.out.println(category + ": " + count + " заказов"));
        System.out.println();
    }

    private static void interactiveMenu(Cart cart, Scanner scanner) {
        scanner.nextLine();
        
        while (true) {
            System.out.println("=== Меню ===");
            System.out.println("1. Добавить заказ");
            System.out.println("2. Показать все заказы");
            System.out.println("3. Поиск по стоимости");
            System.out.println("4. Группировка по категориям");
            System.out.println("5. Очистить корзину");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    addNewOrder(cart, scanner);
                    break;
                case "2":
                    showAllOrders(cart);
                    break;
                case "3":
                    searchByPrice(cart, scanner);
                    break;
                case "4":
                    demonstrateGroupByCategory(cart);
                    break;
                case "5":
                    cart.clear();
                    break;
                case "0":
                    System.out.println("Программа завершена.");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.\n");
            }
        }
    }

    private static void addNewOrder(Cart cart, Scanner scanner) {
        try {
            System.out.print("Введите наименование: ");
            String name = scanner.nextLine();
            
            System.out.print("Введите категорию: ");
            String category = scanner.nextLine();
            
            System.out.print("Введите стоимость: ");
            double price = Double.parseDouble(scanner.nextLine());
            
            cart.addOrder(name, category, price);
            System.out.println();
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении заказа: " + e.getMessage() + "\n");
        }
    }

    private static void showAllOrders(Cart cart) {
        System.out.println("\n=== Все заказы ===");
        List<Order> orders = cart.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("Корзина пуста");
        } else {
            orders.forEach(System.out::println);
            System.out.println("\n" + cart);
        }
        System.out.println();
    }

    private static void searchByPrice(Cart cart, Scanner scanner) {
        try {
            System.out.print("Введите минимальную стоимость: ");
            double minPrice = Double.parseDouble(scanner.nextLine());
            
            List<Order> results = cart.findOrdersAbovePrice(minPrice);
            System.out.println("\nЗаказы с ценой выше " + minPrice + ":");
            if (results.isEmpty()) {
                System.out.println("Нет заказов с такой стоимостью");
            } else {
                results.forEach(System.out::println);
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage() + "\n");
        }
    }
}
