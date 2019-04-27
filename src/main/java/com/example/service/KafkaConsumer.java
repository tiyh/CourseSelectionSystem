package com.example.service;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.example.dao.SelectionDAO;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class KafkaConsumer {
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	private SelectionDAO SelectionDAO;
    @Autowired
	public void setSelectionDAO(SelectionDAO SelectionDAO) {
		this.SelectionDAO = SelectionDAO;
	}
    @Transactional
    @KafkaListener(topics = {"select"})
    public void listenSelect(ConsumerRecord<?, ?> record){
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            logger.info("listenSelect:"+message);
            JSONObject json = JSONObject.parseObject(message.toString());
            int courseId = json.getIntValue("courseId");
            int studentId = json.getIntValue("studentId");
            //record.key(), record.value()
    		if(this.SelectionDAO.courseOrderable(courseId,studentId)){
				logger.info("Student:"+ studentId +" has selected courseId:"+courseId);
				this.SelectionDAO.selectCourse(studentId, courseId);
			}
        }

    }
}
