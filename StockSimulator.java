/**
 * Title: JaykwonnJamesA10
 * Semester: COP3337 Fall 2024
 * @author Jaykwonn James
 *
 * I affirm that this program is entirely my own work
 * and none of it is the work of any other person.
 *
 * This program helps you track buying and selling stocks while following the FIFO (First In, First Out) rule.
 * It calculates your profit by selling the oldest shares first, as required by tax rules.
 * A queue is used to keep track of the order in which stocks were bought.
 * You can use commands like "buy" and "sell" to make transactions, and the program shows your profit for each sale.
 * It also checks for errors, like trying to sell more shares than you have.
 *
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;
import java.util.TreeMap;

/**
 Class for simulating trading a single stock at varying prices.
 */
public class StockSimulator {
    private Map<String, Queue<Block>> blocks;

    /**
     Constructor.
     */
    public StockSimulator() {
        blocks = new TreeMap<>();
    }

    /**
     Handle a user buying a given quantity of stock at a given price.
     @param symbol the stock to buy
     @param quantity how many to buy.
     @param price the price to buy.
     */
    public void buy(String symbol, int quantity, int price) {
        blocks.putIfAbsent(symbol, new LinkedList<>());
        blocks.get(symbol).add(new Block(quantity, price));
        System.out.println("Bought " + quantity + " shares of " + symbol + " at $" + price + " each.");
    }

    /**
     Handle a user selling a given quantity of stock at a given price.
     @param symbol the stock to sell
     @param quantity how many to sell.
     @param price the price to sell.
     */
    public void sell(String symbol, int quantity, int price) {
        if (!blocks.containsKey(symbol) || blocks.get(symbol).isEmpty()) {
            System.out.println("Error: No shares of " + symbol + " to sell.");
            return;
        }

        Queue<Block> stockQueue = blocks.get(symbol);
        int totalProfit = 0;
        int remaining = quantity;

        while (remaining > 0 && !stockQueue.isEmpty()) {
            Block currentBlock = stockQueue.peek();
            int sellQuantity = Math.min(remaining, currentBlock.getQuantity());
            int profit = sellQuantity * (price - currentBlock.getPrice());
            totalProfit += profit;

            currentBlock.sell(sellQuantity);
            remaining -= sellQuantity;

            if (currentBlock.getQuantity() == 0) {
                stockQueue.poll();
            }
        }

        if (remaining > 0) {
            System.out.println("Error: Not enough shares of " + symbol + " to sell.");
        } else {
            System.out.println("Sold " + quantity + " shares of " + symbol + " at $" + price + " each for a profit of $" + totalProfit + ".");
        }
    }
}
