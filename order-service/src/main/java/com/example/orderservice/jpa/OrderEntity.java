package com.example.orderservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="orders")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //고유한 값으로 설정
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String productId;
    @Column(nullable = false)
    private Integer qty;
    @Column(nullable = false)
    private Integer unitPrice; //단가
    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private String userId; //주문한 사용자
    @Column(nullable = false, unique = true)
    private String orderId; //주문 번호

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value="CURRENT_TIMESTAMP") //현재 시간을 가지고 옴
    private Date createdAt;
}
