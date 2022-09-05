package jpabook.jpashop;

import jpabook.jpashop.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Item item = new Item();
            item.setName("itemA");
            item.setPrice(5000);
            item.setStockQuantity(1);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setOrderPrice(15000);
            orderItem.setCount(1);

            Member member = new Member();
            member.setName("memberA");
            member.setCity("seoul");
            member.setStreet("hi street");
            member.setZipcode("12345");

            Order order = new Order();
            order.setOrderDate(LocalDateTime.now());
            order.setStatus(OrderStatus.ORDER);
            // 연관관계 편의 메소드
            order.addOrderItem(orderItem);
            order.changeMember(member);

            em.persist(member);
            em.persist(order);
            em.persist(item);
            em.persist(orderItem);

//            em.flush();
//            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            List<Order> findOrders = findMember.getOrders();
            Order findOrder = findOrders.get(0);
            List<OrderItem> findOrderItems = findOrder.getOrderItems();
            OrderItem findOrderItem = findOrderItems.get(0);
            Item findItem = findOrderItem.getItem();

            System.out.println("========================");
            System.out.println("findItem(" + findItem.getId() + ", " + findItem.getName() + ", " + findItem.getPrice() + ", " + findItem.getStockQuantity() + ")");
            System.out.println("========================");

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }

    }
}
