import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cart {
    private List<Order> orders;

    public Cart() {
        this.orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
        System.out.println("Заказ добавлен: " + order);
    }

    public void addOrder(String name, String category, double price) {
        Order order = new Order(name, category, price);
        addOrder(order);
    }

    public List<Order> findOrdersAbovePrice(double minPrice) {
        return orders.stream()
                .filter(order -> order.getPrice() > minPrice)
                .collect(Collectors.toList());
    }

    public Map<String, Long> groupByCategory() {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCategory, Collectors.counting()));
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public int getOrderCount() {
        return orders.size();
    }

    public double getTotalPrice() {
        return orders.stream()
                .mapToDouble(Order::getPrice)
                .sum();
    }

    public void clear() {
        orders.clear();
        System.out.println("Корзина очищена");
    }

    @Override
    public String toString() {
        return "Корзина{количество заказов=" + orders.size() + 
               ", общая стоимость=" + String.format("%.2f", getTotalPrice()) + "}";
    }
}
