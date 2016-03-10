package com.quizdeck.services;

import com.quizdeck.model.database.submission;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Cade on 3/10/2016.
 */

@Getter
@Setter
@Service
public class RedisSubmission {

    private RedisTemplate<String, submission> redisTemplate;

}
