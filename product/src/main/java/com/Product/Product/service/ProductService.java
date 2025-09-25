package com.Product.Product.service;

import com.Product.Product.dto.ProductDto;
import com.Product.Product.model.Product;
import com.Product.Product.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProductDto> getAllProducts(){
        List<Product>productList = productRepo.findAll();
        return modelMapper.map(productList,new TypeToken<List<ProductDto>>(){}.getType());
    }
    public ProductDto saveProduct(ProductDto productDto){
        System.out.println(productDto);
        productRepo.save(modelMapper.map(productDto,Product.class));
        System.out.println(productDto);
        return productDto;
    }
    public ProductDto updateProduct(ProductDto productDto){
        System.out.println(productDto);
        productRepo.save(modelMapper.map(productDto,Product.class));
        return productDto;
    }
    public String deleteProduct(Integer productId){
        productRepo.deleteById(productId);
        System.out.println(productId);
        return "product deleted";
    }
    public ProductDto getProductById(Integer productID){
        Product product = productRepo.getProductById(productID);
        return modelMapper.map(product,ProductDto.class);
    }


}
