package junrui.logic;

import junrui.mapper.OrderMapper;
import junrui.model.Order;
import junrui.model.User;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderLogic {

    private static final Logger logger = LoggerFactory.getLogger(OrderLogic.class);

    @Autowired
    private OrderMapper orderMapper;

    public void cartAdd(int userId, int publicationId) {
        Order order = orderMapper.selectByUserAndPubl(userId, publicationId);
        if (order == null) {
            order = new Order();
            order.setUserId(userId);
            order.setPublId(publicationId);
            order.setNumber(1);
            orderMapper.insert(order);
        } else {
            orderMapper.incrementById(order.getId());
        }
    }

    public void cartCommit(User user) {
        int userId = user.getId();
        List<Order> orders = orderMapper.selectByUserId(userId, false);
        Map<Integer, List<Order>> sellerOrders = new HashMap<>();
        for (Order order : orders) {
            int sellerId = order.getPubl().getSellerId();
            if (sellerOrders.containsKey(sellerId)) {
                sellerOrders.get(sellerId).add(order);
            } else {
                List<Order> orders2 = new ArrayList<>();
                orders2.add(order);
                sellerOrders.put(sellerId, orders2);
            }
        }

        for (Integer sellerId : sellerOrders.keySet()) {
            List<Order> orders3 = sellerOrders.get(sellerId);
            if (orders3.size() > 0) {
                Utils.sendEmail(orders3.get(0).getPubl().getSeller().getEmail(),
                        "Publications Sold",
                        getEmailText(orders3, user));
                logger.debug(orders3.get(0).getPubl().getSeller().getEmail());
                logger.debug(getEmailText(orders3, user));
            }
        }

        orderMapper.updateCommitTs(userId);
    }

    private String getEmailText(List<Order> orders, User user) {
        StringBuilder builder = new StringBuilder();
        builder.append("The following publication(s) have been sold:\n");
        BigDecimal sum = BigDecimal.ZERO;
        for (Order order : orders) {
            builder.append("\n    Title: ");
            builder.append(order.getPubl().getTitle());
            builder.append("\n    Price: ");
            builder.append(order.getPubl().getPrice());
            builder.append("\n    Number: ");
            builder.append(order.getNumber());
            builder.append("\n");
            sum = sum.add(order.getPubl().getPrice());
        }
        builder.append("\nTotal Price: \n\n    ");
        builder.append(sum);
        builder.append("\n\nBuyer:\n\n    Username: ");
        builder.append(user.getUsername());
        builder.append("\n    Full name: ");
        builder.append(user.getFirstName());
        builder.append(", ");
        builder.append(user.getLastName());
        builder.append("\n\nAddress:\n\n");
        builder.append(user.getAddress());
        builder.append("\n");
        return builder.toString();
    }
}
