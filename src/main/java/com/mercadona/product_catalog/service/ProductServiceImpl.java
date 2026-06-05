package com.mercadona.product_catalog.service;

import com.mercadona.product_catalog.dto.CreateProductRequest;
import com.mercadona.product_catalog.dto.ProductResponse;
import com.mercadona.product_catalog.entity.Product;
import com.mercadona.product_catalog.exception.ProductAlreadyExistsException;
import com.mercadona.product_catalog.exception.ProductNotFoundException;
import com.mercadona.product_catalog.mapper.ProductMapper;
import com.mercadona.product_catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    //So we need dependency injection:
    private final ProductRepository productRepository;
    //and then constructor injection:SPRING
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        if (productRepository.existsByNameAndCategory(
                request.getName(),
                request.getCategory())) {

            throw new ProductAlreadyExistsException(
                    "Product " + request.getName() +
                            " in category " + request.getCategory() +
                            " already exists"
            );
        }
        Product product = ProductMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            return ProductMapper.toResponse(product.get());
        }

        throw new ProductNotFoundException(
                "Product with id " + id + " not found");
    }
    @Override
    public void deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(
                    "Product with id " + id + " not found");
        }

        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse updateProduct(
            Long id,
            CreateProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product with id " + id + " not found"));

        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.toResponse(updatedProduct);
    }
}
