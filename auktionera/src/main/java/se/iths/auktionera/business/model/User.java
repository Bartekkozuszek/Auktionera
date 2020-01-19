package se.iths.auktionera.business.model;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private long id;
    private String userName;
    private Instant createdAt;
    private UserStats stats;
}
