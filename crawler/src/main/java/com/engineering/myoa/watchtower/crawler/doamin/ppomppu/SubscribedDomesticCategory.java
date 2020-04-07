package com.engineering.myoa.watchtower.crawler.doamin.ppomppu;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * SubscribedDomesticCategory
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Entity
@Table(name = "ppomppu_subscribed_domestic_category")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscribedDomesticCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String memberId;

    @Column
    @Enumerated(EnumType.STRING)
    DomesticCategory category;

    @Column
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    LocalDateTime modifiedAt;
}
