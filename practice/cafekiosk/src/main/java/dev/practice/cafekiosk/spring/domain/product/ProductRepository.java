package dev.practice.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * select *
     * from product
     * where selling_status in ('SELLING', 'HOLD');
     */
    List<Product> findBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);

    //왜 In 을 썻는지..
    List<Product> findAllByProductNumberIn(List<String> productNumbers);

    @Query(value = "select product_number from product order by id desc limit 1", nativeQuery = true)
    String findLatestProductNumber();
}
