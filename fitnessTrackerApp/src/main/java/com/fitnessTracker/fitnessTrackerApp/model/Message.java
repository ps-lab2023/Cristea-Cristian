package com.fitnessTracker.fitnessTrackerApp.model;

import com.fitnessTracker.fitnessTrackerApp.enums.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;
}
