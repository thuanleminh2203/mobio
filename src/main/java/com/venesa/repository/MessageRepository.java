package com.venesa.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.venesa.entity.Message;
import com.venesa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    Message findByMessageId(int messageId);
}
