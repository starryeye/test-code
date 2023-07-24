package dev.practice.cafekiosk.spring.api.service.product;

import dev.practice.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import dev.practice.cafekiosk.spring.api.service.product.response.ProductResponse;
import dev.practice.cafekiosk.spring.domain.product.Product;
import dev.practice.cafekiosk.spring.domain.product.ProductRepository;
import dev.practice.cafekiosk.spring.domain.product.ProductSellingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Transactional(readOnly = true)
 * -> CRUD 에서 CUD 는 동작하지 않는다. (DB)
 * -> flush(변경감지), 스냅샷 저장이 동작하지 않는다. (1차 캐시 저장은 동작함. 조회 동일성 보장)
 *
 * CQRS
 * -> @Transactional readOnly 옵션을 보고 조회용 DB(slave), CUD 용 DB(master) 엔드포인트를 정해줄 수 있다.
 *
 * Command / Query 각각의 서비스를 만드는게 좋고
 * 합친다면 서비스 최상단에 @Transactional(readOnly = true) 를 적용하고 CUD 메서드에 @Transactional 을 적용하자.
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //TODO, 동시성 문제
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest productCreateRequest) {

        String nextProductNumber = createNextProductNumber();

        Product product = productCreateRequest.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    public List<ProductResponse> getProductSelling() {

        List<Product> products = productRepository.findBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .toList();
    }

    //UUID 로 하면 애초에 동시성 문제 없음
    private String createNextProductNumber() {
        // productNumber 채번, DB 에서 가장 마지막 productNumber 를 조회하여 +1 해준다.

        String latestProductNumber = productRepository.findLatestProductNumber();

        if(latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);

        int nextProductNumber = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumber);
    }
}
