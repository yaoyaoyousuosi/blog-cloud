package org.cloud.blog.producer.service;

import java.util.Map;

public interface ProducerService {

    void send(Map<String, String> data);
}
