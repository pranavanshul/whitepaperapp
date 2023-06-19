package com.cgi.whitepaperapp.audit;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WhitePaperInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> whitePaperMap = new HashMap<String, String>();
        whitePaperMap.put("App Name", "WhitePaperApp");
        whitePaperMap.put("App Description", "White Paper Application for Students and Admin");
        whitePaperMap.put("App Version", "1.0.0");
        whitePaperMap.put("Contact Email", "pranav.anshul@cgi.com");
        whitePaperMap.put("Contact Mobile", "+44-7561867203");
        builder.withDetail("whitepaperapp-info", whitePaperMap);
    }

}
