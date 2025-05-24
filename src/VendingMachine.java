import java.util.*;

public class VendingMachine {

    private Map<String, Double> beverages;

    private Map<Integer, Double> cards;

    private static class Column {
        String beverageName;
        int cans;

        Column(String beverageName, int cans) {
            this.beverageName = beverageName;
            this.cans = cans;
        }
    }

    private Map<Integer, Column> columns;

    public VendingMachine() {
        beverages = new HashMap<>();
        cards = new HashMap<>();
        columns = new HashMap<>();
    }

    public void addBeverage(String name, double price) {
        beverages.put(name, price);
    }

    public double getPrice(String beverageName) {
        return beverages.getOrDefault(beverageName, -1.0);
    }

    public void rechargeCard(int cardId, double credit) {
        cards.put(cardId, cards.getOrDefault(cardId, 0.0) + credit);
    }

    public double getCredit(int cardId) {
        return cards.getOrDefault(cardId, -1.0);
    }

    public void refillColumn(int column, String beverageName, int cans) {
        if (column >= 1 && column <= 4) {
            columns.put(column, new Column(beverageName, cans));
        }
    }

    public int availableCans(String beverageName) {
        int total = 0;
        for (Column col : columns.values()) {
            if (col.beverageName.equals(beverageName)) {
                total += col.cans;
            }
        }
        return total;
    }

    public int sell(String beverageName, int cardId) {
        if (!beverages.containsKey(beverageName) || !cards.containsKey(cardId)) {
            return -1;
        }

        double price = beverages.get(beverageName);
        double credit = cards.get(cardId);
        if (credit < price) {
            return -1;
        }

        for (Map.Entry<Integer, Column> entry : columns.entrySet()) {
            Column col = entry.getValue();
            if (col.beverageName.equals(beverageName) && col.cans > 0) {
                col.cans--;
                cards.put(cardId, credit - price);
                return entry.getKey();
            }
        }

        return -1;
    }
}
