package com.future.study.spring.boot.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dexterleslie.Chan
 */
@Component
public class CustomizeInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        Map<String,Object> info=new HashMap<>();
        info.put("info1","information one");
        info.put("info2","information two");
        builder.withDetails(info);
    }
}
