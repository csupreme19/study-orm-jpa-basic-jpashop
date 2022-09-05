package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            // 연관관계 매핑이 없을 때(RDB 관점)
            Order order = em.find(Order.class, 1L);
            Member member = em.find(Member.class, order.getMemberId());

            // 연관관계 매핑이 있을 때(객체지향 관점)
//            order.getMember();

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
