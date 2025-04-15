package com.mystore.orders.service;

import com.mystore.orders.OrdersApplication;
import com.mystore.orders.dto.OrderRequest;
import com.mystore.orders.dto.OrderResponse;
import com.mystore.orders.dto.Product;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
@Service
@RefreshScope
public class OrderService {

    String GET_PROD_URL  = "http://PRODUCT-SERVICE/products/{id}";


    @Autowired
    private DiscoveryClient discoveryClient ;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${db.name}")
    private String dbName;

    String GET_URL="http://localhost:8080/products/{id}";
    RestClient restClient;

    public OrderResponse placeOrder(OrderRequest orderRequest) {




        System.out.println(orderRequest.toString());
        restClient=RestClient.builder().build();
//        no we dont need to get the array of instances it will automatically be takjen by the spring
//        List<ServiceInstance> serviceInstances= discoveryClient.getInstances("product-service");
//        ServiceInstance serviceInstance=serviceInstances.get(0);
        List<ServiceInstance> serviceInstances=discoveryClient.getInstances("product-service");
        Integer productId=orderRequest.getProductId();
//        String hostname=serviceInstance.getHost();
//        Integer port=serviceInstance.getPort();
//        GET_URL="http://"+hostname+":"+port+"/products/{id}";



        // TODO: 1. retrieve the product details from the product-service

//        GET_PROD_URL=GET_PROD_URL.replace("{id}",productId.toString());
        System.out.println(GET_PROD_URL);



        ResponseEntity<Product>  productResponseEntity = restTemplate.getForEntity(GET_PROD_URL,Product.class,productId);

        // TODO: 2. process the order (total price should be = quantity ordered * product price)
        Product product= productResponseEntity.getBody();
        Long orderId= new Random().nextLong();
        OrderResponse orderResponse=new OrderResponse();
        orderResponse.setOrderId(orderId);
        orderResponse.setProductId(productId);
        orderResponse.setProductName(product.getName());
        orderResponse.setQty(orderRequest.getQty());
        orderResponse.setTotalPrice(orderRequest.getQty()*product.getPrice());


        // TODO: 3. return the
        System.out.println(orderResponse + "::::::: was send :::::");
        return orderResponse;

    }

    public String callGreeting(String name){
        String url= "http://PRODUCT-SERVICE/products/sayHello/"+name;
        System.out.println(url+":::::::");
        ResponseEntity<String> resp= restTemplate.getForEntity(url,String.class);
        return resp.getBody() + " - Org Location "+dbName;

    }

}
