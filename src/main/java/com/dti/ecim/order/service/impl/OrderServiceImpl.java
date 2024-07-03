package com.dti.ecim.order.service.impl;

import com.dti.ecim.order.repository.OrderRepository;
import com.dti.ecim.order.repository.StatusRepository;
import com.dti.ecim.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final StatusRepository statusRepository;
}
