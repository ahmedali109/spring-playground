package org.example.booting.coach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoachController {
    private Coach coach;

    @Autowired
    public CoachController(Coach coach){
        this.coach = coach;
    }

    @GetMapping("/dailyWork")
    public String getDailyWork(){
        return coach.getDailyWork();
    }

}
