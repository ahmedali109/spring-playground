package org.example.booting.coach;

import org.springframework.stereotype.Component;

@Component
public class Coach implements ICoach{
    @Override
    public String getDailyWork() {
        return "Runnig for 15 minutes";
    }
}
